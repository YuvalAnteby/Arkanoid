package screens;

public class Selection<T> {

    private final String key;
    private final String text;
    private final T returnVal;

    /**
     * Constructor for a new menu Selection.
     *
     * @param key   - key needed to press for this selection.
     * @param text  - text of this selection.
     * @param val   - return value of this selection.
     */
    public Selection(String key, String text, T val) {
        this.key = key;
        this.text = text;
        this.returnVal = val;
    }

    /**
     * Gets value.
     *
     * @return - the value
     */
    public T getValue() {
        return this.returnVal;
    }

    /**
     * Gets the text.
     *
     * @return - string of selection's text.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Gets key press.
     *
     * @return - key press needed for selection.
     */
    public String getKey() {
        return this.key;
    }

}
