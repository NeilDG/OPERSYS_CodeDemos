/**
 * 
 */
package scheduling;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import utils.Debug;

/**
 * Handles the simulation of a scheduler
 * @author NeilDG
 *
 */
public class SchedulerSimulation {
	private final static String TAG = "MainActivity";
	
	/*
	 * Util function that arranges an array P by arrival time and converts it into a queue.
	 */
	public static Queue<ProcessRep> arrangeByArrivalTime(ProcessRep[] P) {
		Arrays.sort(P, new ProcessRep.ArrivalSorter());		
		Queue<ProcessRep> pQueue = new LinkedList<ProcessRep>();
		for(int i = 0; i < P.length; i++) {
			Debug.log(TAG, "P["+P[i].getID()+"] Arrival Time: " +P[i].getArrivalTime()+ " Exec Time: " +P[i].getExecutionTime()+ " Priority: " +P[i].getPriority());
			pQueue.add(P[i]);
		}
		
		return pQueue;
	}
	//Should be called from a main thread.
	public void startSimulation() {
		ProcessRep[] P = new ProcessRep[10];
		
		for(int i = 0; i < P.length; i++) {
			P[i] = ProcessRep.generateRandomData(i);
		}
		
		//arrange by arrival time
		Debug.log(TAG, "=====SORTING BY ARRIVAL TIME=====");
		Queue<ProcessRep> pQueue = arrangeByArrivalTime(P);
		this.performFCFS(pQueue);
		
		P = new ProcessRep[3];
		P[0] = new ProcessRep(0, 0, 24, 1);
		P[1] = new ProcessRep(1, 0, 3, 1);
		P[2] = new ProcessRep(2, 0, 3, 1);
		
		pQueue = arrangeByArrivalTime(P);
		this.performFCFS(pQueue);

		P = new ProcessRep[4];
		P[0] = new ProcessRep(1, 0, 8, 1);
		P[1] = new ProcessRep(2, 1, 4, 1);
		P[2] = new ProcessRep(3, 2, 9, 1);
		P[3] = new ProcessRep(4, 3, 5, 1);
		
		pQueue = arrangeByArrivalTime(P);
		this.performShortestJobFirst(pQueue, true);
		
		P = new ProcessRep[5];
		P[0] = new ProcessRep(1, 0, 7, 1);
		P[1] = new ProcessRep(2, 2, 5, 1);
		P[2] = new ProcessRep(3, 3, 3, 1);
		P[3] = new ProcessRep(4, 5, 8, 1);
		P[4] = new ProcessRep(5, 6, 5, 1);
		pQueue = arrangeByArrivalTime(P);
		
		this.performShortestJobFirst(pQueue, true);
		
		P = new ProcessRep[5];
		P[0] = new ProcessRep(1, 0, 10, 10 - 3); //deduction added because in book example, low numbers = high priority
		P[1] = new ProcessRep(2, 0, 1, 10 - 1);
		P[2] = new ProcessRep(3, 0, 2, 10 - 4);
		P[3] = new ProcessRep(4, 0, 1, 10 - 5);
		P[4] = new ProcessRep(5, 0, 5, 10 - 2);
		pQueue = arrangeByArrivalTime(P);
		
		this.performPriorityShortestJobFirst(pQueue, true);
		
		P = new ProcessRep[3];
		P[0] = new ProcessRep(1, 0, 24, 1);
		P[1] = new ProcessRep(2, 0, 3, 1);
		P[2] = new ProcessRep(3, 0, 3, 1);
		pQueue = arrangeByArrivalTime(P);
		this.performRoundRobin(pQueue, 4);
	}
	
