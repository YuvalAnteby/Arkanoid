package animation.highscore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import util.LocalDateAdapter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage high scores. Will manage saving and loading to the json save file.
 *
 * @author Yuval Anteby
 */
public class HighScoreManager {
    /**
     * Default file path.
     */
    private static final String FILE_PATH = "./resources/highScores.json";
    /**
     * Max amount of high scores to appear on the screen.
     */
    private static final int MAX_HIGH_SCORES = 10;

    private List<HighScore> highScores;

    /**
     * Constructor.
     */
    public HighScoreManager() {
        highScores = new ArrayList<>();
        loadFromJson();
    }

    /**
     * load the json file and try to get the list from it, if there is no file or list - will create one.
     */
    private void loadFromJson() {
        // Register the adapter
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type highScoreListType = new TypeToken<List<HighScore>>() {
            }.getType();
            this.highScores = gson.fromJson(reader, highScoreListType);
        } catch (IOException e) {
            System.out.println("Failed to load high scores: " + e.getMessage());
        }
    }

    /**
     * Save the current list to the json using serializing by GSON.
     */
    private void saveHighScores() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Register the adapter
                .create();

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(this.highScores, writer); // Save highScores list to JSON
        } catch (IOException e) {
            System.out.println("Failed to save high scores: " + e.getMessage());
        }
    }

    /**
     * Getter for the list of high-scores.
     *
     * @return list of high-scores.
     */
    public List<HighScore> getHighScores() {
        return this.highScores;
    }

    /**
     * Add a new score to the file.
     * Will keep at most {@value MAX_HIGH_SCORES}, only the top values are kept.
     *
     * @param name  player's name.
     * @param score player's score.
     */
    public void addNewScore(String name, int score) {
        //Set the default date as today.
        HighScore newScore = new HighScore(name, score, LocalDate.now());
        addNewScore(newScore);
    }

    /**
     * Add a new score to the file.
     * Will keep at most {@value MAX_HIGH_SCORES}, only the top values are kept.
     *
     * @param newScore new High score to add.
     */
    public void addNewScore(HighScore newScore) {
        highScores.add(newScore);
        //Sort the high-scores by score in descending order.
        if (highScores.size() > 1) {
            highScores.sort((hs1, hs2) -> Integer.compare(hs2.getScore(), hs1.getScore()));
        }
        // Keep only the top MAX_HIGH_SCORES
        if (highScores.size() > MAX_HIGH_SCORES) {
            highScores = highScores.subList(0, MAX_HIGH_SCORES);
        }
        saveHighScores();
    }
}
