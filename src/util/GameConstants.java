package util;

import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * Immutable and singleton class for default constant values related to game management to use across the project.
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
    public static final int STARTING_LIVES = 1;
    //TODO change to 4
}