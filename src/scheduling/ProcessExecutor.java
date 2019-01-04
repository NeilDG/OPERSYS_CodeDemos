/**
 * 
 */
package scheduling;

import utils.Debug;

/**
 * Creates an actual process executor, that deducts its execution time.
 * @author NeilDG
 *
 */
public class ProcessExecutor {
	private final static String TAG = "ProcessExecutor";
	
	private int ID;
	private int arrivalTime;
	private int cpuTime; //to be reduced after every execute() call
	
	private ProcessExecutor() {
		
	}
	public ProcessExecutor(ProcessRep P) {
		this.ID = P.getID();
		this.arrivalTime = P.getArrivalTime();
		this.cpuTime = P.getExecutionTime();
	}
	
	//simulates a CPU execution by reducing its CPU execution time.
	public void execute() {
		if(!this.hasExecuted()) {
			this.cpuTime--;
		}
	}
	
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	public int getRemainingTime() {
		return this.cpuTime;
	}
	
	public boolean hasExecuted() {
		return (this.cpuTime == 0);
	}
	
	public int getID() {
		return this.ID;
	}
	
	public static ProcessExecutor createExecutor(ProcessRep P) {
		return new ProcessExecutor(P);
	}
	
	public static ProcessExecutor makeFinishedCopy(ProcessExecutor P) {
		ProcessExecutor F = new ProcessExecutor();
		F.arrivalTime = P.arrivalTime;
		F.cpuTime = P.cpuTime;
		F.ID = P.ID;
		
		return F;
	}
}
