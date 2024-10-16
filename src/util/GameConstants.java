package util;

import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * Class for default constant values related to game management to use across the project.
 * Following immutable and singleton design patterns.
 *
 * @author Yuval Anteby
 */
public final class GameConstants {

    /**
     * Private constructor for the class.
     * Prevents creation of more than one instance.
     */
    private GameConstants() {
    }

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
     * Amount of starting lives of the user.
     */
    public static final int STARTING_LIVES = 4;
    /**
     * Points to receive for each block removed.
     */
    public static final int BLOCK_REMOVAL_POINTS = 5;
    /**
     * Points to receive for removing all blocks in a level.
     */
    public static final int LEVEL_VICTORY_POINTS = 100;
}
