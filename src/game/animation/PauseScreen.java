package game.animation;

import biuoop.DrawSurface;

import java.awt.Color;

import static util.SpriteConstants.KEY_STOPPABLE_FONT_SIZE;
import static util.SpriteConstants.TEXT_COLOR;
import static util.TextValuesEng.PAUSE_MESSAGE;

/**
 * Screen that will be showed when the user pause the game.
 *
 * @author Yuval Anteby
 */
public class PauseScreen implements Animation {

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
        d.setColor(TEXT_COLOR);
        d.drawText(10, d.getHeight() / 2, PAUSE_MESSAGE, KEY_STOPPABLE_FONT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return this.shouldStop;
    }
}
