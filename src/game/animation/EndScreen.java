package game.animation;

import biuoop.DrawSurface;

import static util.SoundConstants.DEFEAT_SOUND;
import static util.SoundConstants.WIN_SOUND;
import static util.SpriteConstants.KEY_STOPPABLE_FONT_SIZE;
import static util.SpriteConstants.MESSAGE_X;
import static util.SpriteConstants.MESSAGE_Y;


/**
 * Class to represent the end game screen. Will show the player if they won or lost and relevant info.
 *
 * @author Yuval Anteby
 */
public class EndScreen implements Animation {

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
            WIN_SOUND.playOnce();
            text = "YOU WIN! your score: " + score;
        } else {
            DEFEAT_SOUND.playOnce();
            text = "Game Over! your score: " + score;
        }
        d.drawText(MESSAGE_X, MESSAGE_Y, text, KEY_STOPPABLE_FONT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
