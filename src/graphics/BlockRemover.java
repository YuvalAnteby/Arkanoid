package graphics;

import collision.HitListener;
import game.Block;
import geometry.Ball;
import util.Counter;
import game.GameLevel;

/**
 * Class in charge of removing blocks from the game, as well as keeping count of the number of blocks that remain.
 * @author Yuval Anteby
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Constructor for the class.
     * @param gameLevel            - game reference the block is in.
     * @param remainingBlocks - number of remaining blocks in the game.
     */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Getter for the counter.
     * @return - counter instance of remaining blocks.
     */
    public Counter getRemainingBlocks() {
        return remainingBlocks;
    }

    // Blocks that are hit should be removed from the game.
    // Remember to remove this listener from the block that is being removed from the game.
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.setColor(beingHit.getColor());
        beingHit.removeFromGame(this.gameLevel);
        this.remainingBlocks.decrease(1);
    }

    /**
     * Check if there are blocks remaining in the game.
     * @return true if there are no blocks anymore, otherwise false.
     */
    public boolean noBlocksRemain() {
        return this.remainingBlocks.getValue() <= 0;
    }


}
