package animation.screens;

import animation.Animation;
import biuoop.DrawSurface;
import util.Constants;

public class EndScreen implements Animation {

    private static final int MESSAGE_X = Constants.BOUNDS_WIDTH;
    private static final int MESSAGE_Y = Constants.GUI_HEIGHT / 2;


    private final int score;
    private final boolean isVictory;

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
