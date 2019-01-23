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
	}
	
	private void trafficExercise() {
		System.out.println("This is a test run.");
		
		TrafficLight light = new TrafficLight(1500);
		light.start();
		
		VehicleLine line = new VehicleLine(250, 100, 20, light);
		line.start();
		
	}
}
