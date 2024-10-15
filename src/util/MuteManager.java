package util;

import biuoop.KeyboardSensor;

/**
 * Class to manage the mute/ unmute state of the game. By, default enabling/disabling the sound is by 'M' key.
 * @author Yuval Anteby
 */
public class MuteManager {
    // Centralized mute/unmute state
    private static boolean soundEnabled = true;
    // To prevent multiple toggles on long press
    private static boolean isAlreadyPressed = false;

    /**
     * Checks if sound is enabled.
     *
     * @return true if sound is enabled, false if muted.
     */
    public static boolean isSoundEnabled() {
        return soundEnabled;
    }

    /**
     * Handles the mute/unmute functionality based on the "M" key press.
     *
     * @param keyboard the KeyboardSensor object to detect key presses.
     */
    public static void toggleMutePress(KeyboardSensor keyboard) {
        if (keyboard.isPressed("m") || keyboard.isPressed("M")) {
            if (!isAlreadyPressed) {
                soundEnabled = !soundEnabled;  // Toggle sound state
                isAlreadyPressed = true;  // Prevent multiple toggles on long press
            }
        } else {
            isAlreadyPressed = false;  // Reset the flag once the key is released
        }
    }
}
