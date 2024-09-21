package collision;

/**
 * Interface to provide methods for the hit event listeners.
 * @author Yuval Anteby
 */
public interface HitNotifier {

    /**
     * Add a new hit listener to the list of listeners.
     * @param hl - new hit listener to add.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove a hit listener from the list of listeners.
     * @param hl - hit listener to remove.
     */
    void removeHitListener(HitListener hl);
}
