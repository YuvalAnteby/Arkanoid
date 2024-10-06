package game;

import animation.AnimationRunner;
import animation.highscore.HighScore;
import animation.highscore.HighScoreManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.levels.LevelInformation;
import animation.screens.EndScreen;
import animation.screens.KeyPressStoppableAnimation;
import util.Constants;
import util.Counter;

import java.time.LocalDate;
import java.util.List;

/**
 * Class to manage to flow between levels in the game.
 *
 * @author Yuval Anteby
 */
public class GameFlow {

    private final static String ASK_NAME_TITLE = "New Game";
    private final static String ASK_NAME_TEXT = "Enter your name";
    private final static String INVALID_NAME_TEXT = "Invalid name. Enter your name";
    private final static String DEFAULT_NAME = "Player";

    private final GUI gui;
    private final AnimationRunner animationRunner;
    private final KeyboardSensor keyboardSensor;
    private final Counter scoreCounter;
    private final Counter livesCounter;
    private HighScore highScore;

    /**
     * Constructor to take basic parameters in order for the game to run.
     *
     * @param gui             - GUI in use.
     * @param animationRunner - animation runner for the animation.screens.
     * @param keyboard        - keyboard sensor from the GUI.
     * @param scoreCounter    - counter for the score of the user.
     * @param livesCounter    - counter for the lives of the user.
     */
    public GameFlow(GUI gui, AnimationRunner animationRunner, KeyboardSensor keyboard, Counter scoreCounter,
                    Counter livesCounter) {
        this.gui = gui;
        this.animationRunner = animationRunner;
        this.keyboardSensor = keyboard;
        this.scoreCounter = scoreCounter;
        this.livesCounter = livesCounter;
    }

    /**
     * Get the user's name for saving the score and get current date.
     */
    private void initializeHighScore() {
        String name = this.gui.getDialogManager().showQuestionDialog(ASK_NAME_TITLE, ASK_NAME_TEXT, DEFAULT_NAME);
        //A name must have a text different from numbers only and must not be empty.
        while (name == null || name.isBlank() || name.chars().allMatch(Character::isDigit)) {
            name = this.gui.getDialogManager().showQuestionDialog(ASK_NAME_TITLE, INVALID_NAME_TEXT, DEFAULT_NAME);
        }
        LocalDate currentDate = LocalDate.now();
        highScore = new HighScore(name, currentDate);
    }

    /**
     * Run all levels in the order we received.
     *
     * @param levels - list of levels the user will play.
     */
    public void runLevels(List<LevelInformation> levels) {
        //Initialize the high score variable.
        initializeHighScore();
        boolean didWin = true;
        //Set the starting amount of lives for the game.
        livesCounter.increase(Constants.STARTING_LIVES);
        //Run the levels.
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(gui, keyboardSensor, levelInfo, scoreCounter, livesCounter);
            level.initialize();
            level.run();
            //If the user lost (no lives remain and no balls on screen) then show the "lost" end screen.
            if (level.shouldStop() && level.getLevelStatus().equals("lose")) {
                didWin = false;
                this.animationRunner.run(
                        new KeyPressStoppableAnimation(keyboardSensor, "space",
                                new EndScreen(scoreCounter.getValue(), false)));
                saveScore();
                break;
            }
        }
        if (didWin) {
            saveScore();
            this.animationRunner.run(
                    new KeyPressStoppableAnimation(keyboardSensor, "space",
                            new EndScreen(scoreCounter.getValue(), true)));
        }
        //gui.close();
    }

    /**
     * Save the user's score to the file.
     */
    private void saveScore() {
        //Update the score variable in the high score.
        this.highScore.setScore(this.scoreCounter.getValue());
        //Update the scores file if needed.
        HighScoreManager scoreManager = new HighScoreManager();
        scoreManager.addNewScore(highScore);
    }
}
