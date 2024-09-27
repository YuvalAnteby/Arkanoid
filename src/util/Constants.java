package util;

import java.awt.Color;

/**
 * Immutable class for default constant values to use across the project.
 * @author Yuval Anteby
 */
public final class Constants {
    /**
        Paddle Constants.
     */
    //Paddle color constant.
    public static final Color PADDLE_COLOR = Color.ORANGE;
    //Paddle size constant variables
    public static final int PADDLE_WIDTH = 80, PADDLE_HEIGHT = 7;
    //Sensitivity of the paddle's movement. The bigger the number the faster it'll move.
    public static final int MOVEMENT_SENSITIVITY = 8;

    /**
        GUI constants.
     **/
    //Screen size constant variables.
    public static final int GUI_WIDTH = 800, GUI_HEIGHT = 600;
    //GUI name.
    public static final String GUI_NAME = "Arkanoid";
    //FPS of the game.
    public static final int FRAMES_PER_SECOND = 60;

    /**
        Boundaries constants.
     */
    //Boundaries color constant.
    public static final Color BOUNDS_COLOR = Color.GRAY;
    //Boundaries size constant variables.
    public static final int BOUNDS_WIDTH = 10, BOUNDS_HEIGHT = 10;

    /**
        Balls constants.
     */
    //Default radius size.
    public static final int DEFAULT_RADIUS = 8;
    //Default color.
    public static final Color DEFAULT_COLOR = Color.WHITE;

    /**
     * Indicator constants.
     */
    //Sizes
    public static final int INDICATOR_WIDTH = GUI_WIDTH, TEXT_SIZE = 20;
    //Colors
    public static final Color TEXT_COLOR = Color.BLACK, INDICATOR_BACKGROUND_COLOR = Color.LIGHT_GRAY;
    //Location
    public static final int TOP_LEFT_X = 0, TOP_LEFT_Y = 0;
    public static final int LEVEL_NAME_X = 70, LEVEL_NAME_Y = TEXT_SIZE - 2;
    public static final int SCORE_X = 300, SCORE_Y = TEXT_SIZE - 2;
    //TODO add lives


    /**
     * Pause screen constants.
     */
    public static final int PAUSE_FONT_SIZE = 32;
    public static final String PAUSE_MESSAGE = "paused -- press space to continue";


    /**
     * Private constructor for the class.
     */
    private Constants() {
    }
}
