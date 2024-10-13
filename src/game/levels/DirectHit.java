package game.levels;

import sprites.Velocity;
import biuoop.DrawSurface;
import game.Block;
import sprites.geometry.Point;
import sprites.Sprite;
import sprites.geometry.Rectangle;
import util.GameConstants;

import java.awt.Color;
import java.util.List;

/**
 * Level of a single block - Easy.
 *
 * @author Yuval Anteby
 */
public class DirectHit implements LevelInformation {
    /**
     * Block's size.
     */
    private static final int BLOCK_WIDTH = 30, BLOCK_HEIGHT = 30;
    /**
     * Block's color.
     */
    private static final Color BLOCK_COLOR = Color.RED;
    /**
     * Block's location.
     */
    private static final int X_BLOCK = (GameConstants.GUI_WIDTH / 2) - (BLOCK_WIDTH / 2), Y_BLOCK = 150;
    /**
     * Level Design colors.
     */
    private static final Color BACKGROUND_COLOR = Color.BLACK, TARGET_COLOR = Color.BLUE;

    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return List.of(new Velocity(0, 5));
    }

    @Override
    public int paddleSpeed() {
        return 5;
    }

    @Override
    public int paddleWidth() {
        return 80;
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
     * @param d surface we draw on.
     */
    private void createBackground(DrawSurface d) {
        d.setColor(BACKGROUND_COLOR);
        d.fillRectangle(0, 0, GameConstants.GUI_WIDTH, GameConstants.GUI_HEIGHT);
    }

    /**
     * Create blue sprite in the shape of a target as part of the background.
     * NOTE: the target sprite cannot be interacted with.
     *
     * @param d surface we draw on.
     */
    private void createTargetSprite(DrawSurface d) {
        d.setColor(TARGET_COLOR);
        int r = 40;
        for (int i = 0; i < 3; i++) {
            d.drawCircle(GameConstants.GUI_WIDTH / 2, Y_BLOCK + (BLOCK_HEIGHT / 2), r);
            r += 20;
        }
        int targetPadding = 10;
        int xVerticalLines = GameConstants.GUI_WIDTH / 2;
        int yHorizontalLines = Y_BLOCK + (BLOCK_HEIGHT / 2);
        //right line
        int xRightLineStart = (GameConstants.GUI_WIDTH / 2) + (BLOCK_WIDTH / 2) + targetPadding;
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
