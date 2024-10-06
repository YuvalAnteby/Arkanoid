
import animation.AnimationRunner;
import animation.highscore.HighScore;
import animation.highscore.HighScoreAnimation;
import animation.highscore.HighScoreManager;
import animation.menu.*;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import game.levels.DirectHit;
import game.levels.LevelInformation;
import game.levels.WideEasy;
import util.Constants;
import util.Counter;

import java.util.ArrayList;
import java.util.List;

//TODO add more levels

/**
 * Class containing the main function to initialize and start the animation.menu and game.
 *
 * @author Yuval Anteby
 */
public class Ass5Game {

    /**
     * Main function.
     *
     * @param args - not used.
     */
    public static void main(String[] args) {
        //Create the gui and sensors.
        GUI gui = new GUI(Constants.GUI_NAME, Constants.GUI_WIDTH, Constants.GUI_HEIGHT);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner animationRunner = new AnimationRunner(gui);
        while (true) {
            //Set up the animation.menu.
            Menu<Task<Void>> menu = new MenuAnimation<>(Constants.GUI_NAME, keyboard, gui);
            addMenuOptions(menu, animationRunner, gui, keyboard);
            //Run the animation.menu.
            animationRunner.run(menu);
            //Wait for user's choice and run it.
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }

    /**
     * Create a list organized by the order of game.levels for a new full game.
     *
     * @return - list of game.levels.
     */
    private static ArrayList<LevelInformation> getListOfLevels() {
        ArrayList<LevelInformation> listOfLevels = new ArrayList<>();
        listOfLevels.add(new DirectHit());
        listOfLevels.add(new WideEasy());
        return listOfLevels;
    }

    /**
     * Add all options needed for the main animation.menu.
     *
     * @param menu   - animation.menu variable.
     * @param runner - animation runner of the animation.menu.
     * @param gui    - GUI in use.
     * @param ks     - keyboard sensor.
     */
    private static void addMenuOptions(Menu<Task<Void>> menu, AnimationRunner runner, GUI gui, KeyboardSensor ks) {
        //New complete game using our list of game.levels.
        addNewGameOption(menu, runner, gui, ks);
        //View the high score table.
        addHighScoreOption(menu, runner, gui);
        //Quit the game.
        addQuitOption(menu);
    }

    /**
     * Add the option of playing a new game to the animation.menu.
     *
     * @param menu   - animation.menu variable.
     * @param runner - animation runner of the animation.menu.
     * @param gui    - GUI in use.
     * @param ks     - keyboard sensor.
     */
    private static void addNewGameOption(Menu<Task<Void>> menu, AnimationRunner runner, GUI gui, KeyboardSensor ks) {
        String keyPress = "n", text = "New Game";
        Task newGame = () -> {
            Counter scoreCounter = new Counter();
            Counter livesCounter = new Counter();
            GameFlow gameFlow = new GameFlow(gui, runner, ks, scoreCounter, livesCounter);
            gameFlow.runLevels(getListOfLevels());
            return null;
        };
        menu.addMenuSelection(keyPress, text, newGame);
    }

    /**
     * Add the option of viewing the high score table to the animation.menu.
     *
     * @param menu   - animation.menu variable.
     * @param runner - animation runner of the animation.menu.
     * @param gui    - GUI in use.
     */
    private static void addHighScoreOption(Menu<Task<Void>> menu, AnimationRunner runner, GUI gui) {
        String keyPress = "h", text = "High Scores Table";
        Task highScore = () -> {
            HighScoreAnimation highScoreAnimation = new HighScoreAnimation(gui);
            highScoreAnimation.run();
            return null;
        };
        menu.addMenuSelection(keyPress, text, highScore);
    }

    /**
     * Add the option to quit the game to the animation.menu.
     *
     * @param menu - animation.menu variable.
     */
    private static void addQuitOption(Menu<Task<Void>> menu) {
        String keyPress = "q", text = "Quit game";
        Task quit = () -> {
            System.exit(0);
            return null;
        };
        menu.addMenuSelection(keyPress, text, quit);
    }

}
