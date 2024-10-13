package game.levels;

import biuoop.DrawSurface;
import sprites.Velocity;
import sprites.Sprite;
import game.Block;
import sprites.geometry.Point;
import sprites.geometry.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Level of a regular sized paddle with a green background - Medium.
 * @author Yuval Antbey
 */
public class Green3 implements LevelInformation {
    /**
     * Blocks size.
     */
    private static final int BLOCK_WIDTH = 50, BLOCK_HEIGHT = 30;
    /**
     * Background's color.
     */
    private static final Color BACKGROUND_COLOR = new Color(2, 133, 0);

    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<>();
        int speed = 150;
        for (int i = 0; i < this.numberOfBalls(); i++) {
            list.add(Velocity.fromAngleAndSpeed(speed, 4));
            speed += 60;
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
        return "Green 3";
    }

    @Override
    public Sprite getBackground() {
        return new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                d.setColor(BACKGROUND_COLOR);
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
                drawBuilding(d);
            }

            @Override
            public void timePassed() {
            }
        };
    }

    /**
     * Draws a building with windows and an antenna on the level's screen.
     * @param d surface to draw on.
     */
    private void drawBuilding(DrawSurface d) {
        //Draw building's skeleton.
        d.setColor(Color.DARK_GRAY);
        d.fillRectangle(70, 400, 130, 600);
        d.fillRectangle(120, 350, 35, 50);
        d.fillRectangle(130, 250, 15, 100);
        //Draw building's windows.
        int x = 80, y = 410;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                d.setColor(Color.WHITE);
                d.fillRectangle(x, y, 10, 30);
                x += 20;
            }
            x = 80;
            y += 50;
        }
        //Draw circles on building's rooftop.
        d.setColor(Color.ORANGE);
        d.fillCircle(137, 240, 13);
        d.setColor(Color.RED);
        d.fillCircle(137, 240, 9);
        d.setColor(Color.WHITE);
        d.fillCircle(137, 240, 4);
    }

    @Override
    public List<Block> blocks() {
        List<Block> blockList = new ArrayList<>();
        List<Color> colors = setColorsList();
        int blocksInRow = 10, xBlock = 275, yBlock = 200;
        int start = xBlock;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < blocksInRow; j++) {
                Rectangle rec = new Rectangle(new Point(xBlock, yBlock), BLOCK_WIDTH, BLOCK_HEIGHT);
                Block block = new Block(rec, colors.get(i));
                blockList.add(block);
                xBlock += BLOCK_WIDTH;
            }
            yBlock += BLOCK_HEIGHT;
            xBlock = start + (i + 1) * BLOCK_WIDTH;
            blocksInRow--;
        }
        return blockList;
    }

    /**
     * @return a list of colors we would like to use.
     */
    private List<Color> setColorsList() {
        return List.of(Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.WHITE);
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks().size();
    }
}
