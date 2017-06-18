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
public class MonitorProducerThread extends Thread {
	private final static String TAG = "MonitorProducerThread";
	
	public MonitorProducerThread() {
		
	}
	
	@Override
	public void run() {
		//execute N times
		for(int i = 0; i < 4; i++) {
			try {
				Thread.sleep(1);
				
				PCMonitor.getInstance().tryProduce();
				
				PCMonitor.getInstance().reportProduce();


			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
