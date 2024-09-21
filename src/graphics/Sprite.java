package graphics;
import biuoop.DrawSurface;

/**
 * Interface to be used for objects that take part in animations and drawing (E.G paddle and balls).
 * @author Yuval Anteby
 */
public interface Sprite {
    /**
     * Notify the object it needs to be drawn on the gui.
     * @param d - surface to be used for drawing on.
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the object that time has passed and updating info is needed.
     */
    void timePassed();
}
