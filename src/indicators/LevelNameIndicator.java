package indicators;

import biuoop.DrawSurface;
import game.GameLevel;
import graphics.Sprite;
import util.Constants;

import java.awt.*;

/**
 * Class to represent the level's name currently played.
 * @author Yuval Anteby
 */
public class LevelNameIndicator implements Sprite {

    private final String levelName;

    /**
     * Constructor.
     * @param levelName - name of the level currently played.
     */
    public LevelNameIndicator(String levelName) {
        this.levelName = levelName;
    }

    /**
     * Add the name indicator to the game as a sprite.
     * @param g - instance of a game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Constants.TEXT_COLOR);
        d.drawText(Constants.LEVEL_NAME_X, Constants.LEVEL_NAME_Y, levelName, Constants.TEXT_SIZE);
    }

    @Override
    public void timePassed() {

    }
}
