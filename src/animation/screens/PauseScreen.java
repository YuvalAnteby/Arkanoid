package animation.screens;

import animation.Animation;
import biuoop.DrawSurface;
import util.Constants;

import java.awt.Color;

/**
 * Screen that will be showed when the user pause the game.
 *
 * @author Yuval Anteby
 */
public class PauseScreen implements Animation {
    private static final String PAUSE_MESSAGE = "Game Paused";

    private boolean shouldStop;

    /**
     * Constructor for the screen.
     */
    public PauseScreen() {
        this.shouldStop = false;
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
        return this.shouldStop;
    }
}
