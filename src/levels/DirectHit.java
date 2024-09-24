package levels;

import animation.Velocity;
import biuoop.DrawSurface;
import game.Block;
import geometry.Point;
import graphics.Sprite;
import geometry.Rectangle;
import util.Constants;

import java.awt.*;
import java.util.List;

public class DirectHit implements LevelInformation {
    private final static int BLOCK_WIDTH = 30;
    private final static int BLOCK_HEIGHT = 30;
    private final static Color BLOCK_COLOR = Color.RED;
    private final static int X_BLOCK = (Constants.GUI_WIDTH / 2) - (BLOCK_WIDTH / 2);
    private final static int Y_BLOCK = 150;
    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return List.of(new Velocity(0, -3));
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
                //d.setColor(Color.BLACK);
               // d.fillRectangle(0, 0, Constants.GUI_WIDTH, Constants.GUI_HEIGHT);
                d.setColor(Color.BLUE);
                //draw circles
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
}
