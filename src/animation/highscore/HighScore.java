package animation.highscore;

import java.time.LocalDate;

public class HighScore {
    private final String name;
    private int score;
    private final LocalDate date;

    public HighScore(String name, int score, LocalDate date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    public HighScore(String name, LocalDate date) {
      this(name, 0, date);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
