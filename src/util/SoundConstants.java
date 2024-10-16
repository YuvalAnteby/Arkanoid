package util;

/**
 * Class for constants related to sound files to use across the project.
 * Following immutable and singleton design patterns.
 *
 * @author Yuval Anteby
 */
public final class SoundConstants {

    /**
     * Private constructor for the class.
     */
    private SoundConstants() {
    }

    /**
     * Default volume level of sounds. Default is 75%.
     */
    public static final float DEFAULT_VOLUME_LEVEL = 0.75f;
    /**
     * Main menu sound.
     */
    public static final SoundPlayer MENU_THEME = new SoundPlayer("./resources/sounds/menu_theme.wav");
    /**
     * Sound to play when players enter their name.
     */
    public static final SoundPlayer NAME_ENTRY = new SoundPlayer("./resources/sounds/name_entry.wav");
    /**
     * Sound to play when all balls removed and user still has extra lives (restarting level).
     */
    public static final SoundPlayer EXTRA_LIFE = new SoundPlayer("./resources/sounds/extra_life.wav");
    /**
     * Sound to play when a level starts.
     */
    public static final SoundPlayer LEVEL_START = new SoundPlayer("./resources/sounds/level_Start.wav");
    /**
     * Array of sounds to play randomly upon hits between balls and blocks/paddle.
     */
    public static final SoundPlayer[] HITS_SOUNDS = new SoundPlayer[]{
            new SoundPlayer("./resources/sounds/impact0.wav"),
            new SoundPlayer("./resources/sounds/impact1.wav"),
            new SoundPlayer("./resources/sounds/impact2.wav"),
            new SoundPlayer("./resources/sounds/impact3.wav"),
            new SoundPlayer("./resources/sounds/impact4.wav"),
            new SoundPlayer("./resources/sounds/impact5.wav"),
            new SoundPlayer("./resources/sounds/impact6.wav"),
            new SoundPlayer("./resources/sounds/impact7.wav"),
            new SoundPlayer("./resources/sounds/impact8.wav")
    };
    /**
     * Sound for the player's winning case.
     */
    public static final SoundPlayer WIN_SOUND = new SoundPlayer("./resources/sounds/victory.wav");
    /**
     * Sound for the player's losing case.
     */
    public static final SoundPlayer DEFEAT_SOUND = new SoundPlayer("./resources/sounds/defeat.wav");

}
