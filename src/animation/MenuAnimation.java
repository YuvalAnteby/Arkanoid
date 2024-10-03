package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import screens.Menu;
import screens.Selection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MenuAnimation<T> implements Menu<T> {

    private boolean shouldStop;
    private final String title;
    private final List<Selection> list;
    private final KeyboardSensor keyboard;
    private T status;
    private final GUI gui;
    private Menu sub;


    /**
     * Instantiates a new Menu animation.
     *
     * @param title     - title of option in the menu.
     * @param keyboard  - keyboard sensor.
     * @param gui       - GUI in use.
     */
    public MenuAnimation(String title, KeyboardSensor keyboard, GUI gui) {
        this.title = title;
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
        //Create black background.
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        int x = 120, start = 200;
        //Add Arkanoid logo.
        ImageIcon icon = new ImageIcon("./resources/arkanoid-logo.jpg");
        d.drawImage(0, 0, icon.getImage());
        //Set text color
        d.setColor(Color.WHITE);
        start += 100;
        //Show all menu selections.
        for (Selection selection : this.list) {
            String text = "(" + selection.getKey().toUpperCase() + ") - " + selection.getText();
            d.drawText(x, start, text, 55);
            start += 85;
        }
        //Listen to user keyboard input for all menu selections.
        for (Selection selection : this.list) {
            String key = selection.getKey();
            if (this.keyboard.isPressed(key.toLowerCase()) || this.keyboard.isPressed(key.toUpperCase())) {
                this.status = (T) selection.getValue();
                this.shouldStop = true;
            }
        }
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