	private void performFCFS(Queue<ProcessRep> P) {
		Queue<ProcessExecutor> readyQueue = new LinkedList<ProcessExecutor>();
		LinkedList<ProcessExecutor> finishedP = new LinkedList<ProcessExecutor>();
		int cpuTime = 0;
		ProcessExecutor current = null; //current process being executed
		while(!P.isEmpty() || !readyQueue.isEmpty() || current != null) {
			
			while(!P.isEmpty() && P.peek().getArrivalTime() == cpuTime) {
				ProcessExecutor e = ProcessExecutor.createExecutor(P.remove());
				readyQueue.add(e);
			}
			
			//ready queue and CPU processing
			if(!readyQueue.isEmpty() && current == null) {
				current = readyQueue.remove();
				current.reportCPUEntry(cpuTime);
			}
			
			if(current != null) {
				current.execute();
				if(current.hasExecuted()) {
					current.reportFinished(cpuTime + 1);
					finishedP.add(ProcessExecutor.makeFinishedCopy(current));
					current = null;
				}
			}
			cpuTime++;
		}
		
		Debug.log(TAG, "=====FCFS FINISHED SIMULATION. EXECUTION ORDER=====");
		for(int i = 0; i < finishedP.size(); i++) {
			ProcessExecutor f = finishedP.get(i);
			f.computeWaitingTime();
			System.out.println("P["+f.getID()+ "] " +f.getTimeString()+  " Waiting time: " +f.getWaitingTime());
		}
		
		System.out.println("Average waiting time:  "+ProcessExecutor.computeAVGWaitingTime((LinkedList<ProcessExecutor>) finishedP));
		System.out.println();
	}
	
	private void performShortestJobFirst(Queue<ProcessRep> P, boolean isPreemptive) {
		LinkedList<ProcessExecutor> readyQueue = new LinkedList<ProcessExecutor>();
		LinkedList<ProcessExecutor> finishedP = new LinkedList<ProcessExecutor>();
		int cpuTime = 0;
		ProcessExecutor current = null; //current process being executed
		while(!P.isEmpty() || !readyQueue.isEmpty() || current != null) {
			
			while(!P.isEmpty() && P.peek().getArrivalTime() == cpuTime) {
				ProcessExecutor e = ProcessExecutor.createExecutor(P.remove());
				readyQueue.add(e);
			}
			
			//ready queue and CPU processing
			if(!readyQueue.isEmpty()) {
				if(current == null) {
					current = readyQueue.remove(this.findLowestTime(readyQueue));
					current.reportCPUEntry(cpuTime);
				}
				else if(readyQueue.get(this.findLowestTime(readyQueue)).getRemainingTime() < current.getRemainingTime() && isPreemptive) {
					current.reportReadyQueueEntry(cpuTime);
					readyQueue.add(current); //put back current and replace from ready queue
					
					current = readyQueue.remove(this.findLowestTime(readyQueue));
					current.reportCPUEntry(cpuTime);
				}
				
			}
			
			if(current != null) {
				current.execute();
				if(current.hasExecuted()) {
					current.reportFinished(cpuTime + 1);
					finishedP.add(ProcessExecutor.makeFinishedCopy(current));
					current = null;
				}
			}
			
			
			cpuTime++;
		}
		
		Debug.log(TAG, "=====SJF FINISHED SIMULATION. EXECUTION ORDER=====");
		for(int i = 0; i < finishedP.size(); i++) {
			ProcessExecutor f = finishedP.get(i);
			f.computeWaitingTime();
			System.out.println("P["+f.getID()+ "] " +f.getTimeString()+  " Waiting time: " +f.getWaitingTime());
		}
		
		System.out.println("Average waiting time:  "+ProcessExecutor.computeAVGWaitingTime((LinkedList<ProcessExecutor>) finishedP));
		System.out.println();
	}
	
