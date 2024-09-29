package indicators;

import biuoop.DrawSurface;
import game.GameLevel;
import graphics.Sprite;
import util.Constants;
import util.Counter;

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
     * @param livesCounter - instance of the lives counter.
     */
    public LivesIndicator(Counter livesCounter) {
        this.livesCounter = livesCounter;
    }

    /**
     * Add the lives indicator to the game as a sprite.
     *
     * @param g - instance of a game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Constants.TEXT_COLOR);
        String livesText = "Lives: " + this.livesCounter.toString();
        d.drawText(Constants.LIVES_X, Constants.LIVES_Y, livesText, Constants.TEXT_SIZE);
    }

    @Override
    public void timePassed() {
    }
}
