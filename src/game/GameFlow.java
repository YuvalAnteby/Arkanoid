package game;

import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import util.Counter;

import java.util.List;

/**
 * Class to manage to flow between levels in the game.
 * @author Yuval Anteby
 */
public class GameFlow {
    private final GUI gui;
    private final AnimationRunner animationRunner;
    private final KeyboardSensor keyboardSensor;
    private final Counter scoreCounter;
    //TODO add high score table
    //TODO add lives indicator

    /**
     * Constructor to take basic parameters in order for the game to run.
     * @param gui               - GUI in use.
     * @param animationRunner   - animation runner for the screens.
     * @param keyboard          - keyboard sensor from the GUI.
     * @param scoreCounter      - counter for the score of the user.
     */
    public GameFlow(GUI gui, AnimationRunner animationRunner, KeyboardSensor keyboard, Counter scoreCounter) {
        this.gui = gui;
        this.animationRunner = animationRunner;
        this.keyboardSensor = keyboard;
        this.scoreCounter = scoreCounter;
    }

    /**
     * Run all levels in the order we received.
     * @param levels - list of levels the user will play.
     */
    public void runLevels(List<LevelInformation> levels) {
        // ...
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(gui, keyboardSensor, levelInfo, scoreCounter);
            level.initialize();
            level.run();


            if (level.shouldStop() && level.getLevelStatus().equals("lose")) {
                //TODO show you lost screen
            } else if (level.shouldStop() && level.getLevelStatus().equals("win")) {
                //TODO show you won screen
            }

        }
        //gui.close();
    }
}
