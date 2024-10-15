import game.animation.AnimationRunner;
import game.levels.DirectHit;
import game.levels.FinalFour;
import game.levels.Green3;
import game.levels.LevelInformation;
import game.levels.WideEasy;
import menu.highscore.HighScoreAnimation;
import menu.Menu;
import menu.MenuAnimation;
import menu.Task;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import util.GameConstants;
import util.Counter;
import util.SoundConstants;

import java.util.ArrayList;

import static util.GameConstants.isSoundEnabled;
import static util.GameConstants.swapSoundEnabled;
import static util.KeymapConstants.HIGH_SCORE_KEY;
import static util.KeymapConstants.MUTE_KEY;
import static util.KeymapConstants.NEW_GAME_KEY;
import static util.KeymapConstants.QUIT_GAME_MENU_KEY;
import static util.TextValuesEng.GUI_NAME;
import static util.TextValuesEng.NEW_GAME;
import static util.TextValuesEng.QUIT_GAME;
import static util.TextValuesEng.SCOREBOARD_TABLE;

/**
 * Class containing the main function to initialize and start the menu and game.
 *
 * @author Yuval Anteby
 */
public class Ass5Game {
    private static boolean shouldRun = true;
    private static boolean isSoundPlayingNow = false;

    /**
     * Main function.
     *
     * @param args - not used.
     */
    public static void main(String[] args) {
        //Create the gui and sensors.
        GUI gui = new GUI(GUI_NAME, GameConstants.GUI_WIDTH, GameConstants.GUI_HEIGHT);
        SoundConstants.MENU_THEME.loop();
        isSoundPlayingNow = true;
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner animationRunner = new AnimationRunner(gui);
        while (shouldRun) {
            handleSoundManagement(keyboard);
            //Set up the menu.
            Menu<Task<Void>> menu = new MenuAnimation<>(keyboard, gui);
            addMenuOptions(menu, animationRunner, gui);
            //Run the animation.menu.
            animationRunner.run(menu);
            //Wait for user's choice and run it.
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }

    /**
     * Create a list organized by the order of levels for a new full game.
     *
     * @return list of levels.
     */
    private static ArrayList<LevelInformation> getListOfLevels() {
        ArrayList<LevelInformation> listOfLevels = new ArrayList<>();
        listOfLevels.add(new DirectHit());
        listOfLevels.add(new WideEasy());
        listOfLevels.add(new Green3());
        listOfLevels.add(new FinalFour());
        return listOfLevels;
    }

    /**
     * Add all options needed for the main animation.menu.
     *
     * @param menu   animation.menu variable.
     * @param runner animation runner of the animation.menu.
     * @param gui    GUI in use.
     */
    private static void addMenuOptions(Menu<Task<Void>> menu, AnimationRunner runner, GUI gui) {
        //New complete game using our list of game.levels.
        addNewGameOption(menu, runner, gui);
        //View the high score table.
        addHighScoreOption(menu, gui);
        //Quit the game.
        addQuitOption(menu);
    }

    /**
     * Add the option of playing a new game to the animation.menu.
     *
     * @param menu   animation.menu variable.
     * @param runner animation runner of the animation.menu.
     * @param gui    GUI in use.
     */
    private static void addNewGameOption(Menu<Task<Void>> menu, AnimationRunner runner, GUI gui) {
        Task newGame = () -> {
            Counter scoreCounter = new Counter();
            Counter livesCounter = new Counter();
            SoundConstants.MENU_THEME.stop();
            isSoundPlayingNow = false;
            GameFlow gameFlow = new GameFlow(gui, runner, scoreCounter, livesCounter);
            gameFlow.runLevels(getListOfLevels());
            return null;
        };
        menu.addMenuSelection(NEW_GAME_KEY, NEW_GAME, newGame);
    }

    /**
     * Add the option of viewing the high score table to the animation.menu.
     *
     * @param menu animation.menu variable.
     * @param gui  GUI in use.
     */
    private static void addHighScoreOption(Menu<Task<Void>> menu, GUI gui) {
        Task highScore = () -> {
            HighScoreAnimation highScoreAnimation = new HighScoreAnimation(gui);
            highScoreAnimation.run();
            return null;
        };
        menu.addMenuSelection(HIGH_SCORE_KEY, SCOREBOARD_TABLE, highScore);
    }

    /**
     * Add the option to quit the game to the animation.menu.
     *
     * @param menu animation.menu variable.
     */
    private static void addQuitOption(Menu<Task<Void>> menu) {
        Task quit = () -> {
            shouldRun = false;
            SoundConstants.MENU_THEME.stop();
            isSoundPlayingNow = false;
            System.exit(0);
            return null;
        };
        menu.addMenuSelection(QUIT_GAME_MENU_KEY, QUIT_GAME, quit);
    }

    /**
     * Handle the management of sound in the main menu.
     *
     * @param keyboard keyboard sensor.
     */
    private static void handleSoundManagement(KeyboardSensor keyboard) {
        //Resume playing the main theme upon return to the menu and if game isn't muted.
        if (!isSoundPlayingNow && isSoundEnabled()) {
            SoundConstants.MENU_THEME.loop();
        }
        //Mute or unmute the game.
        if (keyboard.isPressed(MUTE_KEY.toLowerCase()) || keyboard.isPressed(MUTE_KEY.toUpperCase())) {
            swapSoundEnabled();
        }
        if (!isSoundEnabled()) {
            SoundConstants.MENU_THEME.stop();
        }
    }
}
