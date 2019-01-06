/**
 * 
 */
package scheduling;

import java.util.LinkedList;

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
	private int priority;
	
	private int waitingTime; //the total amount of time spent in the ready queue
	
	public class MetricInfo {
		public int startTime;
		public int endTime;
	}
	
	private LinkedList<MetricInfo> metricList = new LinkedList<MetricInfo>();
	
	private ProcessExecutor() {
		
	}
	public ProcessExecutor(ProcessRep P) {
		this.ID = P.getID();
		this.arrivalTime = P.getArrivalTime();
		this.cpuTime = P.getExecutionTime();
		this.priority = P.getPriority();
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	//simulates a CPU execution by reducing its CPU execution time.
	public void execute() {
		if(!this.hasExecuted()) {
			this.cpuTime--;
		}
	}
	
	//reports a CPU entry which "bookmarks" its start time in the metric info list
	public void reportCPUEntry(int time) {
		MetricInfo M = new MetricInfo();
		M.startTime = time;
		
		this.metricList.add(M);
	}
	
	//reports a ready queue entry and "bookmarks" the time it went there.
	public void reportReadyQueueEntry(int time) {
		this.metricList.peekLast().endTime = time;
	}
	
	//reports a finished execution. similar functionality as ready queue, but different function name to avoid confusion
	public void reportFinished(int time) {
		this.metricList.peekLast().endTime = time;
	}
	
	//computes the accumulated waiting time of this process or the time spent in the ready queue
	public void computeWaitingTime() {
		this.waitingTime = 0;
		MetricInfo M = this.metricList.get(0);
		this.waitingTime += (M.startTime - this.arrivalTime);
		
		for(int i = 1; i < this.metricList.size(); i++) {
			MetricInfo M2 = this.metricList.get(i);
			this.waitingTime += (M2.startTime - M.endTime);
		}
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
	
	//retrieves the start time, or the moment
	public int getStartTime() {
		return this.metricList.peekFirst().startTime;
	}
	
	//retrieves the end time. The very last end time will be retrieved, if the process was pre-empted N times.
	public int getEndTime() {
		return this.metricList.peekLast().endTime;
	}
	
	//Returns all the start and end times as a continuous string.
	public String getTimeString() {
		String text = "";
		for(int i = 0; i < this.metricList.size(); i++) {
			MetricInfo M = this.metricList.get(i);
			text += "Start time: " +M.startTime+ " End time: " +M.endTime+ " | ";
		}
		
		return text;
	}
	
	public static ProcessExecutor createExecutor(ProcessRep P) {
		return new ProcessExecutor(P);
	}
	
	public static ProcessExecutor makeFinishedCopy(ProcessExecutor P) {
		ProcessExecutor F = new ProcessExecutor();
		F.arrivalTime = P.arrivalTime;
		F.cpuTime = P.cpuTime;
		F.ID = P.ID;
		F.metricList = P.metricList;
		
		return F;
	}
	
	public static float computeAVGWaitingTime(LinkedList<ProcessExecutor> P) {
		float avg = 0.0f;
		
		for(int i = 0; i < P.size(); i++) {
			ProcessExecutor p = P.get(i);	
			p.computeWaitingTime();
			avg += p.getWaitingTime();
		}
		
		avg = avg / P.size() * 1.0f;
		return avg;
	}
}
