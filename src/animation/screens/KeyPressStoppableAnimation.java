package animation.screens;

import animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import util.Constants;

public class KeyPressStoppableAnimation implements Animation {

    private static final int PRESS_X = Constants.BOUNDS_WIDTH;
    private static final int PRESS_Y = Constants.GUI_HEIGHT - Constants.MESSAGE_FONT_SIZE;

    private final Animation animation;
    private final KeyboardSensor keyboardSensor;
    private final String key;
    private boolean isRunning = true;
    private boolean isAlreadyPressed = true;

    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboardSensor = sensor;
        this.key = key;
        this.animation = animation;
    }


    @Override
    public void doOneFrame(DrawSurface d) {
        animation.doOneFrame(d);
        if (keyboardSensor.isPressed(key)) {
            if (!isAlreadyPressed) {
                this.isRunning = false;
            } else {
                this.isAlreadyPressed = false;
            }
        }
        d.drawText(PRESS_X, PRESS_Y, "press " + key + " to continue", Constants.MESSAGE_FONT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return !this.isRunning;
    }
}
