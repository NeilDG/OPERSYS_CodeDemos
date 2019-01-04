/**
 * 
 */
package scheduling;
import java.util.Comparator;
import java.util.Random;

import utils.Debug;

/**
 * @author NeilDG
 *
 */
public class ProcessRep {
	private final static String TAG = "ProcessRep";
	
	private int ID = 0;
	
	private int executionTime = 0;
	private int priority = 0;
	private int arrivalTime = 0;
	
	public ProcessRep(int ID, int exec, int arrival, int priority) {
		this.ID = ID;
		this.executionTime = exec;
		this.arrivalTime = arrival;
		this.priority = priority;
	}
	
	public int getExecutionTime() {
		return this.executionTime;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public static ProcessRep generateRandomData(int ID) {
		Random rand = new Random();
		int exec = rand.nextInt(20);
		int priority = rand.nextInt(5);
		int arrivalTime = rand.nextInt(10);
		
		return new ProcessRep(ID, exec, arrivalTime, priority);
	}
	
	public static class ArrivalSorter implements Comparator<ProcessRep> {

		@Override
		public int compare(ProcessRep p1, ProcessRep p2) {
			return p1.getArrivalTime() - p2.getArrivalTime();
		}
		
	}
}
