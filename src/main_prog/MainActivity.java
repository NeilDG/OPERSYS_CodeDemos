package main_prog;

import critical_section_demo.ConsumerThread;
import critical_section_demo.ProducerThread;
import critical_section_demo.SharedData;
import monitor_demo.MonitorConsumerThread;
import monitor_demo.MonitorProducerThread;
import semaphore_demo.SemConsumerThread;
import semaphore_demo.SemProducerThread;
import semaphore_demo.SimpleSemUsage;
import semaphore_demo.ThreadBarrierTest;
import threading_exercise.ExerciseMain;

public class MainActivity {
	private final static String TAG = "MainActivity";
	
	public static void main(String[] args) {
		SharedData.setup();
		
		///DEMO OF PETERSON'S SOLUTION AND CRITICAL SECTION PROBLEM	
		/*ProducerThread producerThread = new ProducerThread();
		producerThread.start();
		
		ConsumerThread consumerThread = new ConsumerThread();
		consumerThread.start();*/

		///DEMO OF SEMAPHORE
		//SimpleSemUsage simpleSemUsage = new SimpleSemUsage();
		//simpleSemUsage.perform();
		
		///DEMO OF SEMAPHORE USAGE IN PRODUCER CONSUMER
		/*SemProducerThread semProducerThread = new SemProducerThread();
		semProducerThread.start();
		
		SemConsumerThread semConsumerThread = new SemConsumerThread();
		semConsumerThread.start();*/
		
		/*ThreadBarrierTest threadBarrierTest = new ThreadBarrierTest();
		threadBarrierTest.start();*/
		
		///DEMO OF PRODUCER-CONSUMER USING MONITORS
		/*MonitorProducerThread monProducerThread = new MonitorProducerThread();
		monProducerThread.start();
		
		MonitorConsumerThread monConsumerThread = new MonitorConsumerThread();
		monConsumerThread.start();*/
		
		///DEMO OF CPU SCHEDULING
		//SchedulerSimulation scheduler = new SchedulerSimulation();
		//scheduler.startSimulation();
		
		//DEMO OF BASIC THREADING
		ExerciseMain exerciseMain = new ExerciseMain();
		exerciseMain.startExercise();
		
	}

}
