package game;

import game.animation.AnimationRunner;
import menu.highscore.HighScore;
import menu.highscore.HighScoreManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.levels.LevelInformation;
import game.animation.EndScreen;
import game.animation.KeyPressStoppableAnimation;
import util.GameConstants;
import util.Counter;
import util.MuteManager;

import java.time.LocalDate;
import java.util.List;

import static util.SoundConstants.NAME_ENTRY;
import static util.TextValuesEng.ASK_NAME_TEXT;
import static util.TextValuesEng.ASK_NAME_TITLE;
import static util.TextValuesEng.DEFAULT_NAME;
import static util.TextValuesEng.INVALID_NAME_TEXT;

/**
 * Class to manage the flow between levels in the game.
 *
 * @author Yuval Anteby
 */
public class GameFlow {

    private final GUI gui;
    private final AnimationRunner animationRunner;
    private final KeyboardSensor keyboardSensor;
    private final Counter scoreCounter;
    private final Counter livesCounter;
    private HighScore highScore;
    private final HighScoreManager scoreManager;

    /**
     * Constructor to take basic parameters in order for the game to run.
     *
     * @param gui             GUI in use.
     * @param animationRunner animation runner for the animation.screens.
     * @param scoreCounter    counter for the score of the user.
     * @param livesCounter    counter for the lives of the user.
     */
    public GameFlow(GUI gui, AnimationRunner animationRunner, Counter scoreCounter, Counter livesCounter) {
        this.gui = gui;
        this.animationRunner = animationRunner;
        this.keyboardSensor = gui.getKeyboardSensor();
        this.scoreCounter = scoreCounter;
        this.livesCounter = livesCounter;
        this.scoreManager = new HighScoreManager();
    }

    /**
     * Get the user's name for saving the score and get current date.
     */
    private void initializeHighScore() {
        if (MuteManager.isSoundEnabled()) {
            NAME_ENTRY.playOnce();
        }
        String name = this.gui.getDialogManager().showQuestionDialog(ASK_NAME_TITLE, ASK_NAME_TEXT, DEFAULT_NAME);
        //A name must have a text different from numbers only and must not be empty.
        while (name == null || name.isBlank() || name.chars().allMatch(Character::isDigit)) {
            name = this.gui.getDialogManager().showQuestionDialog(ASK_NAME_TITLE, INVALID_NAME_TEXT, DEFAULT_NAME);
        }
        LocalDate currentDate = LocalDate.now();
        highScore = new HighScore(name, currentDate);
        if (MuteManager.isSoundEnabled()) {
            NAME_ENTRY.stop();
        }
    }

    /**
     * Run all levels in the order we received.
     *
     * @param levels list of levels the user will play.
     */
    public void runLevels(List<LevelInformation> levels) {
        //Initialize the high score variable.
        initializeHighScore();
        boolean didWin = true;
        //Set the starting amount of lives for the game.
        livesCounter.increase(GameConstants.STARTING_LIVES);
        //Run the levels.
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(gui, levelInfo, scoreCounter, livesCounter);
            level.initialize();
            level.run();
            //If the user lost (no lives remain and no balls on screen) then show the "lost" end screen.
            if (level.shouldStop() && level.getLevelStatus().equals("lose")) {
                didWin = false;
                handleDefeat();
                break;
            }
        }
        if (didWin) {
            handleVictory();
        }
    }

    /**
     * Show the end screen of player's defeat.
     * Will be shown if no balls remain and no additional lives.
     */
    private void handleDefeat() {
        saveScore();
        this.animationRunner.run(
                new KeyPressStoppableAnimation(keyboardSensor, "space", "",
                        new EndScreen(highScore, false, scoreManager.getHighestScore())));
    }

    /**
     * Show the end screen of player's victory.
     * Will be shown if player finished all levels.
     */
    private void handleVictory() {
        saveScore();
        this.animationRunner.run(
                new KeyPressStoppableAnimation(keyboardSensor, "space", "",
                        new EndScreen(highScore, true, scoreManager.getHighestScore())));
    }

    /**
     * Save the user's score to the file.
     */
    private void saveScore() {
        //Update the score variable in the high score.
        this.highScore.setScore(this.scoreCounter.getValue());
        //Update the scores file if needed.
        scoreManager.addNewScore(highScore);
    }
}
