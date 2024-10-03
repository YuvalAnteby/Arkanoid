
import animation.AnimationRunner;
import game.GameLevel;
import menu.MenuAnimation;
import menu.Task;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import levels.DirectHit;
import levels.LevelInformation;
import levels.WideEasy;
import menu.Menu;
import util.Constants;
import util.Counter;

import java.util.ArrayList;
import java.util.List;

//TODO add more levels

/**
 * Class containing the main function to initialize and start the menu and game.
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
            //Set up the menu.
            Menu<Task<Void>> menu = new MenuAnimation<>(Constants.GUI_NAME, keyboard, gui);
            addMenuOptions(menu, animationRunner, gui, keyboard);
            //Run the menu.
            animationRunner.run(menu);
            //Wait for user's choice and run it.
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }

    /**
     * Create a list organized by the order of levels for a new full game.
     *
     * @return - list of levels.
     */
    private static ArrayList<LevelInformation> getListOfLevels() {
        ArrayList<LevelInformation> listOfLevels = new ArrayList<>();
        listOfLevels.add(new DirectHit());
        listOfLevels.add(new WideEasy());
        return listOfLevels;
    }

    /**
     * Add all options needed for the main menu.
     *
     * @param menu   - menu variable.
     * @param runner - animation runner of the menu.
     * @param gui    - GUI in use.
     * @param ks     - keyboard sensor.
     */
    private static void addMenuOptions(Menu<Task<Void>> menu, AnimationRunner runner, GUI gui, KeyboardSensor ks) {
        addNewGameOption(menu, runner, gui, ks);
        addHighScoreOption(menu, runner, gui);
        addQuitOption(menu);
    }

    /**
     * Add the option of playing a new game to the menu.
     *
     * @param menu   - menu variable.
     * @param runner - animation runner of the menu.
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
     * Add the option of viewing the high score table to the menu.
     *
     * @param menu   - menu variable.
     * @param runner - animation runner of the menu.
     * @param gui    - GUI in use.
     */
    private static void addHighScoreOption(Menu<Task<Void>> menu, AnimationRunner runner, GUI gui) {
        String keyPress = "h", text = "High Scores Table";
        //TODO add high score table
        menu.addMenuSelection(keyPress, text, null);
    }

    /**
     * Add the option to quit the game to the menu.
     *
     * @param menu - menu variable.
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
