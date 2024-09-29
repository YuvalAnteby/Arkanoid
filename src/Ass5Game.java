
import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import levels.DirectHit;
import levels.LevelInformation;
import indicators.ScoreIndicator;
import levels.WideEasy;
import util.Constants;
import util.Counter;

import java.util.ArrayList;

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
        Counter scoreCounter = new Counter();
        Counter livesCounter = new Counter();
        GameFlow gameFlow = new GameFlow(gui, animationRunner, keyboard, scoreCounter, livesCounter);
        gameFlow.runLevels(listOfLevels);
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
}
