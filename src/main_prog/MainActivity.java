package main_prog;

import scheduling.SchedulerSimulation;
public class MainActivity {
	private final static String TAG = "MainActivity";
	
	public static void main(String[] args) {
		SchedulerSimulation scheduler = new SchedulerSimulation();
		scheduler.startSimulation();	
	}

}
