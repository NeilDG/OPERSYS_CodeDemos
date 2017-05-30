/**
 * 
 */
package critical_section_demo;

import utils.Debug;

/**
 * Consumer thread that consumes a number in the buffer
 * @author NeilDG
 *
 */
public class ConsumerThread extends Thread {
	private final static String TAG = "ConsumerThread";
	
	public ConsumerThread() {
		
	}
	
	@Override
	public void run() {
		//execute N times
		for(int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1);
				
				/*
				 * Peterson solution start. Comment out when needed
				 */
				SharedData.flag[SharedData.CONSUMER_ID] = true;
				SharedData.turn = SharedData.CONSUMER_ID;
				
				while(SharedData.flag[SharedData.PRODUCER_ID] == true && SharedData.turn == SharedData.CONSUMER_ID) {
					//busy wait
					Debug.log(TAG, "Busy waiting");
				}
				
				///START CRITICAL SECTION
				if(SharedData.counter == 0) {
					//do nothing
					Debug.log(TAG, "Consumer did not consume. Buffer empty");
				}
				else {
					
					int numberToConsume = SharedData.numberList[SharedData.counter];
					SharedData.counter--;
					Debug.log(TAG, "Consumer consumed. Counter is: " +SharedData.counter);
					
				}
				///END CRITICAL SECTION	
				SharedData.flag[SharedData.CONSUMER_ID] = false;
				
				
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
