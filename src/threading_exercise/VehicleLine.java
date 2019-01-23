/**
 * 
 */
package threading_exercise;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author delgallegon
 *
 */
public class VehicleLine extends Thread {
	
	private int threadID = 0;
	private int queueInterval = 0;
	private int goInterval = 0;
	private boolean running = true;
	
	private int maxCounter = 0;

	private Queue<Vehicle> waitingLine = new LinkedList<Vehicle>();
	private TrafficLight light;
	
	public VehicleLine(int threadID, int queueInterval, int goInterval, int maxVehicles, TrafficLight light) {
		this.threadID = threadID;
		this.queueInterval = queueInterval;
		this.goInterval = goInterval;
		this.maxCounter = maxVehicles;
		this.light = light;
	}
	
	@Override
	public void run() {
		
		int ID = 1;
		
		while(this.running) {
			try {
				Thread.sleep(this.queueInterval);
				
				if(ID <= this.maxCounter) {
					Vehicle vehicle = new Vehicle(ID);
					this.waitingLine.add(vehicle);
					ID++;
					System.out.println("Vehicle " +ID+ " queued.");
				}
				
				
				if(light.isGreen()) {
					Vehicle vehicle = this.waitingLine.remove();
					System.out.println("Vehicle " +vehicle.ID+ " passed!");
					
					Thread.sleep(this.goInterval);
				}
				
				if(ID > this.maxCounter && this.waitingLine.isEmpty()) {
					System.out.println("No more cars");
					this.running = false;
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
