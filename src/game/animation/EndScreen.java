package game.animation;

import biuoop.DrawSurface;
import menu.highscore.HighScore;
import util.MuteManager;
import util.SoundPlayer;

import java.awt.Color;

import static util.GameConstants.GAME_IMAGE;
import static util.SoundConstants.DEFEAT_SOUND;
import static util.SoundConstants.WIN_SOUND;
import static util.SpriteConstants.KEY_STOPPABLE_FONT_SIZE;
import static util.SpriteConstants.MENU_VERTICAL_SPACING;
import static util.SpriteConstants.MESSAGE_X;
import static util.SpriteConstants.MESSAGE_Y;
import static util.TextValuesEng.DEFEAT_TEXT;
import static util.TextValuesEng.NEW_HIGHEST_SCORE;
import static util.TextValuesEng.VICTORY_TEXT;


/**
 * Class to represent the end game screen. Will show the player if they won or lost and relevant info.
 *
 * @author Yuval Anteby
 */
public class EndScreen implements Animation {

    private final HighScore currentHighScore;
    private final boolean isVictory;
    private final HighScore highestScore;

    /**
     * Constructor.
     *
     * @param currentHighScore  current high score of the user.
     * @param isVictory         true if the user won the game, otherwise false.
     * @param highestScore      the highest score that was saved.
     */
    public EndScreen(HighScore currentHighScore, boolean isVictory, HighScore highestScore) {
        this.currentHighScore = currentHighScore;
        this.isVictory = isVictory;
        this.highestScore = highestScore;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        drawBackground(d);
        d.setColor(Color.WHITE);
        //Show correct end screen.
        if (isVictory) {
            handleGameEnding(WIN_SOUND, VICTORY_TEXT + currentHighScore.getScore(), d);
        } else {
            handleGameEnding(DEFEAT_SOUND, DEFEAT_TEXT + currentHighScore.getScore(), d);
        }
        //Congratulate the player if they got a new high score in 1st place.
        if (currentHighScore.equals(highestScore)) {
            d.drawText(MESSAGE_X, MESSAGE_Y + MENU_VERTICAL_SPACING, NEW_HIGHEST_SCORE, KEY_STOPPABLE_FONT_SIZE);
        }
    }

    /**
     * Create the black background with the game's logo.
     *
     * @param d surface to draw on.
     */
    private void drawBackground(DrawSurface d) {
        //Create black background.
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        //Add Arkanoid logo.
        d.drawImage(0, 0, GAME_IMAGE);
    }

    /**
     * Play the correct sound and show the correct message according to if the player was defeat or victorious.
     *
     * @param sound sound of victory or defeat according to the game's outcome.
     * @param text  text of victory or defeat.
     * @param d     surface to draw on.
     */
    private void handleGameEnding(SoundPlayer sound, String text, DrawSurface d) {
        if (MuteManager.isSoundEnabled()) {
            sound.playOnce();
        }
        d.drawText(MESSAGE_X, MESSAGE_Y, text, KEY_STOPPABLE_FONT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
