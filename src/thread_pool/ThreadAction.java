package thread_pool;

/**
 * Any class that needs to be executed by a thread pool should implement this interface
 * @author NeilDG
 */
public interface ThreadAction {
	void execute();
}
