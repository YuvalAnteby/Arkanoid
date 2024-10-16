package sprites.indicators;

import sprites.collision.HitListener;
import game.Block;
import sprites.geometry.Ball;
import util.Counter;

import static util.GameConstants.BLOCK_REMOVAL_POINTS;

/**
 * Class to keep track of the user's score.
 *
 * @author Yuval Anteby
 */
public class ScoreTrackingListener implements HitListener {
    private final Counter currentScore;

    /**
     * Constructor for the listener.
     *
     * @param scoreCounter a counter for the user's score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void onHit(Block beingHit, Ball hitter) {
        if (beingHit == null || hitter == null) {
            return;
        }
        if (!beingHit.isDeathBlock()) {
            this.currentScore.increase(BLOCK_REMOVAL_POINTS);
        }
    }

    @Override
    public String toString() {
        return "Score: " + this.currentScore.toString();
    }
}
