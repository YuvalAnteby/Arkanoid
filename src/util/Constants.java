package util;

import javax.swing.*;
import java.awt.*;

/**
 * Immutable and singleton class for default constant values to use across the project.
 *
 * @author Yuval Anteby
 */
public final class Constants {

    /**
     * GUI size constants.
     **/
    public static final int GUI_WIDTH = 800, GUI_HEIGHT = 600;

    /**
     * Game logo as an image.
     */
    public static final Image GAME_IMAGE = new ImageIcon("./resources/arkanoid-logo.jpg").getImage();

    /**
     * FPS of the game.
     */
    public static final int FRAMES_PER_SECOND = 60;

    /**
     * Boundaries color constant.
     */
    public static final Color BOUNDS_COLOR = Color.GRAY;
    /**
     * Boundaries size constants.
     */
    public static final int BOUNDS_WIDTH = 10, BOUNDS_HEIGHT = 10;

    /**
     * Indicators sizes.
     */
    public static final int INDICATOR_WIDTH = GUI_WIDTH, TEXT_SIZE = 20;
    /**
     * Indicators location.
     */
    public static final int INDICATORS_X = 0, INDICATORS_Y = 0;
    /**
     * Indicators color.
     */
    public static final Color TEXT_COLOR = Color.BLACK, INDICATOR_BACKGROUND_COLOR = Color.LIGHT_GRAY;
    /**
     * Indicators text location.
     */
    public static final int LEVEL_NAME_X = 70, LEVEL_NAME_Y = TEXT_SIZE - 2;
    public static final int SCORE_X = 350, SCORE_Y = TEXT_SIZE - 2;
    public static final int LIVES_X = 600, LIVES_Y = TEXT_SIZE - 2;
    public static final int STARTING_LIVES = 1;

    /**
     * Key stoppable screen font size.
     */
    public static final int MESSAGE_FONT_SIZE = 32;


    /**
     * Private constructor for the class. Prevent
     */
    private Constants() {
    }
}
