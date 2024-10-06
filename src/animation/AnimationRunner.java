package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import util.Constants;

/**
 * Class to run all animations.
 * Responsible for the running of general type.
 *
 * @author Yuval Anteby
 */
public class AnimationRunner {
    private final GUI gui;
    private final Sleeper sleeper;

    /**
     * Constructor for the class.
     *
     * @param gui GUI we will use to show the user.
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        sleeper = new Sleeper();
    }

    /**
     * Run a given animation.
     *
     * @param animation animation to run.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / Constants.FRAMES_PER_SECOND;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();
            //one frame of the animation
            animation.doOneFrame(d);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
