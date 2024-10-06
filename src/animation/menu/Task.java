package animation.menu;

/**
 * Interface to represent a menu task. Will be used as a lambda.
 *
 * @param <T> generic for the task we want to do.
 * @author Yuval Anteby
 */
public interface Task<T> {

    /**
     * Run the selected task.
     *
     * @return generic task.
     */
    T run();
}
