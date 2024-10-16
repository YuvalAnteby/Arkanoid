package menu;

import game.animation.Animation;

/**
 * Interface for all menu objects. Enables adding a menu selections and animation attributes.
 *
 * @param <T> generic of the menu's options.
 * @author Yuval Anteby
 */
public interface Menu<T> extends Animation {

    /**
     * Add a new option to select from on the animation.menu.
     *
     * @param key       key to press.
     * @param text      text to appear.
     * @param returnVal the return value.
     */
    void addMenuSelection(String key, String text, T returnVal);


    /**
     * Get status.
     *
     * @return status
     */
    T getStatus();

}
