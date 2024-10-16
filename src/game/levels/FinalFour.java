package game.levels;

import biuoop.DrawSurface;
import game.Block;
import sprites.Sprite;
import sprites.Velocity;
import sprites.geometry.Point;
import sprites.geometry.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Level of a regular sized paddle with several layers of blocks. - Hard.
 * @author Yuval Anteby
 */
public class FinalFour implements LevelInformation {
    /**
     * Blocks size.
     */
    private static final int BLOCK_WIDTH = 50, BLOCK_HEIGHT = 30;
    /**
     * Background's color.
     */
    private static final Color BACKGROUND_COLOR = new Color(0, 130, 255);
    /**
     * Clouds color's.
     */
    private static final Color CLOUD_COLOR_1 = Color.WHITE, CLOUD_COLOR_2 = Color.LIGHT_GRAY,
            CLOUD_COLOR_3 = new Color(211, 207, 198);
    /**
     * Spacing.
     */
    private static final int HORIZONTAL_SPACING = 7, LINES_SPACING = 20;
    /**
     * Radius of clouds.
     */
    private static final int CLOUD_RADIUS = 20, BIGGER_CLOUD_RADIUS = 25;

    @Override
    public int numberOfBalls() {
        return 3;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<>();
        int angle = 30;
        for (int i = 0; i < this.numberOfBalls(); i++) {
            list.add(Velocity.fromAngleAndSpeed(angle, 4));
            angle -= 30;
        }
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 8;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Final Four";
    }

    @Override
    public Sprite getBackground() {
        return new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                //Draw the background color.
                d.setColor(BACKGROUND_COLOR);
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
                //Draw the clouds.
                drawLeftClouds(d);
                drawRightClouds(d);
            }

            @Override
            public void timePassed() {

            }
        };
    }

    /**
     * Draw the clouds of the bottom left corner of the screen.
     * @param d surface to draw on.
     */
    private void drawLeftClouds(DrawSurface d) {
        int xValue = 88, yValue = 450;
        for (int i = 0; i < 10; i++) {
            d.setColor(CLOUD_COLOR_1);
            d.drawLine(xValue, yValue, xValue - LINES_SPACING, 600);
            xValue += HORIZONTAL_SPACING + 1;
        }
        //Draw left cloud in left cluster.
        d.setColor(CLOUD_COLOR_2);
        d.fillCircle(100, 450, CLOUD_RADIUS);
        //Draw middle cloud in left cluster.
        d.fillCircle(120, 440, CLOUD_RADIUS);
        //Draw right cloud in left cluster.
        d.setColor(CLOUD_COLOR_3);
        d.fillCircle(140, 460, BIGGER_CLOUD_RADIUS);
    }

    /**
     * Draw the clouds of the bottom right corner of the screen.
     * @param d surface to draw on.
     */
    private void drawRightClouds(DrawSurface d) {
        int xValue = 688, yValue = 450;
        for (int i = 0; i < 8; i++) {
            d.setColor(CLOUD_COLOR_1);
            d.drawLine(xValue, yValue, xValue - LINES_SPACING, 600);
            xValue += HORIZONTAL_SPACING;
        }
        //Draw left cloud in right cluster.
        d.setColor(CLOUD_COLOR_2);
        d.fillCircle(688, 450, CLOUD_RADIUS);
        //Draw middle cloud in right cluster.
        d.fillCircle(700, 440, CLOUD_RADIUS);
        //Draw right cloud in right cluster.
        d.setColor(CLOUD_COLOR_3);
        d.fillCircle(720, 460, BIGGER_CLOUD_RADIUS);
    }

    @Override
    public List<Block> blocks() {
        List<Block> blockList = new ArrayList<>();
        List<Color> colors = setColorsList();
        int x = 24, y = 100;
        int start = x;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 15; j++) {
                Rectangle rec = new Rectangle(new Point(x, y), BLOCK_WIDTH, BLOCK_HEIGHT);
                Block block = new Block(rec, colors.get(i));
                blockList.add(block);
                x += BLOCK_WIDTH;
            }
            x = start;
            y += BLOCK_HEIGHT;
        }
        return blockList;
    }

    /**
     * @return a list of colors we would like to use.
     */
    private List<Color> setColorsList() {
        return List.of(Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.WHITE,
                new Color(255, 180, 255), new Color(0, 220, 255));
    }

}
