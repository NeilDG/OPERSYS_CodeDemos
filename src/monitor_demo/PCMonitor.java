/**
 * 
 */
package monitor_demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import critical_section_demo.SharedData;
import semaphore_demo.SharedSemaphores;
import utils.Debug;

/**
 * Shared monitor class for the producer-consumer problem
 * @author NeilDG
 *
 */
public class PCMonitor {
	private final static String TAG = "PCMonitor";
	
	private static PCMonitor sharedInstance = null;
	public static PCMonitor getInstance() {
		if(sharedInstance == null) {
			sharedInstance = new PCMonitor();
		}
		return sharedInstance;
	}
	
	private ReentrantLock pcLock = new ReentrantLock();
	private Condition full = pcLock.newCondition();
	private Condition empty = pcLock.newCondition();
	
	
	private PCMonitor() {
		
	}
	
	public void tryProduce() throws InterruptedException {
		this.pcLock.lock();
		
		while(SharedData.counter == SharedData.BUFFER_SIZE) {
			//buffer is full. wait first
			full.await();
		}
		int numberProduce = 5;

		SharedData.numberList[SharedData.counter] = numberProduce;
		SharedData.counter++;
		Debug.log(TAG, "Producer produced. Counter is " +SharedData.counter);
		
		
		this.pcLock.unlock();
	}
	
	public void tryConsume() throws InterruptedException {
		this.pcLock.lock();
		while(SharedData.counter == 0) {
			//buffer empty. wait first
			Debug.log(TAG, "Consumer will wait in empty.");
			empty.await();
		}
		
		int numberToConsume = SharedData.numberList[SharedData.counter];
		SharedData.counter--;
		Debug.log(TAG, "Consumer consumed. Counter is: " +SharedData.counter);
		
		this.pcLock.unlock();
	}
	
	public void reportConsume() {
		this.pcLock.lock();
		full.signal();
		this.pcLock.unlock();
	}
	
	public void reportProduce() {
		this.pcLock.lock();
		empty.signal();
		Debug.log(TAG, "Producer waking up 1 consumer");
		this.pcLock.unlock();
	}
}
