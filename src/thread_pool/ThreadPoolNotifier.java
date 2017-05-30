package thread_pool;

/*
 * The ThreadPoolManager class should implement this interface so that when the ThreadAction finishes,
 * it can call this function
 */
public interface ThreadPoolNotifier {
	void onFinished(int threadID);
}
