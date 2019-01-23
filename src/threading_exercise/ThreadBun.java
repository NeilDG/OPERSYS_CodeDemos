/**
 * 
 */
package threading_exercise;

/**
 * Thread bun example
 * @author delgallegon
 *
 */
public class ThreadBun extends Thread {

	private int threadID = 0;
	private int interval = 0;
	private boolean running = true;
	
	public ThreadBun(int threadID, int interval) {
		this.threadID = threadID;
		this.interval = interval;
	}
	
	public void terminate() {
		this.running = false;
	}
	
	@Override
	public void run() {
		while(this.running) {
			try {
				Thread.sleep(this.interval);
				
				BunPattyTracker tracker = BunPattyTracker.getInstance();
				
				if(tracker.reachedMaxBun()) {
					this.running = false;
					System.out.println("Max bun produced! Stopping");
				}
				else {
					BunPattyTracker.getInstance().addBuns();
					System.out.println("Produced bun!");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
