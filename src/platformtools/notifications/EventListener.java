
package platformtools.notifications;

/**
 * Add this to the one who will listen to a certain notification
 * @author user
 *
 */
public interface EventListener {

	void onNotify(String notificationString, Parameters params);
}
