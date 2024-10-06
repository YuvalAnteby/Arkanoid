package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to contain a list of objects which use sprite interface.
 *
 * @author Yuval Anteby
 */
public class SpriteCollection {
    private final List<Sprite> spriteList;

    /**
     * Default constructor for the class, will initialize a new array list.
     */
    public SpriteCollection() {
        this.spriteList = new ArrayList<>();
    }

    /**
     * Add a new sprite to the list of the sprites list.
     *
     * @param s a new sprite to be added.
     */
    public void addSprite(Sprite s) {
        if (s != null) {
            spriteList.add(s);
        }
    }

    /**
     * Remove a sprite object from the game environment.
     *
     * @param s sprite object to be removed.
     */
    public void removeSprite(Sprite s) {
        if (s != null) {
            spriteList.remove(s);
        }
    }

    /**
     * Notify every sprite that time passed.
     */
    public void notifyAllTimePassed() {
        //Changing to iterator for (enhanced for) requires further code changes for it.
        for (int i = 0; i < spriteList.size(); i++) {
            spriteList.get(i).timePassed();
        }
    }

    /**
     * Call the draw animation for each sprite.
     *
     * @param d the draw surface of the gui.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : spriteList) {
            sprite.drawOn(d);
        }
    }
}