	private void performPriorityShortestJobFirst(Queue<ProcessRep> P, boolean isPreemptive) {
		LinkedList<ProcessExecutor> readyQueue = new LinkedList<ProcessExecutor>();
		LinkedList<ProcessExecutor> finishedP = new LinkedList<ProcessExecutor>();
		int cpuTime = 0;
		ProcessExecutor current = null; //current process being executed
		while(!P.isEmpty() || !readyQueue.isEmpty() || current != null) {
			
			while(!P.isEmpty() && P.peek().getArrivalTime() == cpuTime) {
				ProcessExecutor e = ProcessExecutor.createExecutor(P.remove());
				readyQueue.add(e);
			}
			
			//ready queue and CPU processing
			if(!readyQueue.isEmpty()) {
				if(current == null) {
					current = readyQueue.remove(this.findLowestTimeHighPriority(readyQueue));
					current.reportCPUEntry(cpuTime);
				}
				else {
					ProcessExecutor candidate = readyQueue.get(this.findLowestTimeHighPriority(readyQueue));
					if(candidate.getPriority() > current.getPriority() && isPreemptive) {
						current.reportReadyQueueEntry(cpuTime);
						readyQueue.add(current); //put back current and replace from ready queue
						
						current = candidate;
						current.reportCPUEntry(cpuTime);
					}
					else if(candidate.getPriority() == current.getPriority() && candidate.getRemainingTime() < candidate.getRemainingTime() && isPreemptive) {
						current.reportReadyQueueEntry(cpuTime);
						readyQueue.add(current); //put back current and replace from ready queue
						
						current = candidate;
						current.reportCPUEntry(cpuTime);
					}
				}
				
			}
			
			if(current != null) {
				current.execute();
				if(current.hasExecuted()) {
					current.reportFinished(cpuTime + 1);
					finishedP.add(ProcessExecutor.makeFinishedCopy(current));
					current = null;
				}
			}
			
			
			cpuTime++;
		}
		
		Debug.log(TAG, "=====PRIORITY SJF FINISHED SIMULATION. EXECUTION ORDER=====");
		for(int i = 0; i < finishedP.size(); i++) {
			ProcessExecutor f = finishedP.get(i);
			f.computeWaitingTime();
			System.out.println("P["+f.getID()+ "] " +f.getTimeString()+  " Waiting time: " +f.getWaitingTime());
		}
		
		System.out.println("Average waiting time:  "+ProcessExecutor.computeAVGWaitingTime((LinkedList<ProcessExecutor>) finishedP));
		System.out.println();
	}
	
	private void performRoundRobin(Queue<ProcessRep> P, int timeSlice) {
		LinkedList<ProcessExecutor> readyQueue = new LinkedList<ProcessExecutor>();
		LinkedList<ProcessExecutor> finishedP = new LinkedList<ProcessExecutor>();
		int cpuTime = 0;
		int slice = 0;
		ProcessExecutor current = null; //current process being executed
		while(!P.isEmpty() || !readyQueue.isEmpty() || current != null) {
			
			while(!P.isEmpty() && P.peek().getArrivalTime() == cpuTime) {
				ProcessExecutor e = ProcessExecutor.createExecutor(P.remove());
				readyQueue.add(e);
			}
			
			//ready queue and CPU processing
			if(!readyQueue.isEmpty()) {
				if(current == null) {
					current = readyQueue.pollFirst();
					current.reportCPUEntry(cpuTime);
					slice = 0;
				}
				else if(slice % timeSlice == 0) {
					current.reportReadyQueueEntry(cpuTime);
					readyQueue.add(current); //put back current and replace from ready queue
					
					current = readyQueue.pollFirst();
					current.reportCPUEntry(cpuTime);
					slice = 0;
				}
			}
			
			if(current != null) {
				current.execute(); 
				slice++;
				if(current.hasExecuted()) {
					current.reportFinished(cpuTime + 1);
					finishedP.add(ProcessExecutor.makeFinishedCopy(current));
					current = null;
				}
			}
			
			
			cpuTime++;
		}
		
		Debug.log(TAG, "=====ROUND-ROBIN FINISHED SIMULATION. EXECUTION ORDER=====");
		for(int i = 0; i < finishedP.size(); i++) {
			ProcessExecutor f = finishedP.get(i);
			f.computeWaitingTime();
			System.out.println("P["+f.getID()+ "] " +f.getTimeString()+  " Waiting time: " +f.getWaitingTime());
		}
		
		System.out.println("Average waiting time:  "+ProcessExecutor.computeAVGWaitingTime((LinkedList<ProcessExecutor>) finishedP));
		System.out.println();
	}
	
	private int findLowestTime(LinkedList<ProcessExecutor> P) {
		int index = 0;
		for(int i = 1; i < P.size(); i++) {
			if(P.get(i).getRemainingTime() <= P.get(index).getRemainingTime()) {
				index = i;
			}
		}
		
		return index;
	}
	
	private int findLowestTimeHighPriority(LinkedList<ProcessExecutor> P) {
		int index = 0;
		for(int i = 1; i < P.size(); i++) {
			ProcessExecutor p1 = P.get(i);
			ProcessExecutor p2 = P.get(index);
			if(p1.getPriority() > p2.getPriority()) {
				index = i;
			}
			else if(p1.getPriority() == p2.getPriority() && p1.getRemainingTime() <= p2.getRemainingTime()) {
				index = i;
			}
		}
		
		return index;
	}
}
