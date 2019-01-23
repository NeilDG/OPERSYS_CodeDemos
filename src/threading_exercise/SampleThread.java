/**
 * 
 */
package threading_exercise;

/**
 * @author NeilDG
 *
 */
public class SampleThread extends Thread{
	
	private int threadID = 0;
	
	public SampleThread(int ID) {
		this.threadID = ID;
	}
	
	@Override
	//DO NOT CALL RUN DIRECTLY
	public void run() {
		System.out.println("Thread ID is " +this.threadID);
	}
}
