
package platformtools.notifications;

import java.util.ArrayList;
import java.util.HashMap;

import utils.Debug;


/**
 * Represents the notification center just like Cocos2D and iOS
 * @author NeilDG
 *
 */
public class EventBroadcaster {

	private static EventBroadcaster sharedInstance = null;
	
	private final static String TAG = "NotificationCenter";
	
	private HashMap<String, ArrayList<EventListener>> notificationMap;

	public static EventBroadcaster getInstance() {
		if(sharedInstance == null) {
			sharedInstance = new EventBroadcaster();
		}

		return sharedInstance;
	}

	private EventBroadcaster() {
		this.notificationMap = new HashMap<String, ArrayList<EventListener>>();
	}
	
	public void addObserver(String notificationString, EventListener listener) {
		
		//if there is already an existing key, put listener to array list
		if(this.notificationMap.containsKey(notificationString)) {
			ArrayList<EventListener> listenerList = this.notificationMap.get(notificationString);
			listenerList.add(listener);
		}
		//create new arraylist
		else {
			ArrayList<EventListener> listenerList = new ArrayList<EventListener>();
			listenerList.add(listener);
			this.notificationMap.put(notificationString, listenerList);
		}
	}
	
	public void removeObserver(String notificationString, EventListener listener) {
		if(this.notificationMap.containsKey(notificationString)) {
			ArrayList<EventListener> listenerList = this.notificationMap.get(notificationString);
			if(listenerList.remove(listener)) {
				Debug.log(TAG, "Removed observer " +listener);
			}
			else {
				Debug.log(TAG, "Listener not found. Doing nothing");
			}
		}
	}
	
	public void clearObservers() {
		this.notificationMap.clear();
	}

	private void actualNotify(String notificationString, Parameters parameters) {
		ArrayList<EventListener> listenerList = notificationMap.get(notificationString);

		if(listenerList != null) {
			for(EventListener listener : listenerList) {
				listener.onNotify(notificationString, parameters);
			}
		}

		parameters.clear();
	}

	public void postNotification(final String notificationString, final Parameters parameters) {
		this.actualNotify(notificationString, parameters);
	}

	private void actualNotify(String notificationString) {
		ArrayList<EventListener> listenerList = notificationMap.get(notificationString);

		if(listenerList != null) {
			for(EventListener listener : listenerList) {
				listener.onNotify(notificationString, null);
			}
		}
	}
	
	public void postNotification(final String notificationString) {
			this.actualNotify(notificationString);
	}
}
