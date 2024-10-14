package menu.highscore;

import game.animation.Animation;
import game.animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import util.GameConstants;

import java.awt.Color;
import java.util.List;

import static util.SpriteConstants.HORIZONTAL_SPACING;
import static util.SpriteConstants.SCORE_RETURN_FONT_SIZE;
import static util.SpriteConstants.SCORE_TXT_SIZE;
import static util.SpriteConstants.VERTICAL_SPACING;
import static util.TextValuesEng.SCOREBOARD_HEADER;

/**
 * Class for the high score screen animation.
 *
 * @author Yuval Anteby
 */
public class HighScoreAnimation implements Animation {

    private final GUI gui;
    private final KeyboardSensor keyboardSensor;
    private final List<HighScore> highScores;
    private boolean shouldStop = false;
    private boolean isAlreadyPressed = false;

    /**
     * Constructor for the class.
     *
     * @param gui GUI in use.
     */
    public HighScoreAnimation(GUI gui) {
        HighScoreManager manager = new HighScoreManager();
        this.highScores = manager.getHighScores();
        this.keyboardSensor = gui.getKeyboardSensor();
        this.gui = gui;
    }

    /**
     * Run the animation to show the high score table.
     */
    public void run() {
        AnimationRunner runner = new AnimationRunner(this.gui);
        runner.run(this);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int xValue = 200, yValue = 240;
        drawBackground(d);
        //Set text color
        d.setColor(Color.WHITE);
        //Show the header of the high score table.
        drawScoreRow(d, SCOREBOARD_HEADER, xValue, yValue);
        yValue += VERTICAL_SPACING;
        //Show the scores.
        for (HighScore hs : this.highScores) {
            //Set each row to contain the attributes of a high score.
            List<String> row = List.of(hs.getName(), String.valueOf(hs.getScore()), String.valueOf(hs.getDate()));
            //Draw the row.
            drawScoreRow(d, row, xValue, yValue);
            yValue += VERTICAL_SPACING;
        }
        drawReturnOption(d, xValue, yValue);
        shouldStop = checkForReturnToMenu();
    }

    /**
     * Show return option. If user press on the specified key return to the main menu.
     *
     * @param d surface to draw on.
     * @param x text's x value.
     * @param y text's y value.
     */
    private void drawReturnOption(DrawSurface d, int x, int y) {
        y += VERTICAL_SPACING / 2;
        d.drawText(x, y, "Press space to return", SCORE_RETURN_FONT_SIZE);
    }

    /**
     * Check if the user presses the return key (space) to stop the animation and return to the main menu.
     *
     * @return true if the user pressed the return key, otherwise false.
     */
    private boolean checkForReturnToMenu() {
        if (keyboardSensor.isPressed(KeyboardSensor.SPACE_KEY) && !isAlreadyPressed) {
            this.isAlreadyPressed = true;
            return true;
        }
        // Reset when key is released
        if (!keyboardSensor.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.isAlreadyPressed = false;
        }
        return false;
    }

    /**
     * Draw the row of a score.
     * Shows the attributes of a high score.
     *
     * @param d       surface to draw on.
     * @param rowText list of string to show in a row.
     * @param x       starting x value.
     * @param y       y value.
     */
    private void drawScoreRow(DrawSurface d, List<String> rowText, int x, int y) {
        for (String s : rowText) {
            d.drawText(x, y, s, SCORE_TXT_SIZE);
            x += HORIZONTAL_SPACING;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.shouldStop;
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
        //Add the game logo image.
        d.drawImage(0, 0, GameConstants.GAME_IMAGE);
    }

}
