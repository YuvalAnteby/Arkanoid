package graphics;

import collision.HitListener;
import game.Block;
import game.GameLevel;
import geometry.Ball;
import util.Counter;

/**
 * Class in charge of removing balls from the game, as well as keeping count of the number of balls that remain.
 * @author Yuval Anteby
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Constructor for the class.
     * @param gameLevel            - game reference the ball is in.
     * @param remainingBalls  - number of remaining balls in the game.
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Getter for the counter.
     * @return - counter instance of remaining blocks.
     */
    public Counter getRemainingBalls() {
        return remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.isDeathBlock()) {
            hitter.removeFromGame(this.gameLevel);
            this.remainingBalls.decrease(1);
        }
    }

    /**
     * Check if there are balls remaining in the game.
     * @return true if there are no balls anymore, otherwise false.
     */
    public boolean noBallsRemain() {
        return this.remainingBalls.getValue() <= 0;
    }
}
