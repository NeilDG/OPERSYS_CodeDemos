/**
 * 
 */
package threading_exercise;

/**
 * ThreadPatty example
 * @author delgallegon
 *
 */
public class ThreadPatty extends Thread {
	private final static String TAG = "ThreadPatty";
	
	private int threadID = 0;
	private int interval = 0;
	private boolean running = true;
	
	public ThreadPatty(int threadID, int interval) {
		this.threadID = threadID;
		this.interval = interval;
	}

	@Override
	public void run() {
		while(this.running) {
			
			try {
				Thread.sleep(this.interval);
				
				BunPattyTracker tracker = BunPattyTracker.getInstance();
				
				if(tracker.reachedMaxPatty()) {
					this.running = false;
					System.out.println("Max patty produced! Stopping");
				}
				else {
					BunPattyTracker.getInstance().addPatty();
					System.out.println("Produced patty!");
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
}
