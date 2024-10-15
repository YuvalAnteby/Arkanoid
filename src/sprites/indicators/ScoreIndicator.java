package sprites.indicators;

import biuoop.DrawSurface;
import game.GameLevel;
import sprites.Sprite;
import util.Counter;

import static util.SpriteConstants.INDICATORS_BLOCK_X;
import static util.SpriteConstants.INDICATORS_BLOCK_Y;
import static util.SpriteConstants.INDICATOR_BACKGROUND_COLOR;
import static util.SpriteConstants.INDICATOR_TXT_SIZE;
import static util.SpriteConstants.INDICATOR_WIDTH;
import static util.SpriteConstants.SCORE_X;
import static util.SpriteConstants.SCORE_Y;
import static util.SpriteConstants.DARK_TEXT_COLOR;
import static util.TextValuesEng.SCORE_TEXT;

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
        d.setColor(INDICATOR_BACKGROUND_COLOR);
        d.fillRectangle(INDICATORS_BLOCK_X, INDICATORS_BLOCK_Y, INDICATOR_WIDTH, INDICATOR_TXT_SIZE);
        d.setColor(DARK_TEXT_COLOR);
        String scoreText = SCORE_TEXT + this.scoreCounter.toString();
        d.drawText(SCORE_X, SCORE_Y, scoreText, INDICATOR_TXT_SIZE);
    }

    @Override
    public void timePassed() {
    }
}
