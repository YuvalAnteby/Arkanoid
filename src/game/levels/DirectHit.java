package game.levels;

import sprites.Velocity;
import biuoop.DrawSurface;
import game.Block;
import sprites.geometry.Point;
import sprites.Sprite;
import sprites.geometry.Rectangle;
import util.Constants;

import java.awt.Color;
import java.util.List;

/**
 * Level of a single block - Easy.
 *
 * @author Yuval Anteby
 */
public class DirectHit implements LevelInformation {
    /**
     * Blocks constants.
     */
    private static final int BLOCK_WIDTH = 30;
    private static final int BLOCK_HEIGHT = 30;
    private static final Color BLOCK_COLOR = Color.RED;
    private static final int X_BLOCK = (Constants.GUI_WIDTH / 2) - (BLOCK_WIDTH / 2);
    private static final int Y_BLOCK = 150;
    /**
     * Paddle constants.
     */
    private static final int PADDLE_WIDTH = 80;
    private static final int PADDLE_SPEED = 5;
    /**
     * Balls constants.
     */
    private static final int NUM_OF_BALLS = 1;
    /**
     * Level Design constants.
     */
    private static final Color BACKGROUND_COLOR = Color.BLACK, TARGET_COLOR = Color.BLUE;

    @Override
    public int numberOfBalls() {
        return NUM_OF_BALLS;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return List.of(new Velocity(0, 3));
    }

    @Override
    public int paddleSpeed() {
        return PADDLE_SPEED;
    }

    @Override
    public int paddleWidth() {
        return PADDLE_WIDTH;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        return new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                createBackground(d);
                createTargetSprite(d);
            }

            @Override
            public void timePassed() {
            }
        };
    }

    @Override
    public List<Block> blocks() {
        return List.of(new Block(new Rectangle(new Point(X_BLOCK, Y_BLOCK), BLOCK_WIDTH, BLOCK_HEIGHT), BLOCK_COLOR));
    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }

    /**
     * Generate a black background by default for this level.
     *
     * @param d - surface we draw on.
     */
    private void createBackground(DrawSurface d) {
        d.setColor(BACKGROUND_COLOR);
        d.fillRectangle(0, 0, Constants.GUI_WIDTH, Constants.GUI_HEIGHT);
    }

    /**
     * Create blue sprite in the shape of a target as part of the background.
     * NOTE: the target sprite cannot be interacted with.
     *
     * @param d - surface we draw on.
     */
    private void createTargetSprite(DrawSurface d) {
        d.setColor(TARGET_COLOR);
        int r = 40;
        for (int i = 0; i < 3; i++) {
            d.drawCircle(Constants.GUI_WIDTH / 2, Y_BLOCK + (BLOCK_HEIGHT / 2), r);
            r += 20;
        }
        int targetPadding = 10;
        int xVerticalLines = Constants.GUI_WIDTH / 2;
        int yHorizontalLines = Y_BLOCK + (BLOCK_HEIGHT / 2);
        //right line
        int xRightLineStart = (Constants.GUI_WIDTH / 2) + (BLOCK_WIDTH / 2) + targetPadding;
        int xRightLineEnd = xRightLineStart + r;
        d.drawLine(xRightLineStart, yHorizontalLines, xRightLineEnd, yHorizontalLines);
        //top line
        int yTopLineEnd = Y_BLOCK - targetPadding;
        int yTopLineStart = yTopLineEnd - r;
        d.drawLine(xVerticalLines, yTopLineStart, xVerticalLines, yTopLineEnd);
        //left line
        int xLeftLineEnd = X_BLOCK - targetPadding;
        int xLeftLineStart = xLeftLineEnd - r;
        d.drawLine(xLeftLineStart, yHorizontalLines, xLeftLineEnd, yHorizontalLines);
        //bottom line
        int yBottomLineStart = Y_BLOCK + BLOCK_HEIGHT + targetPadding;
        int yBottomLineEnd = yBottomLineStart + r;
        d.drawLine(xVerticalLines, yBottomLineStart, xVerticalLines, yBottomLineEnd);
    }
}
