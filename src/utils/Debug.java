/**
 * 
 */
package utils;

/**
 * Convenience class for printing in console with a TAG
 * @author NeilDG
 *
 */
public class Debug {

	public static void log(String tag, String message) {
		System.out.println("["+tag+"]: " +message);
	}
}
