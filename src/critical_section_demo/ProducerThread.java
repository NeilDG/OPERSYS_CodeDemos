/**
 * 
 */
package critical_section_demo;

import utils.Debug;

/**
 * Producer thread that adds a number on a list
 * @author NeilDG
 *
 */
public class ProducerThread extends Thread {
	private final static String TAG = "ProducerThread";
	
	public ProducerThread() {
		
	}
	
	@Override
	public void run() {
		//execute N times
		for(int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1); //pause
				
				/*
				 * Peterson solution start. Comment out when needed
				 */
				SharedData.flag[SharedData.PRODUCER_ID] = true;
				SharedData.turn = SharedData.PRODUCER_ID;
				
				while(/*SharedData.flag[SharedData.CONSUMER_ID] == true &&*/ SharedData.turn == SharedData.PRODUCER_ID) {
					//busy wait
					//Debug.log(TAG, "Busy waiting");
				}
				
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
				///END CRITICAL SECTION
				SharedData.flag[SharedData.PRODUCER_ID] = false;
				
				
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
