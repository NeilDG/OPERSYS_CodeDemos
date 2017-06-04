package semaphore_demo;

import java.util.concurrent.Semaphore;

import critical_section_demo.SharedData;
import utils.Debug;

public class SemConsumerThread extends Thread {
	private final static String TAG = "SemConsumerThread";

	private Semaphore emptySem;
	private Semaphore fullSem;
	
	public SemConsumerThread() {
		this.emptySem = SharedSemaphores.getInstance().getEmptySem();
		this.fullSem = SharedSemaphores.getInstance().getFullSem();
	}

	@Override
	public void run() {
		//execute N times
		for(int i = 0; i < 4; i++) {
			try {
				Thread.sleep(1);

				this.fullSem.acquire();
				
				///START CRITICAL SECTION
				/*if(SharedData.counter == 0) {
					//do nothing
					Debug.log(TAG, "Consumer did not consume. Buffer empty");
				}
				else {

					int numberToConsume = SharedData.numberList[SharedData.counter];
					SharedData.counter--;
					Debug.log(TAG, "Consumer consumed. Counter is: " +SharedData.counter);

				}*/
				
				int numberToConsume = SharedData.numberList[SharedData.counter];
				SharedData.counter--;
				Debug.log(TAG, "Consumer consumed. Counter is: " +SharedData.counter);
				
				this.emptySem.release();


			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
