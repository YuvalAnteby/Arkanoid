package game.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import static util.SpriteConstants.KEY_STOPPABLE_FONT_SIZE;
import static util.SpriteConstants.KEY_STOPPABLE_TXT_X;
import static util.SpriteConstants.KEY_STOPPABLE_TXT_Y;
import static util.TextValuesEng.KEY_STOPPABLE_CONTINUE;
import static util.TextValuesEng.KEY_STOPPABLE_PRESS;

/**
 * Class to represent animation of all stoppable screens. (Screens we can get to and return from using single button).
 * Meant to prevent multiple key presses of the user's keyboard.
 * @author Yuval Anteby
 */
public class KeyPressStoppableAnimation implements Animation {

    private final Animation animation;
    private final KeyboardSensor keyboardSensor;
    private final String key;
    private boolean isRunning = true;
    private boolean isAlreadyPressed = true;

    /**
     * Constructor.
     *
     * @param keyboardSensor keyboard sensor.
     * @param key            key for return from this screen.
     * @param animation      animation to run.
     */
    public KeyPressStoppableAnimation(KeyboardSensor keyboardSensor, String key, Animation animation) {
        this.keyboardSensor = keyboardSensor;
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
        d.drawText(KEY_STOPPABLE_TXT_X, KEY_STOPPABLE_TXT_Y, KEY_STOPPABLE_PRESS + key + KEY_STOPPABLE_CONTINUE,
                KEY_STOPPABLE_FONT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return !this.isRunning;
    }
}
