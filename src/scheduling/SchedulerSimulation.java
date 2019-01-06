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
		ProcessRep[] P = new ProcessRep[3];
		P[0] = new ProcessRep(0, 0, 24, 1);
		P[1] = new ProcessRep(1, 0, 3, 1);
		P[2] = new ProcessRep(2, 0, 3, 1);
		
		Queue<ProcessRep> pQueue = arrangeByArrivalTime(P);
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
		//simulation here
		Debug.log(TAG, "=====FCFS FINISHED SIMULATION. EXECUTION ORDER=====");
		//printing of results
	}
	
	private void performShortestJobFirst(Queue<ProcessRep> P, boolean isPreemptive) {
		//simulation here
		Debug.log(TAG, "=====SJF FINISHED SIMULATION. EXECUTION ORDER=====");
		//printing of results
	}
	
	private void performPriorityShortestJobFirst(Queue<ProcessRep> P, boolean isPreemptive) {
		//simulation here	
		Debug.log(TAG, "=====PRIORITY SJF FINISHED SIMULATION. EXECUTION ORDER=====");
		//printing of results
	}
	
	private void performRoundRobin(Queue<ProcessRep> P, int timeSlice) {
		//simulation here	
		Debug.log(TAG, "=====ROUND-ROBIN FINISHED SIMULATION. EXECUTION ORDER=====");
		//printing of results
	}
	
}
