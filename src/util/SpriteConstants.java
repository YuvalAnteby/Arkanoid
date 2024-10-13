package util;

import java.awt.Color;

import static util.GameConstants.GUI_HEIGHT;
import static util.GameConstants.GUI_WIDTH;

/**
 * Immutable and singleton class for default constant values related to sprites to use across the project.
 *
 * @author Yuval Anteby
 */
public final class SpriteConstants {

    /**
     * Private constructor for the class.
     * Prevents creation of more than one instance.
     */
    private SpriteConstants() {
    }
    //Menu Constants
    /**
     * Font size of menu objects.
     */
    public static final int MENU_FONT_SIZE = 55;
    /**
     * Vertical spacing between menu objects.
     */
    public static final int MENU_VERTICAL_SPACING = 85;
    //Boundaries constants
    /**
     * Boundaries default size.
     */
    public static final int BOUND_WIDTH = 10, BOUND_HEIGHT = 10;
    /**
     * Boundaries default color.
     */
    public static final Color BOUND_COLOR = Color.GRAY;
    //Paddle constants
    /**
     * Default color of the paddle.
     */
    public static final Color PADDLE_COLOR = Color.ORANGE;
    /**
     * Default height of the paddle.
     */
    public static final int PADDLE_HEIGHT = 8;
    //Balls constants
    /**
     * Default color of the ball.
     */
    public static final Color DEFAULT_COLOR = Color.WHITE;
    /**
     * Spacing between the ball spawn point and the paddle.
     */
    public static final int BALL_OFFSET = 50;
    /**
     * Default values of the ball.
     */
    public static final int X_BALL = GUI_WIDTH / 2, Y_BALL = GUI_HEIGHT - PADDLE_HEIGHT - BALL_OFFSET, BALL_RADIUS = 8;
    //Indicators constants
    /**
     * Indicators sizes.
     */
    public static final int INDICATOR_WIDTH = GUI_WIDTH, INDICATOR_TXT_SIZE = 20;
    /**
     * Indicators location.
     */
    public static final int INDICATORS_BLOCK_X = 0, INDICATORS_BLOCK_Y = 0;
    /**
     * Indicators color.
     */
    public static final Color TEXT_COLOR = Color.BLACK, INDICATOR_BACKGROUND_COLOR = Color.LIGHT_GRAY;
    /**
     * Level name indicator location.
     */
    public static final int LEVEL_NAME_X = 70, LEVEL_NAME_Y = INDICATOR_TXT_SIZE - 2;
    /**
     * Score indicator location.
     */
    public static final int SCORE_X = 350, SCORE_Y = INDICATOR_TXT_SIZE - 2;
    /**
     * Lives indicator location.
     */
    public static final int LIVES_X = 600, LIVES_Y = INDICATOR_TXT_SIZE - 2;
    //Key stoppable screen constants
    /**
     * Key stoppable screen font size.
     */
    public static final int KEY_STOPPABLE_FONT_SIZE = 32;
    /**
     * Main text of key stoppable screen x value.
     */
    public static final int KEY_STOPPABLE_TXT_X = BOUND_WIDTH;
    /**
     * Main text of key stoppable screen y value.
     */
    public static final int KEY_STOPPABLE_TXT_Y = GUI_HEIGHT - KEY_STOPPABLE_FONT_SIZE;
    //Countdown constants
    /**
     * Location of the countdown text.
     */
    public static final int COUNTDOWN_X = GUI_WIDTH / 2 - BOUND_WIDTH, COUNTDOWN_Y = (GUI_HEIGHT / 2) + 100;
    //End screen constants
    /**
     * Position of the end game message on the GUI.
     */
    public static final int MESSAGE_X = BOUND_WIDTH, MESSAGE_Y = GUI_HEIGHT / 2;
}
