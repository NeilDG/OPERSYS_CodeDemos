/**
 * 
 */
package scheduling;
import java.util.Comparator;
import java.util.Random;

import utils.Debug;

/**
 * A data structure for a representing a process. It is treated as READ-ONLY data.
 * @author NeilDG
 *
 */
public class ProcessRep {
	private final static String TAG = "ProcessRep";
	
	private int ID = 0;
	
	private int executionTime = 0;
	private int priority = 0;
	private int arrivalTime = 0;
	
	public ProcessRep(int ID, int arrival, int exec, int priority) {
		this.ID = ID;
		this.executionTime = exec;
		this.arrivalTime = arrival;
		this.priority = priority;
	}
	
	//Returns the process total execution/CPU time required to finish.
	public int getExecutionTime() {
		return this.executionTime;
	}
	
	//Returns the priority level, higher value = higher priority.
	public int getPriority() {
		return this.priority;
	}
	
	//Returns the supposed arrival time in the ready queue.
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	public int getID() {
		return this.ID;
	}
	
	/*
	 * Convenience function that generates random process data for stress-testing.
	 */
	public static ProcessRep generateRandomData(int ID) {
		Random rand = new Random();
		int exec = rand.nextInt(20);
		int priority = rand.nextInt(5);
		int arrivalTime = rand.nextInt(10);
		
		return new ProcessRep(ID, arrivalTime, exec, priority);
	}
	
	public static class ArrivalSorter implements Comparator<ProcessRep> {

		@Override
		public int compare(ProcessRep p1, ProcessRep p2) {
			return p1.getArrivalTime() - p2.getArrivalTime();
		}
		
	}
}
