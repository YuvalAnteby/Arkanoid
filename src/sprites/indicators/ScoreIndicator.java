package sprites.indicators;

import biuoop.DrawSurface;
import game.GameLevel;
import sprites.Sprite;
import util.Constants;
import util.Counter;

/**
 * Class to represent the score shown in the GUI.
 *
 * @author Yuval Anteby
 */
public class ScoreIndicator implements Sprite {
    private final Counter scoreCounter;

    /**
     * Constructor for the class.
     *
     * @param scoreCounter instance of the score counter.
     */
    public ScoreIndicator(Counter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    /**
     * Add the score indicator to the game as a sprite.
     *
     * @param g instance of a game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Constants.INDICATOR_BACKGROUND_COLOR);
        d.fillRectangle(Constants.INDICATORS_X, Constants.INDICATORS_Y, Constants.INDICATOR_WIDTH, Constants.TEXT_SIZE);
        d.setColor(Constants.TEXT_COLOR);
        String scoreText = "Score: " + this.scoreCounter.toString();
        d.drawText(Constants.SCORE_X, Constants.SCORE_Y, scoreText, Constants.TEXT_SIZE);
    }

    @Override
    public void timePassed() {
    }
}
