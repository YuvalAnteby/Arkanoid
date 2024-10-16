package util;

/**
 * Class for default keyboard keys to use across the project by the player.
 * Following immutable and singleton design patterns.
 *
 * @author Yuval Anteby
 */
public final class KeymapConstants {
    /**
     * Private constructor for the class.
     * Prevents creation of more than one instance.
     */
    private KeymapConstants() {
    }

    /**
     * Key for disabling functionality for one use.
     * For example not needing a second action in KeyPressStoppableAnimation.
     */
    public static final String EMPTY_KEY = "";
    /**
     * Key to start a new game from the menu.
     */
    public static final String NEW_GAME_KEY = "N";
    /**
     * Key to access the high score table from the menu.
     */
    public static final String HIGH_SCORE_KEY = "H";
    /**
     * Keys to quit the game in the main menu and pause screen respectively.
     */
    public static final String QUIT_GAME_MENU_KEY = "Q", QUIT_GAME_PAUSE_KEY = "Q";
    /**
     * Key to pause the game.
     */
    public static final String PAUSE_GAME_KEY = "P";
    /**
     * Key to continue from paused game.
     */
    public static final String RETURN_PAUSE_KEY = "SPACE";
    /**
     * Key for enabling and disabling the sounds of the game.
     */
    public static final String MUTE_KEY = "M";
    /**
     * Key for returning to main menu from various screens.
     */
    public static final String RETURN_TO_MENU = "SPACE";
    /**
     * Key for moving the paddle to the right.
     */
    public static final String[] MOVE_RIGHT = {"D", "RIGHT"};
    /**
     * Key for moving the paddle to the left.
     */
    public static final String[] MOVE_LEFT = {"A", "LEFT"};
}
