package main_prog;

import critical_section_demo.ConsumerThread;
import critical_section_demo.ProducerThread;
import critical_section_demo.SharedData;
import semaphore_demo.SimpleSemUsage;

public class MainActivity {
	private final static String TAG = "MainActivity";
	
	public static void main(String[] args) {
		
		///DEMO OF PETERSON'S SOLUTION AND CRITICAL SECTION PROBLEM
		/*SharedData.setup();
		
		ProducerThread producerThread = new ProducerThread();
		producerThread.start();
		
		ConsumerThread consumerThread = new ConsumerThread();
		consumerThread.start();*/
		
		
		///DEMO OF SEMAPHORE
		SimpleSemUsage simpleSemUsage = new SimpleSemUsage();
		simpleSemUsage.perform();
	}

}
