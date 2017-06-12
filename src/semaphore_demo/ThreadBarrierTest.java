/**
 * 
 */
package semaphore_demo;

import java.util.concurrent.Semaphore;

import platformtools.core_application.RandomUtil;
import utils.Debug;

/**
 * Testing of thread barrier to be used for exercise
 * @author NeilDG
 *
 */
public class ThreadBarrierTest {
	private final static String TAG = "ThreadBarrierTest";
	
	private int count = 0;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore flagSem = new Semaphore(0);
	
	public ThreadBarrierTest() {
		
	}
	
	public void start() {
		int numThreads = 5;
		Thread_A[] threadsA = new Thread_A[numThreads];
		Thread_B[] threadsB = new Thread_B[numThreads];
		
		for(int i = 0; i < numThreads; i++) {
			threadsA[i] = new Thread_A(i, this);
			threadsA[i].start();
		}
		
		for(int j = 0; j < numThreads; j++) {
			threadsB[j] = new Thread_B(j, this);
			threadsB[j].start();
		}
	}
	
	private void combine(String threadName) throws InterruptedException {
		this.mutex.acquire();
		this.count = count + 1; //possible deadlock. need to add mutex
		this.mutex.release();
		
		if(this.count == 10) {
			this.flagSem.release();
		}
		
		this.flagSem.acquire();
		this.flagSem.release();
		
		Debug.log(TAG, "Called combine by " +threadName);
	}
	
	
	private class Thread_A extends Thread {
		
		private int threadID;
		private String threadName;
		ThreadBarrierTest barrierTest;
		
		public Thread_A(int ID, ThreadBarrierTest barrierTest) {
			this.threadID = ID;
			this.threadName = "Thread_A" + this.threadID;
			this.barrierTest = barrierTest;
		}
		
		@Override
		public void run() {
			try {
				a1();
				Debug.log(TAG, this.threadName+ " has finished executing.");
				
				this.barrierTest.combine(this.threadName);
				
			} catch(InterruptedException e)  {
				e.printStackTrace();
			}
		}
		
		public void a1() throws InterruptedException {
			int toSleep = RandomUtil.randomRangeInclusive(100, 3000);
			Thread.sleep(toSleep);
		}
	}
	
private class Thread_B extends Thread {
		
		private int threadID;
		private String threadName;
		ThreadBarrierTest barrierTest;
		
		public Thread_B(int ID, ThreadBarrierTest barrierTest) {
			this.threadID = ID;
			this.threadName = "Thread_B" + this.threadID;
			this.barrierTest = barrierTest;
		}
		
		@Override
		public void run() {
			try {
				b1();
				
				Debug.log(TAG, this.threadName+ " has finished executing.");
				this.barrierTest.combine(this.threadName);
				
			} catch(InterruptedException e)  {
				e.printStackTrace();
			}
		}
		
		public void b1() throws InterruptedException {
			int toSleep = RandomUtil.randomRangeInclusive(100, 3000);
			Thread.sleep(toSleep);
		}
	}

}
