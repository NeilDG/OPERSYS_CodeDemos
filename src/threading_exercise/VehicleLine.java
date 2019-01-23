/**
 * 
 */
package threading_exercise;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author NeilDG
 *
 */
public class VehicleLine extends Thread {

	private boolean running = true;
	
	private int queueInterval = 0;
	private int passInterval = 0;
	
	private int counter = 0;
	private int limit = 0;
		
	private Queue<Vehicle> vehicleLine = new LinkedList<Vehicle>();
	private TrafficLight light;
	
	public VehicleLine(int queue, int pass, int limit, TrafficLight light) {
		this.queueInterval = queue;
		this.passInterval = pass;
		this.limit = limit;
		this.light = light;
	}
	
	@Override
	public void run() {
		while(this.running) {
			
			try {
				Thread.sleep(this.queueInterval);
				if(this.counter < this.limit) {
					Vehicle vehicle = new Vehicle(this.counter + 1);
					vehicleLine.add(vehicle);
					this.counter++;
					System.out.println("Vehicle " +vehicle.ID + " queued.");
				}
				
				if(this.light.isGreen() && !this.vehicleLine.isEmpty()) {
					Vehicle vehicle = this.vehicleLine.remove();
					System.out.println("Vehicle " +vehicle.ID+ " passed!");
					Thread.sleep(this.passInterval);
				}
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public class Vehicle {
		public int ID;
		
		public Vehicle(int ID) {
			this.ID = ID;
		}
	}
}
