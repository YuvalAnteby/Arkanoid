package animation;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import graphics.SpriteCollection;
import util.Constants;

import java.awt.*;

/**
 * Countdown for the start of the game.
 * The CountdownAnimation will display the given gameScree, for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will appear on the screen for (numOfSeconds / countFrom)
 * seconds, before it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {

    private double seconds;
    private int from;
    private SpriteCollection screen;
    private boolean stop;
    private int passed;

    /**
     * Constructor for the class.
     * @param numOfSeconds  - number of seconds to display.
     * @param countFrom     - start the count from.
     * @param gameScreen    - sprites of the game to display in background.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.seconds = numOfSeconds;
        this.from = countFrom;
        this.screen = gameScreen;
        this.stop = false;
        this.passed = 0;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        if (this.passed == this.from) {
            this.stop = true;
        }
        //draws the screen
        Sleeper sleep = new Sleeper();
        this.screen.drawAllOn(d);

        //hold the count for second
        d.setColor(Color.WHITE);
        //draw the number of seconds left at the centered, slightly to the bottom to be below the blocks.
        int topLeftX = Constants.GUI_WIDTH / 2;
        int topLeftY = (Constants.GUI_HEIGHT / 2) + 100;
        d.drawText(topLeftX, topLeftY, Integer.toString(this.from - this.passed), 100);
        sleep.sleepFor((long) ((this.seconds / this.from) * 1000));
        this.passed++;

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
