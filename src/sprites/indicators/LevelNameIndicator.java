package sprites.indicators;

import biuoop.DrawSurface;
import game.GameLevel;
import sprites.Sprite;

import static util.SpriteConstants.INDICATOR_TXT_SIZE;
import static util.SpriteConstants.LEVEL_NAME_X;
import static util.SpriteConstants.LEVEL_NAME_Y;
import static util.SpriteConstants.DARK_TEXT_COLOR;

/**
 * Class to represent the level's name currently played.
 *
 * @author Yuval Anteby
 */
public class LevelNameIndicator implements Sprite {

    private final String levelName;

    /**
     * Constructor.
     *
     * @param levelName name of the level currently played.
     */
    public LevelNameIndicator(String levelName) {
        this.levelName = levelName;
    }

    /**
     * Add the name indicator to the game as a sprite.
     *
     * @param g instance of a game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(DARK_TEXT_COLOR);
        d.drawText(LEVEL_NAME_X, LEVEL_NAME_Y, levelName, INDICATOR_TXT_SIZE);
    }

    @Override
    public void timePassed() {

    }
}
