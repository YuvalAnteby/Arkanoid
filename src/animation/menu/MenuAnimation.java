package animation.menu;

import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the animation of the menu.
 *
 * @param <T> generic of the menu's options.
 * @author Yuval Anteby
 */
public class MenuAnimation<T> implements Menu<T> {

    private boolean shouldStop;
    private final List<Selection> list;
    private final KeyboardSensor keyboard;
    private T status;
    private final GUI gui;
    private Menu sub;


    /**
     * Instantiates a new Menu animation.
     *
     * @param keyboard - keyboard sensor.
     * @param gui      - GUI in use.
     */
    public MenuAnimation(KeyboardSensor keyboard, GUI gui) {
        this.list = new ArrayList<>();
        this.keyboard = keyboard;
        this.shouldStop = false;
        this.gui = gui;
    }

    @Override
    public void addMenuSelection(String key, String text, T returnVal) {
        this.list.add(new Selection(key, text, returnVal));
    }

    @Override
    public T getStatus() {
        if (this.status instanceof MenuAnimation) {
            AnimationRunner runner = new AnimationRunner(this.gui);
            runner.run(this.sub);
            return (T) sub.getStatus();
        }
        return this.status;
    }

    @Override
    public void addSubMenu(String key, String text, Menu<T> subMenu) {
        this.list.add(new Selection<>(key, text, subMenu));
        this.sub = subMenu;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int x = 120, y = 200, spacing = 85, fontSize = 55;
        drawBackground(d, x, y);
        //Set text color
        d.setColor(Color.WHITE);
        y += 100;
        //Show all animation.menu selections.
        for (Selection selection : this.list) {
            String text = "(" + selection.getKey().toUpperCase() + ") - " + selection.getText();
            d.drawText(x, y, text, fontSize);
            y += spacing;
        }
        //Listen to user keyboard input for all animation.menu selections.
        for (Selection selection : this.list) {
            String key = selection.getKey();
            if (this.keyboard.isPressed(key.toLowerCase()) || this.keyboard.isPressed(key.toUpperCase())) {
                this.status = (T) selection.getValue();
                this.shouldStop = true;
            }
        }
    }

    /**
     * Create the black background with the game's logo.
     *
     * @param d - surface to draw on.
     * @param x - x value of the logo.
     * @param y - y value of the logo.
     */
    private void drawBackground(DrawSurface d, int x, int y) {
        //Create black background.
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        //Add Arkanoid logo.
        ImageIcon icon = new ImageIcon("./resources/arkanoid-logo.jpg");
        d.drawImage(0, 0, icon.getImage());
    }

    @Override
    public boolean shouldStop() {
        return this.shouldStop;
    }

    /**
     * Sets shouldStop boolean to false.
     */
    public void setFalse() {
        this.shouldStop = false;
    }
}
