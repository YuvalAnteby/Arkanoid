package score;

import collision.HitListener;
import game.Block;
import geometry.Ball;
import util.Counter;

/**
 * Class to keep track of the user's score.
 * @author Yuval Anteby
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructor for the listener.
     *
     * @param scoreCounter - a counter for the user's score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Getter for the current score counter.
     * @return - counter instance of the score.
     */
    public Counter getScoreCounter() {
        return this.currentScore;
    }

    /**
     * Add points to the score for clearing all the blocks.
     * According to the assigment the score will increase by 100 points.
     */
    public void levelCleared() {
        this.currentScore.increase(100);
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (!beingHit.isDeathBlock()) {
            this.currentScore.increase(5);
        }
    }

    @Override
    public String toString() {
        return "Score: " + this.currentScore.toString();
    }
}
