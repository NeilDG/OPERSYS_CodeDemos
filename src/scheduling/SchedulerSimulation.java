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
	
	//Should be called from a main thread.
	public void startSimulation() {
		ProcessRep[] P = new ProcessRep[10];
		
		for(int i = 0; i < P.length; i++) {
			P[i] = ProcessRep.generateRandomData(i);
		}
		
		this.performFCFS(P);
	}
	
	private void performFCFS(ProcessRep[] P) {
		//arrange by arrival time
		Debug.log(TAG, "=====SORTING BY ARRIVAL TIME=====");
		Arrays.sort(P, new ProcessRep.ArrivalSorter());
				
		int currentCPUTime = 0;
		Queue<ProcessExecutor> processQueue = new LinkedList<ProcessExecutor>();
		for(int i = 0; i < P.length; i++) {
			Debug.log(TAG, "P["+P[i].getID()+"] Arrival Time: " +P[i].getArrivalTime()+ " Exec Time: " +P[i].getExecutionTime()+ " Priority: " +P[i].getPriority());
			processQueue.add(ProcessExecutor.createExecutor(P[i]));
		}
		
		Queue<ProcessExecutor> finishedP = new LinkedList<ProcessExecutor>();
		ProcessExecutor current = null; //simulates a CPU core
		
		while(!processQueue.isEmpty()) {
			ProcessExecutor E = processQueue.peek();
			//Debug.log(TAG, "Process " +E.getID()+ " arrival time: " +E.getArrivalTime() + " current CPU time: " +currentCPUTime);
			if(E.getArrivalTime() <= currentCPUTime && current == null) {
				current = processQueue.remove();
			}
			else {
				currentCPUTime++;
			}
			
			while(current != null) {
				current.execute();
				//Debug.log(TAG, "Process " +current.getID()+ " executing at time " +currentCPUTime);
				if(current.hasExecuted()) {
					finishedP.add(ProcessExecutor.makeFinishedCopy(current));
					current = null; //remove from CPU
				}
				
				//increment CPU time
				currentCPUTime++;
			}
		}
		
		Debug.log(TAG, "=====FINISHED SIMULATION. EXECUTION ORDER=====");
		while(!finishedP.isEmpty()) {
			System.out.print("P["+finishedP.remove().getID()+ "] ");
		}
		
		System.out.println();
		
	}
}
