package main_prog;

import critical_section_demo.ConsumerThread;
import critical_section_demo.ProducerThread;
import critical_section_demo.SharedData;
import semaphore_demo.SemConsumerThread;
import semaphore_demo.SemProducerThread;
import semaphore_demo.SimpleSemUsage;
import semaphore_demo.ThreadBarrierTest;

public class MainActivity {
	private final static String TAG = "MainActivity";
	
	public static void main(String[] args) {
		SharedData.setup();
		
		///DEMO OF PETERSON'S SOLUTION AND CRITICAL SECTION PROBLEM	
		ProducerThread producerThread = new ProducerThread();
		producerThread.start();
		
		ConsumerThread consumerThread = new ConsumerThread();
		consumerThread.start();

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
		
	}

}
