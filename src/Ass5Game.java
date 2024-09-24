
import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import levels.DirectHit;
import levels.LevelInformation;
import score.ScoreIndicator;
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
        ScoreIndicator scoreIndicator = new ScoreIndicator(scoreCounter);

        GameFlow gameFlow = new GameFlow(gui, animationRunner, keyboard, scoreCounter);
        gameFlow.runLevels(listOfLevels);
    }

    /**
     * Create a list organized by the order of levels.
     *
     * @return - list of levels.
     */
    private static ArrayList<LevelInformation> getListOfLevels() {
        ArrayList<LevelInformation> listOfLevels = new ArrayList<LevelInformation>();
        listOfLevels.add(new DirectHit());
        return listOfLevels;
    }
}
