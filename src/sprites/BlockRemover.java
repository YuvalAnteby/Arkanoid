package sprites;

import sprites.collision.HitListener;
import game.Block;
import sprites.geometry.Ball;
import util.Counter;
import game.GameLevel;

/**
 * Class in charge of removing blocks from the game, as well as keeping count of the number of blocks that remain.
 *
 * @author Yuval Anteby
 */
public class BlockRemover implements HitListener {
    private final GameLevel gameLevel;
    private final Counter remainingBlocks;

    /**
     * Constructor for the class.
     *
     * @param gameLevel       game reference the block is in.
     * @param remainingBlocks number of remaining blocks in the game.
     */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }

    // Blocks that are hit should be removed from the game.
    // Remember to remove this listener from the block that is being removed from the game.
    @Override
    public void onHit(Block beingHit, Ball hitter) {
        if (beingHit == null || hitter == null) {
            return;
        }
        hitter.setColor(beingHit.getColor());
        beingHit.removeFromGame(this.gameLevel);
        this.remainingBlocks.decrease(1);
    }
}
