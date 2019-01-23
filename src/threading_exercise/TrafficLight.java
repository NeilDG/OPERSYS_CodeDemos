/**
 * 
 */
package threading_exercise;

/**
 * TrafficLight thread
 * @author delgallegon
 *
 */
public class TrafficLight extends Thread {

	private int threadID = 0;
	private int interval = 0;
	private boolean running = true;
	
	private boolean green = false;
	
	public TrafficLight(int threadID, int interval) {
		this.threadID = threadID;
		this.interval = interval;
	}
	
	public boolean isGreen() {
		return this.green;
	}
	
	private void toggleColor() {
		this.green = !this.green;
	}
	
	private void printColor() {
		if(this.green) {
			System.out.println("Green light. Go!");
		}
		else {
			System.out.println("Red light. Stop!");
		}
	}
	
	@Override
	public void run() {
		while(this.running) {
			try {
				Thread.sleep(this.interval);
				this.toggleColor();
				this.printColor();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
