/**
 * 
 */
package semaphore_demo;

import java.util.concurrent.Semaphore;

import critical_section_demo.SharedData;
import utils.Debug;

/**
 * @author NeilDG
 *
 */
public class SemProducerThread extends Thread {
	private final static String TAG = "SemProducerThread";

	private Semaphore emptySem;
	private Semaphore fullSem;
	
	public SemProducerThread() {
		this.emptySem = SharedSemaphores.getInstance().getEmptySem();
		this.fullSem = SharedSemaphores.getInstance().getFullSem();
	}

	@Override
	public void run() {
		
		//execute N times
		for(int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1);	
				
				this.emptySem.acquire();
				
				///START CRITICAL SECTION
				if(SharedData.counter == SharedData.BUFFER_SIZE) {
					//do nothing
					Debug.log(TAG, "Producer did not produce. Buffer full");
				}
				else {
					int numberProduce = 5;

					SharedData.numberList[SharedData.counter] = numberProduce;
					SharedData.counter++;
					Debug.log(TAG, "Producer produced. Counter is " +SharedData.counter);

				}
				
				this.fullSem.release();

			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}

}
