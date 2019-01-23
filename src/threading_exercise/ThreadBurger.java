/**
 * 
 */
package threading_exercise;

/**
 * Produces a burger
 * @author delgallegon
 *
 */
public class ThreadBurger extends Thread {
	
	private int threadID = 0;
	private int interval = 0;
	private boolean running = true;
	private int pattiesRequired = 0;
	
	public ThreadBurger(int threadID, int interval, int pattiesRequired) {
		this.threadID = threadID;
		this.interval = interval;
		this.pattiesRequired = pattiesRequired;
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
				if(tracker.getNumBuns() >= 2 && tracker.getNumPatties() >= this.pattiesRequired) {
					tracker.produceBurger(this.pattiesRequired);
					System.out.println("Produced 1 burger!");
				}
				else {
					if (tracker.getNumBuns() < 2) {
						System.out.println("Cannot produce burger. Not enough buns");
					}
					if(tracker.getNumPatties() < this.pattiesRequired) {
						System.out.println("Cannot produce burger. Not enough patties");
					}
				}
				
				if ((tracker.getNumBuns() < 2 || (tracker.getNumPatties() < this.pattiesRequired))
						&& tracker.reachedMaxBun() && tracker.reachedMaxPatty()) {
					this.running = false;
					System.out.println("Bun and patty production have stopped! Not enough supplies. Closing burger chain.");
					System.out.println("Total burgers produced: " +tracker.getBurgersProduced());
				}
						
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
