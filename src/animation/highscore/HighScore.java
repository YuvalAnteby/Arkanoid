package animation.highscore;

import java.time.LocalDate;

/**
 * Class to represent a single high score in game.
 *
 * @author Yuval Anteby
 */
public class HighScore {
    private final String name;
    private int score;
    private final LocalDate date;

    /**
     * Constructor providing a score.
     *
     * @param name  name of the player.
     * @param score final score of the player.
     * @param date  date of the player's game.
     */
    public HighScore(String name, int score, LocalDate date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    /**
     * Constructor without providing a score. Used in creating a new game for the player.
     *
     * @param name name of the player.
     * @param date date of the player's game.
     */
    public HighScore(String name, LocalDate date) {
        this(name, 0, date);
    }

    /**
     * Getter for the player's name.
     *
     * @return string of the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the score.
     *
     * @return score of the player's game.
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for the date.
     *
     * @return date of the game played.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Setter for the score. Used when finishing a game.
     *
     * @param score final score of a game.
     */
    public void setScore(int score) {
        this.score = score;
    }

}
