/**
 * 
 */
package semaphore_demo;

import java.util.concurrent.Semaphore;

import utils.Debug;

/**
 * Entry class for demonstrating a simple semaphore with one flag
 * @author NeilDG
 *
 */
public class SimpleSemUsage {
	private final static String TAG = "SimpleSemUsage";
	
	private Semaphore flag = new Semaphore (1); //initialize a semaphore with 1 permit.
	
	public SimpleSemUsage() {
		
	}
	
	public void perform() {
		SemThread thread1 = new SemThread(1, this.flag);
		thread1.start();
		
		SemThread thread2 = new SemThread(2, this.flag);
		thread2.start();
	}
	
	private class SemThread extends Thread {
		private final static String TAG = "SemThread";
		
		private int threadID;
		private Semaphore flag;
		
		public SemThread(int threadID, Semaphore flag) {
			this.threadID = threadID;
			this.flag = flag;
		}
		
		@Override
		public void run() {
			try {
				this.flag.acquire();
				Debug.log(TAG + "_"+ threadID, "Did something!");
				this.flag.release();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}

}
