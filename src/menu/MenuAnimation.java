package menu;

import game.animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static util.GameConstants.GAME_IMAGE;
import static util.SpriteConstants.MENU_FONT_SIZE;
import static util.SpriteConstants.MENU_VERTICAL_SPACING;

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
    //Currently not in use, meant for sub menus.
    private Menu subMenu;


    /**
     * Instantiates a new Menu animation.
     *
     * @param keyboard keyboard sensor.
     * @param gui      GUI in use.
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
            runner.run(this.subMenu);
            return (T) subMenu.getStatus();
        }
        return this.status;
    }

    @Override
    public void addSubMenu(String key, String text, Menu<T> subMenu) {
        this.list.add(new Selection<>(key, text, subMenu));
        this.subMenu = subMenu;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int xPosition = 120, yPosition = 200;
        drawBackground(d);
        //Set text color
        d.setColor(Color.WHITE);
        yPosition += 100;
        //Show all animation.menu selections.
        for (Selection selection : this.list) {
            String text = "(" + selection.getKey().toUpperCase() + ") - " + selection.getText();
            d.drawText(xPosition, yPosition, text, MENU_FONT_SIZE);
            yPosition += MENU_VERTICAL_SPACING;
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
     * @param d surface to draw on.
     */
    private void drawBackground(DrawSurface d) {
        //Create black background.
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        //Add Arkanoid logo.
        d.drawImage(0, 0, GAME_IMAGE);
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
