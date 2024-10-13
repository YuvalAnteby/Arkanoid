package game.levels;

import sprites.Velocity;
import biuoop.DrawSurface;
import game.Block;
import sprites.geometry.Point;
import sprites.Sprite;
import util.GameConstants;
import sprites.geometry.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static util.SpriteConstants.BOUND_WIDTH;

/**
 * Level of a wide paddle with a single row of blocks - Easy.
 * @author Yuval Anteby
 */
public class WideEasy implements LevelInformation {
    /**
     * Blocks size.
     */
    private static final int BLOCK_WIDTH = 52, BLOCK_HEIGHT = 30;
    /**
     * Blocks Y value.
     */
    private static final int BLOCK_Y = 250;

    @Override
    public int numberOfBalls() {
        return 5;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return List.of(
                Velocity.fromAngleAndSpeed(60, 3),
                Velocity.fromAngleAndSpeed(45, 3),
                Velocity.fromAngleAndSpeed(0, 3),
                Velocity.fromAngleAndSpeed(-30, 3),
                Velocity.fromAngleAndSpeed(-60, 3));
    }

    @Override
    public int paddleSpeed() {
        return 4;
    }

    @Override
    public int paddleWidth() {
        return 400;
    }

    @Override
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public Sprite getBackground() {
        return new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                createBackground(d);
                createSunSprite(d);
                createBeachSprite(d);
            }

            @Override
            public void timePassed() {

            }
        };
    }

    @Override
    public List<Block> blocks() {
        int blockAmount = 15;
        Color[] blockColors = {
                Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PINK, Color.CYAN, Color.MAGENTA
        };
        List<Block> blocks = new ArrayList<>();
        for (int i = 0; i < blockAmount; i++) {
            int xBlock = i * BLOCK_WIDTH + BOUND_WIDTH;
            Rectangle rec = new Rectangle(new Point(xBlock, BLOCK_Y), BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks.add(new Block(rec, blockColors[i / 2 % blockColors.length]));
        }
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }

    /**
     * Generate a blue background by default for this level.
     *
     * @param d surface we draw on.
     */
    private void createBackground(DrawSurface d) {
        d.setColor(new Color(0, 105, 148));
        d.fillRectangle(0, 0, GameConstants.GUI_WIDTH, GameConstants.GUI_HEIGHT);
    }

    /**
     * Create blue sprite in the shape of a target as part of the background.
     * NOTE: the target sprite cannot be interacted with.
     *
     * @param d surface we draw on.
     */
    private void createSunSprite(DrawSurface d) {
        int xSun = GameConstants.GUI_WIDTH / 4;
        int ySun = GameConstants.GUI_HEIGHT / 4;
        Color raysColor = new Color(225, 217, 157);
        d.setColor(raysColor);
        for (int x = 10; x <= 780; x += 10) {
            d.drawLine(xSun, ySun, x, BLOCK_Y);
        }

        Color[] sunColors = {
                new Color(255, 225, 24),
                new Color(236, 215, 73),
                new Color(225, 217, 157)
        };
        int r = 70;
        for (int i = 2; i >= 0; i--) {
            d.setColor(sunColors[i]);
            d.fillCircle(xSun, ySun, r);
            r -= 10 * i;
        }
    }

    /**
     * Create sprite at the bottom of the screen to look like sand.
     *
     * @param d surface we draw on.
     */
    private void createBeachSprite(DrawSurface d) {
        Color sandColor = new Color(203, 189, 147);
        int r = 80;
        int xValue = 0;
        int yValue = GameConstants.GUI_HEIGHT;
        int sandAmount = GameConstants.GUI_WIDTH / (r / 2);
        for (int i = 0; i < sandAmount; i++) {
            d.setColor(sandColor);
            d.fillCircle(xValue, yValue, r);
            xValue += (r / 2);
            yValue -= 2;
        }
    }
}
