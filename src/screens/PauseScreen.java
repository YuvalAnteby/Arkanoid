package screens;

import animation.Animation;
import biuoop.DrawSurface;
import util.Constants;

import java.awt.*;

/**
 * Screen that will be showed when the user pause the game.
 */
public class PauseScreen implements Animation {
    private static final String PAUSE_MESSAGE = "Game Paused";

    private boolean stop;

    /**
     * Constructor for the screen.
     */
    public PauseScreen() {
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Constants.TEXT_COLOR);
        d.drawText(10, d.getHeight() / 2, PAUSE_MESSAGE, Constants.MESSAGE_FONT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
