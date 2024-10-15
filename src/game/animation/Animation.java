package game.animation;

import biuoop.DrawSurface;

/**
 * Interface for animation's methods.
 */
public interface Animation {

    /**
     * Method for each frame render.
     *
     * @param d draw surface
     */
    void doOneFrame(DrawSurface d);

    /**
     * Check if the animation should stop.
     *
     * @return true if it should stop, otherwise false.
     */
    boolean shouldStop();

}
