/**
 * 
 */
package critical_section_demo;

import java.util.concurrent.Semaphore;

/**
 * Shared data class to hold variables by producer-consumer
 * @author NeilDG
 *
 */
public class SharedData {
	private final static String TAG = "SharedData";
	
	public final static int BUFFER_SIZE = 10;
	public static int counter;
	
	public static int[] numberList;
	
	//peterson's solution. applicable to two processes only
	public final static int PRODUCER_ID = 0;
	public final static int CONSUMER_ID = 1;
	public static boolean[] flag = new boolean[2];
	public static int turn = 0;
	
	public static void setup() {
		counter = 0;
		numberList = new int[BUFFER_SIZE];
		flag[PRODUCER_ID] = false; //process 1 flag
		flag[CONSUMER_ID] = false; //process 2 flag
		turn = PRODUCER_ID; //whose process turn is it?
	}
}
