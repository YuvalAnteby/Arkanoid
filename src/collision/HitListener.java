package collision;

import game.Block;
import geometry.Ball;

/**
 * Interface to manage hit events by using its methods.
 * @author Yuval Anteby
 */
public interface HitListener {

    /**
     * Function for handling each hit event.
     * @param beingHit - block that is being hit.
     * @param hitter   - ball that hit.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
