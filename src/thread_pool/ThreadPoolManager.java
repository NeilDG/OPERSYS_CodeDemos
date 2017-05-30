/**
 * 
 */
package thread_pool;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import utils.Debug;

/**
 * Manager for handling thread pools
 * @author NeilDG
 *
 */
public class ThreadPoolManager extends Thread implements ThreadPoolNotifier {
	private final static String TAG = "ThreadPoolManager";
	
	private static ThreadPoolManager sharedInstance = null;
	public static ThreadPoolManager getInstance() {
		return sharedInstance;
	}
	
	private int poolSize = 0;
	private boolean hasOrderedStop = false;
	
	private Queue<ThreadAction> pendingActions = new LinkedList<ThreadAction>();
	private Queue<PoolThread> inactiveThreads = new LinkedList<PoolThread>();
	private HashMap<Integer, PoolThread> activeThreads = new HashMap<Integer,PoolThread>();
	
	private ThreadPoolManager(int poolSize) {
		this.poolSize = poolSize;
		
		this.createPool();
	}
	
	public static void initialize(int poolSize) {
		sharedInstance = new ThreadPoolManager(poolSize);
	}
	
	public static void destroyPool() {
		if(sharedInstance != null) {
			sharedInstance.stopPoolExecutor();
			sharedInstance = null;
		}
	}
	
	public void createPool() {
		this.inactiveThreads.clear();
		this.activeThreads.clear();
		
		//create inactive threads
		for(int i = 0; i < this.poolSize; i++) {
				this.inactiveThreads.add(new PoolThread(i, this));	
		}
	}
	
	/*
	 * Starts the thread that continuously checks if there are any queued actions
	 */
	public void startPoolExecutor() {
		this.start();
	}
	
	/*
	 * Issues a stop order if the pool executor is running
	 */
	public void stopPoolExecutor() {
		this.hasOrderedStop = true;
	}
	
	public void addThreadAction(ThreadAction threadAction) {
		this.pendingActions.add(threadAction);
	}
	
	
	@Override
	public void run() {
		//continuously run this thread until an ordered stop was called.
		while(true && this.hasOrderedStop == false) {
			
			//are there any pending actions?
			if(this.pendingActions.isEmpty() == false) {
				
				//is there an inactive thread we can use from the pool?
				if(this.inactiveThreads.isEmpty() == false) {
					
					ThreadAction threadAction = this.pendingActions.poll();
					PoolThread poolThread = this.inactiveThreads.poll();
					
					Debug.log(TAG, "New action executed in thread. Remaining actions: " +this.pendingActions.size());
					
					//assign the action and then start the pool thread. Put to active threads pool.
					poolThread.setThreadAction(threadAction);
					poolThread.start();
					this.activeThreads.put(poolThread.getThreadID(), poolThread);
				}
				else {
					//Debug.log(TAG, "No thread is available. Will try again.");
				}
			}
			else {
				Debug.log(TAG, "No pending actions found in queue. Available threads: " +this.inactiveThreads.size());
			}
			
			//for debugging purposes
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		Debug.log(TAG, "Stopped pool executor.");
		this.createPool(); //reset by recreating the pool list again
	}
	
	public void onFinished(int threadID) {
		if(this.activeThreads.containsKey(threadID)) {
			PoolThread inactiveThread = this.activeThreads.remove(threadID);
			
			/*NOTE: Re-adding a thread that has finished execution results in an IllegalThreadException. Because
			 * Java does not allow a thread's state to be declared as NEW again. It always ends up as TERMINATED.
			 * For now, since we do not have background on Semaphore's YET, let's create a new thread and
			 * put it in the inactive thread pool.
			 */
			//this.inactiveThreads.add(inactiveThread);
			
			this.inactiveThreads.add(new PoolThread(threadID, this));
		}
		else {
			Debug.log(TAG, threadID + " not found in active pool. Was it removed prematurely?");
		}
	}
	
	
	/*
	 * Inner class that executes an assigned ThreadAction interface
	 */
	private class PoolThread extends Thread {
		private final static String TAG = "PoolThread";
		
		private int threadID;
		private ThreadAction threadAction;
		private ThreadPoolNotifier notifier;
		
		public PoolThread(int threadID, ThreadPoolNotifier notifier) {
			this.threadID = threadID;
			this.notifier = notifier;
			
			//Debug.log(TAG, "Created pool thread ID: " +this.threadID);
		}
		
		public int getThreadID() {
			return this.threadID;
		}
		
		public void setThreadAction(ThreadAction threadAction) {
			this.threadAction = threadAction;
		}
		
		@Override
		public void run() {
			
			if(this.threadAction != null) {
				this.threadAction.execute();
			}
			else {
				Debug.log(TAG, "ERROR. No thread action was assigned.");
			}
			
			this.notifier.onFinished(this.threadID);
		}
	}
}
