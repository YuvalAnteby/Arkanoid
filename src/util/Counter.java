package util;

/**
 * Class to count simple things.
 * @author Yuval Anteby
 */
public class Counter {
    private int count;

    /**
     * Default constructor to set the count at 0.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * Increase the count value.
     * @param number - number increase the counter by.
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Decrease the count value.
     * @param number - number to decrease the counter by.
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Getter for the value of the count.
     * @return - int value of the counter.
     */
    public int getValue() {
        return this.count;
    }

    @Override
    public String toString() {
        return String.valueOf(this.count);
    }
}
