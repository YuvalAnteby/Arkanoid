
import game.Game;

/**
 * Class containing the main function to initialize and start the game.
 * @author Yuval Anteby
 */
public class Ass5Game {
    /**
     * Main function.
     * @param args - not used.
     */
    public static void main(String[] args) {
       Game game = new Game();
       game.initialize();
       game.run();
    }
}
