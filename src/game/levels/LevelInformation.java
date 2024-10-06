package game.levels;

import sprites.Velocity;
import game.Block;
import sprites.Sprite;

import java.util.List;

/**
 * Contains all needed functions for level classes.
 */
public interface LevelInformation {

    /**
     * @return - number of ball in the level.
     */
    int numberOfBalls();

    /**
     * <p></p>.
     * Note that initialBallVelocities().size() == numberOfBalls()
     * @return - list of initial velocities of all balls.
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return - paddle's movement speed.
     */
    int paddleSpeed();

    /**
     * @return - paddle's width.
     */
    int paddleWidth();

    /**
     * The name will be display at the top of the screen.
     * @return - level's name.
     */
    String levelName();

    /**
     * @return - sprite with the background of the level.
     */
    Sprite getBackground();

    /**
     * @return - list of blocks in this level.
     */
    List<Block> blocks();

    /**
     * The number of blocks that will be required to consider this level as "cleared" (a win of the user).
     * This number should be <= blocks.size();
     * @return - number of blocks that should be removed.
     */
    int numberOfBlocksToRemove();
}
