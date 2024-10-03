package menu;

import animation.Animation;

public interface Menu<T> extends Animation {

    /**
     * Add a new option to select from on the menu.
     * @param key       - key to press.
     * @param text      - text to appear.
     * @param returnVal - the return value.
     */
    void addMenuSelection(String key, String text, T returnVal);


    /**
     * Get status.
     * @return - status
     */
    T getStatus();

    /**
     * Add new sub menu.
     * @param key     - key to press.
     * @param text    - text to appear.
     * @param subMenu - the return value.
     */
    void addSubMenu(String key, String text, Menu<T> subMenu);
}
