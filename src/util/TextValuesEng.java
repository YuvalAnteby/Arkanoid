package util;

import java.util.List;

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
     * Text for the menu option of starting a new game.
     */
    public static final String NEW_GAME_TEXT = "New Game";
    /**
     * Text for the menu option of viewing the scoreboard table.
     */
    public static final String SCOREBOARD_TABLE_TEXT = "High-Score table";
    /**
     * Text for the menu option of quitting the game.
     */
    public static final String QUIT_GAME_TEXT = "Quit game";
    /**
     *
     */
    public static final List<String> SCOREBOARD_HEADER = List.of("NAME", "SCORE", "DATE");
    /**
     * Default player's name.
     */
    public static final String DEFAULT_NAME = "Player";
    /**
     * Text when countdown ends.
     */
    public static final String COUNTDOWN_END = "Go!";
    /**
     * Key stoppable press button text.
     */
    public static final String KEY_STOPPABLE_PRESS = "Press ";
    /**
     * Key stoppable actions text.
     */
    public static final String KEY_STOPPABLE_CONTINUE = " to continue", KEY_STOPPABLE_QUIT = " to quit";
    /**
     * High score screen action text.
     */
    public static final String HIGH_SCORE_RETURN = " to return";
    /**
     * Score indicator english text.
     */
    public static final String SCORE_TEXT = "Score: ";
    /**
     * Lives indicator english text.
     */
    public static final String LIVES_TEXT = "Lives: ";
    /**
     * Text to appear in the end screen if the player won.
     */
    public static final String VICTORY_TEXT = "YOU WIN! your score: ";
    /**
     * Text to appear in the end screen if the player won.
     */
    public static final String DEFEAT_TEXT = "Game Over! your score: ";
    /**
     * Text to show when player got a new high score in 1st place.
     */
    public static final String NEW_HIGHEST_SCORE = "New High score! you're 1st place!";
    /**
     * Text to appear when the game is paused.
     */
    public static final String PAUSE_MESSAGE = "Game Paused";
}
