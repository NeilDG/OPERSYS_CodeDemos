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
		P[0] = new ProcessRep(0, 24, 0, 1);
		P[1] = new ProcessRep(1, 3, 0, 1);
		P[2] = new ProcessRep(2, 3, 0, 1);
		
		pQueue = arrangeByArrivalTime(P);
		this.performFCFS(pQueue);
		
		P = new ProcessRep[4];
		P[0] = new ProcessRep(1, 8, 0, 1);
		P[1] = new ProcessRep(2, 4, 1, 1);
		P[2] = new ProcessRep(3, 9, 2, 1);
		P[3] = new ProcessRep(4, 5, 3, 1);
		
		pQueue = arrangeByArrivalTime(P);
		this.performShortestJobFirst(pQueue, true);
	}
	
	private void performFCFS(Queue<ProcessRep> P) {
		Queue<ProcessExecutor> readyQueue = new LinkedList<ProcessExecutor>();
		Queue<ProcessExecutor> finishedP = new LinkedList<ProcessExecutor>();
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
		while(!finishedP.isEmpty()) {
			ProcessExecutor f = finishedP.remove();
			f.computeWaitingTime();
			System.out.println("P["+f.getID()+ "] Start Time: " +f.getStartTime()+ " End time: " +f.getEndTime() + " Waiting time: " +f.getWaitingTime());
		}
		
		System.out.println();
	}
	
	private void performShortestJobFirst(Queue<ProcessRep> P, boolean isPreemptive) {
		LinkedList<ProcessExecutor> readyQueue = new LinkedList<ProcessExecutor>();
		Queue<ProcessExecutor> finishedP = new LinkedList<ProcessExecutor>();
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
				else if(readyQueue.peek().getRemainingTime() < current.getRemainingTime() && isPreemptive) {
					current.reportReadyQueueEntry(cpuTime);
					readyQueue.add(current); //put back current and replace from ready queue
					
					current = readyQueue.remove(); 
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
		while(!finishedP.isEmpty()) {
			ProcessExecutor f = finishedP.remove();
			f.computeWaitingTime();
			System.out.println("P["+f.getID()+ "] Start Time: " +f.getStartTime()+ " End time: " +f.getEndTime() + " Waiting time: " +f.getWaitingTime());
		}
		
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
}
