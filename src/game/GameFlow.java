package game;

import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import screens.EndScreen;
import screens.KeyPressStoppableAnimation;
import util.Constants;
import util.Counter;

import java.util.List;

/**
 * Class to manage to flow between levels in the game.
 *
 * @author Yuval Anteby
 */
public class GameFlow {
    private final GUI gui;
    private final AnimationRunner animationRunner;
    private final KeyboardSensor keyboardSensor;
    private final Counter scoreCounter;
    private final Counter livesCounter;
    //TODO add high score table

    /**
     * Constructor to take basic parameters in order for the game to run.
     *
     * @param gui             - GUI in use.
     * @param animationRunner - animation runner for the screens.
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
     * Run all levels in the order we received.
     *
     * @param levels - list of levels the user will play.
     */
    public void runLevels(List<LevelInformation> levels) {
        boolean didWin = true;
        livesCounter.increase(Constants.STARTING_LIVES);
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(gui, keyboardSensor, levelInfo, scoreCounter, livesCounter);
            level.initialize();
            level.run();
            if (level.shouldStop() && level.getLevelStatus().equals("lose")) {
                didWin = false;
                this.animationRunner.run(
                        new KeyPressStoppableAnimation(keyboardSensor, "space",
                                new EndScreen(scoreCounter.getValue(), false)));
                break;
            }
        }
        if (didWin) {
            this.animationRunner.run(
                    new KeyPressStoppableAnimation(keyboardSensor, "space",
                            new EndScreen(scoreCounter.getValue(), true)));
        }
        //gui.close();
    }
}
