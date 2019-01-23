/**
 * 
 */
package threading_exercise;

/**
 * Entry point for the threading exercise for INTROOS/OPERSYS
 * @author delgallegon
 *
 */
public class ExerciseMain {
	private final static String TAG = "ExerciseMain";
	
	public ExerciseMain() {
		
	}
	
	//called in the main() program
	public void startExercise() {
		this.trafficExercise();
		//this.burgerExercise();
	}
	
	private void trafficExercise() {
		TrafficLight light = new TrafficLight(0, 1500);
		light.start();
		
		VehicleLine line = new VehicleLine(1, 250, 100, 20, light);
		line.start();
	}
	
	private void burgerExercise() {
		BunPattyTracker.initialize(5, 6);
		
		ThreadPatty pattyCreator = new ThreadPatty(0, 500);
		pattyCreator.start();
		
		ThreadBun bunCreator = new ThreadBun(0, 500);
		bunCreator.start();
		
		ThreadBurger burgerCreator = new ThreadBurger(0, 250, 1);
		burgerCreator.start();
		
	}
}
