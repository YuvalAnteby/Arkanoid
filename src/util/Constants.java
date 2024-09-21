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
    //Background color constant.
    public static final Color BACKGROUND_COLOR = new Color(0, 128, 128);
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
        Blocks constants.
     */
    //Blocks size constant variables.
    public static final int BLOCK_WIDTH = 50, BLOCK_HEIGHT = 20;
    //Amount of rows.
    public static final int NUM_OF_ROWS = 6;

    /**
        Balls constants.
     */
    //Starting amount of balls.
    public static final int BALLS_AMOUNT = 3;
    //Default radius size.
    public static final int DEFAULT_RADIUS = 8;
    //Starting Coordinates.
    public static final int MIN_X = BOUNDS_WIDTH + DEFAULT_RADIUS;
    public static final int MAX_X = GUI_WIDTH - BOUNDS_WIDTH - DEFAULT_RADIUS;
    public static final int MIN_Y = BOUNDS_HEIGHT + DEFAULT_RADIUS + (NUM_OF_ROWS + 3) * BLOCK_HEIGHT;
    public static final int MAX_Y = GUI_HEIGHT - BOUNDS_HEIGHT - DEFAULT_RADIUS - PADDLE_HEIGHT;

    /**
     * Score indicator constants.
     */
    //Sizes
    public static final int SCORE_WIDTH = GUI_WIDTH, SCORE_FONT_SIZE = 20;
    //Colors
    public static final Color TEXT_COLOR = Color.BLACK, SCORE_BACKGROUND_COLOR = Color.LIGHT_GRAY;
    //Location
    public static final int TOP_LEFT_X = 0, TOP_LEFT_Y = 0, TEXT_LOCATION_X = 350, TEXT_LOCATION_Y = 18;

    /**
     * Private constructor for the class.
     */
    private Constants() {
    }
}
