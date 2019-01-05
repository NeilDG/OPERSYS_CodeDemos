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
	
	private int startTime; //the official CPU start/end time for gantt chart debugging
	private int endTime;
	
	private int waitingTime; //the total amount of time spent in the ready queue
	
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
	
	public void computeWaitingTime() {
		this.waitingTime += (this.startTime - this.arrivalTime);
	}
	
	public int getWaitingTime() {
		return this.waitingTime;
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
	
	public void setStartTime(int time) {
		this.startTime = time;
	}
	
	public void setEndTime(int time) {
		this.endTime = time;
	}
	
	public int getStartTime() {
		return this.startTime;
	}
	
	public int getEndTime() {
		return this.endTime;
	}
	
	public static ProcessExecutor createExecutor(ProcessRep P) {
		return new ProcessExecutor(P);
	}
	
	public static ProcessExecutor makeFinishedCopy(ProcessExecutor P) {
		ProcessExecutor F = new ProcessExecutor();
		F.arrivalTime = P.arrivalTime;
		F.cpuTime = P.cpuTime;
		F.ID = P.ID;
		F.startTime = P.startTime;
		F.endTime = P.endTime;
		
		return F;
	}
}
