
import animation.AnimationRunner;
import animation.MenuAnimation;
import animation.Task;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import levels.DirectHit;
import levels.LevelInformation;
import levels.WideEasy;
import screens.Menu;
import util.Constants;
import util.Counter;

import java.util.ArrayList;
//TODO add high score table
//TODO add more levels

/**
 * Class containing the main function to initialize and start the game.
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
        ArrayList<LevelInformation> listOfLevels = getListOfLevels();
        //Create the gui and sensors
        GUI gui = new GUI(Constants.GUI_NAME, Constants.GUI_WIDTH, Constants.GUI_HEIGHT);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner animationRunner = new AnimationRunner(gui);

        while (true) {
            Menu<Task<Void>> menu = new MenuAnimation<>(Constants.GUI_NAME, keyboard, gui);

            addMenuOptions(menu, animationRunner, gui, keyboard);

            animationRunner.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();

            //
            // gameFlow.runLevels(listOfLevels);
        }
    }

    /**
     * Create a list organized by the order of levels.
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
        addLevelOption(menu, runner, gui, ks);
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
        Task newGame = new Task() {
            @Override
            public Object run() {
                Counter scoreCounter = new Counter();
                Counter livesCounter = new Counter();
                GameFlow gameFlow = new GameFlow(gui, runner, ks, scoreCounter, livesCounter);
                gameFlow.runLevels(getListOfLevels());
                return null;
            }
        };
        menu.addMenuSelection(keyPress, text, newGame);
    }

    /**
     * Add the option of playing a specific level to the menu.
     *
     * @param menu   - menu variable.
     * @param runner - animation runner of the menu.
     * @param gui    - GUI in use.
     * @param ks     - keyboard sensor.
     */
    private static void addLevelOption(Menu<Task<Void>> menu, AnimationRunner runner, GUI gui, KeyboardSensor ks) {
        Menu<Task<Void>> subMenu = new MenuAnimation<>("Select Level", ks, gui);
        menu.addSubMenu("l", "Play level", subMenu);
        Task levels = new Task() {
            @Override
            public Object run() {
                ((MenuAnimation<Task<Void>>) menu).setFalse();
                runner.run(subMenu);
                return null;
            }
        };

    }

    /**
     * Add the option of viewing the high score table to the menu.
     *
     * @param menu   - menu variable.
     * @param runner - animation runner of the menu.
     * @param gui    - GUI in use.
     */
    private static void addHighScoreOption(Menu<Task<Void>> menu, AnimationRunner runner, GUI gui) {
        String keyPress = "h", text = "High-Scores Table";

        menu.addMenuSelection(keyPress, text, null);
    }

    /**
     * Add the option to quit the game to the menu.
     *
     * @param menu - menu variable.
     */
    private static void addQuitOption(Menu<Task<Void>> menu) {
        String keyPress = "q", text = "Quit game";
        Task quit = new Task() {
            @Override
            public Object run() {
                System.exit(0);
                return null;
            }
        };
        menu.addMenuSelection(keyPress, text, quit);
    }
}
