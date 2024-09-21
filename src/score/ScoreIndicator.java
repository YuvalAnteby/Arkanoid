package score;

import biuoop.DrawSurface;
import game.Game;
import graphics.Sprite;
import util.Constants;
import util.Counter;

/**
 * Class to represent the score shown in the GUI.
 * @author Yuval Anteby
 */
public class ScoreIndicator implements Sprite {
    private Counter scoreCounter;

    /**
     * Constructor for the class.
     * @param scoreCounter - instance of the score counter.
     */
    public ScoreIndicator(Counter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    /**
     * Getter for the score counter.
     * @return counter instance of the score.
     */
    public Counter getScoreCounter() {
        return scoreCounter;
    }

    /**
     * Add the score indicator to the game as a sprite.
     * @param g - instance of a game.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Constants.SCORE_BACKGROUND_COLOR);
        d.fillRectangle(Constants.TOP_LEFT_X, Constants.TOP_LEFT_Y, Constants.SCORE_WIDTH, Constants.SCORE_FONT_SIZE);
        d.setColor(Constants.TEXT_COLOR);
        String scoreText = "Score: " + this.scoreCounter.toString();
        d.drawText(Constants.TEXT_LOCATION_X, Constants.TEXT_LOCATION_Y, scoreText, Constants.SCORE_FONT_SIZE);
    }

    @Override
    public void timePassed() {
    }
}
