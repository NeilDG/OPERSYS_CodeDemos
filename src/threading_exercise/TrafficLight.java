/**
 * 
 */
package threading_exercise;

/**
 * @author NeilDG
 *
 */
public class TrafficLight extends Thread {
	
	private boolean green = false;
	private boolean running = true;
	
	private int interval = 0;
	
	public TrafficLight(int interval) {
		this.interval = interval;
	}
	
	public void toggleLight() {
		this.green = !this.green;
	}
	
	public void printLight() {
		if(this.green) {
			System.out.println("Green light. Go!");
		}
		else {
			System.out.println("Red light. Stop!");
		}
	}
	
	public boolean isGreen() {
		return this.green;
	}
	
	@Override
	public void run() {
		while(this.running) {
			try {
				Thread.sleep(this.interval);
				this.toggleLight();
				this.printLight();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
