package util;

/**
 * Immutable and singleton class for default constant String (English) values to use across the project.
 *
 * @author Yuval Anteby
 */
public final class TextValuesEng {

    /**
     * Private constructor for the class.
     * Prevents creation of more than one instance.
     */
    private TextValuesEng() {
    }

    /**
     * Appears on the top of the GUI window.
     */
    public static final String GUI_NAME = "Arkanoid";
    /**
     * Text to appear in the top of dialog asking for player's name.
     */
    public static final String ASK_NAME_TITLE = "New Game";
    /**
     * Text asking for the player's name in the dialog asking for their name.
     */
    public static final String ASK_NAME_TEXT = "Enter your name";
    /**
     * Text to appear if the player entered an invalid name in the dialog asking for their name.
     */
    public static final String INVALID_NAME_TEXT = "Invalid name. Enter your name";
    /**
     * Text for the menu options (starting a game, viewing the scoreboard etc.).
     */
    public static final String NEW_GAME = "New Game", SCOREBOARD_TABLE = "High-Score table", QUIT_GAME = "Quit game";
    /**
     * Default player's name.
     */
    public static final String DEFAULT_NAME = "Player";
    /**
     * Text when countdown ends.
     */
    public static final String COUNTDOWN_END = "Go!";
    /**
     * Key stoppable pressing text.
     */
    public static final String KEY_STOPPABLE_PRESS = "Press ", KEY_STOPPABLE_CONTINUE = " to continue";
    /**
     * Score indicator english text.
     */
    public static final String SCORE_TEXT = "Score: ";
    /**
     * Text to appear when the game is paused.
     */
    public static final String PAUSE_MESSAGE = "Game Paused";
}
