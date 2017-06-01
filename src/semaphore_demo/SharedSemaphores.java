/**
 * 
 */
package semaphore_demo;

import java.util.concurrent.Semaphore;

import critical_section_demo.SharedData;

/**
 * Holder for shared semaphores by threads
 * @author NeilDG
 *
 */
public class SharedSemaphores {
	private final static String TAG = "SharedSemaphores";
	
	private static SharedSemaphores sharedInstance = null;
	public static SharedSemaphores getInstance() {
		if(sharedInstance == null) {
			sharedInstance = new SharedSemaphores();
		}
		return sharedInstance;
	}
	
	private SharedSemaphores() {
		
	}
	
	private Semaphore fullSem = new Semaphore(0);
	private Semaphore emptySem = new Semaphore(SharedData.BUFFER_SIZE);
	private Semaphore mutex = new Semaphore(1); //mutex is required for multiple producer-consumer
	
	public Semaphore getFullSem() {
		return this.fullSem;
	}
	
	public Semaphore getEmptySem() {
		return this.emptySem;
	}
	
	public Semaphore getMutex() {
		return this.mutex;
	}

}
