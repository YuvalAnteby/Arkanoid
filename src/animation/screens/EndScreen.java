package animation.screens;

import animation.Animation;
import biuoop.DrawSurface;
import util.Constants;

/**
 * Class to represent the end game screen. Will show the player if they won or lost and relevant info.
 *
 * @author Yuval Anteby
 */
public class EndScreen implements Animation {

    /**
     * Position of the message on the GUI.
     */
    private static final int MESSAGE_X = Constants.BOUNDS_WIDTH, MESSAGE_Y = Constants.GUI_HEIGHT / 2;

    private final int score;
    private final boolean isVictory;

    /**
     * Constructor.
     *
     * @param score     final score of the user.
     * @param isVictory true if the user won the game, otherwise false.
     */
    public EndScreen(int score, boolean isVictory) {
        this.score = score;
        this.isVictory = isVictory;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        String text;
        if (isVictory) {
            text = "YOU WIN! your score: " + score;
        } else {
            text = "Game Over! your score: " + score;
        }
        d.drawText(MESSAGE_X, MESSAGE_Y, text, Constants.MESSAGE_FONT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
