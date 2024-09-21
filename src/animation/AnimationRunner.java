package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond = 60;
    private Sleeper sleeper = new Sleeper();

    public AnimationRunner(GUI gui) {
        this.gui = gui;
    }

    // ...
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        double dt;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();
            //one frame of the animation

            long currentTime = System.currentTimeMillis();
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