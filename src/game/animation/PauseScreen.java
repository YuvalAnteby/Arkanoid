package game.animation;

import biuoop.DrawSurface;

import java.awt.Color;

import static util.GameConstants.GAME_IMAGE;
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
        drawBackground(d);
        d.setColor(Color.WHITE);
        //Show pause text
        d.drawText(10, d.getHeight() / 2, PAUSE_MESSAGE, KEY_STOPPABLE_FONT_SIZE);
    }

    /**
     * Create the black background with the game's logo.
     *
     * @param d surface to draw on.
     */
    private void drawBackground(DrawSurface d) {
        //Create black background.
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        //Add Arkanoid logo.
        d.drawImage(0, 0, GAME_IMAGE);
    }


    @Override
    public boolean shouldStop() {
        return this.shouldStop;
    }
}
