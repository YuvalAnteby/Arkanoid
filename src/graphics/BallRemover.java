package graphics;

import collision.HitListener;
import game.Block;
import game.Game;
import geometry.Ball;
import util.Counter;

/**
 * Class in charge of removing balls from the game, as well as keeping count of the number of balls that remain.
 * @author Yuval Anteby
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * Constructor for the class.
     * @param game            - game reference the ball is in.
     * @param remainingBalls  - number of remaining balls in the game.
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
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
            hitter.removeFromGame(this.game);
            this.remainingBalls.decrease(1);
        }
    }
}
