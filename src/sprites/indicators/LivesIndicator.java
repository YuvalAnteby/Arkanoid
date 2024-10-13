package sprites.indicators;

import biuoop.DrawSurface;
import game.GameLevel;
import sprites.Sprite;
import util.Counter;

import static util.SpriteConstants.INDICATOR_TXT_SIZE;
import static util.SpriteConstants.LIVES_X;
import static util.SpriteConstants.LIVES_Y;
import static util.SpriteConstants.TEXT_COLOR;

/**
 * Class to represent the lives a user have left.
 *
 * @author Yuval Anteby
 */
public class LivesIndicator implements Sprite {

    private final Counter livesCounter;

    /**
     * Constructor for the class.
     *
     * @param livesCounter instance of the lives counter.
     */
    public LivesIndicator(Counter livesCounter) {
        this.livesCounter = livesCounter;
    }

    /**
     * Add the lives indicator to the game as a sprite.
     *
     * @param g instance of a game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(TEXT_COLOR);
        String livesText = "Lives: " + this.livesCounter.toString();
        d.drawText(LIVES_X, LIVES_Y, livesText, INDICATOR_TXT_SIZE);
    }

    @Override
    public void timePassed() {
    }
}
