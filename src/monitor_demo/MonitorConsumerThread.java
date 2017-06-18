/**
 * 
 */
package monitor_demo;

import critical_section_demo.SharedData;
import utils.Debug;

/**
 * Demo of a monitor in the producer-consumer problem
 * @author NeilDG
 *
 */
public class MonitorConsumerThread extends Thread {
	private final static String TAG = "MonitorConsumerThread";
	
	public MonitorConsumerThread() {
		
	}
	
	@Override
	public void run() {
		//execute N times
		for(int i = 0; i < 4; i++) {
			try {
				Thread.sleep(1);
				PCMonitor.getInstance().tryConsume();
				PCMonitor.getInstance().reportConsume();


			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
