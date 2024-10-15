package game.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import util.MuteManager;

import static util.SpriteConstants.*;
import static util.TextValuesEng.*;

/**
 * Class to represent animation of all stoppable screens. (Screens we can get to and return from using single button).
 * Meant to prevent multiple key presses of the user's keyboard.
 *
 * @author Yuval Anteby
 */
public class KeyPressStoppableAnimation implements Animation {
    private final Animation animation;
    private final KeyboardSensor keyboard;
    private final String mainKey, secondKey;
    private boolean didPressFirstKey = false;
    private boolean didPressSecondKey = false;
    private boolean isAlreadyPressed = true;

    /**
     * Constructor.
     *
     * @param keyboard  keyboard sensor.
     * @param mainKey   key for returning from this screen.
     * @param secondKey key for quitting the game from this screen.
     * @param animation animation to run.
     */
    public KeyPressStoppableAnimation(KeyboardSensor keyboard, String mainKey, String secondKey, Animation animation) {
        this.keyboard = keyboard;
        this.mainKey = mainKey;
        this.secondKey = secondKey;
        this.animation = animation;
    }


    @Override
    public void doOneFrame(DrawSurface d) {
        animation.doOneFrame(d);
        String mainMsg = KEY_STOPPABLE_PRESS + mainKey + KEY_STOPPABLE_CONTINUE;
        //No second key is present, show only the main message.
        if (secondKey.isEmpty()) {
            d.drawText(KEY_STOPPABLE_TXT_X, KEY_STOPPABLE_MAIN_TXT_Y, mainMsg, KEY_STOPPABLE_FONT_SIZE);
        } else {
            //Draw main message.
            d.drawText(KEY_STOPPABLE_TXT_X, KEY_STOPPABLE_MAIN_TXT_Y, mainMsg, KEY_STOPPABLE_FONT_SIZE);
            //Draw second message.
            String secondMsg = KEY_STOPPABLE_PRESS + secondKey + KEY_STOPPABLE_QUIT;
            d.drawText(KEY_STOPPABLE_TXT_X, KEY_STOPPABLE_SECOND_TXT_Y, secondMsg, KEY_STOPPABLE_FONT_SIZE);
            //Wait for player's input and handle accordingly.
        }
        handleKeyboardInput();
    }

    /**
     * Handle player's input from the keyboard.
     * Including muting, main key and second key handling.
     */
    private void handleKeyboardInput() {
        //Mute or unmute the game on 'm' press.
        MuteManager.toggleMutePress(this.keyboard);
        //Main key handling.
        if (keyboard.isPressed(mainKey.toLowerCase()) || keyboard.isPressed(mainKey.toUpperCase())) {
            if (!isAlreadyPressed) {
                this.didPressFirstKey = true;
            } else {
                this.isAlreadyPressed = false;
            }
        }
        //Second key handling
        if (keyboard.isPressed(secondKey.toLowerCase()) || keyboard.isPressed(secondKey.toUpperCase())) {
            if (!isAlreadyPressed) {
                this.didPressFirstKey = true;
                this.didPressSecondKey = true;
            } else {
                this.isAlreadyPressed = false;
            }
        }
    }

    /**
     * Check if player's used the second key of the key stoppable screen.
     * Mainly used for checking if the player wants to quit the game from pause screen.
     *
     * @return true if user pressed the second key, otherwise false.
     */
    public boolean DidPressSecondKey() {
        return this.didPressSecondKey;
    }

    @Override
    public boolean shouldStop() {
        return this.didPressFirstKey;
    }
}
