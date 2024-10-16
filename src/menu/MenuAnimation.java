package menu;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import util.MuteManager;
import util.SoundConstants;

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

    private boolean isSoundPlayingNow = true;
    private boolean shouldStop;
    private final List<Selection<T>> list;
    private final KeyboardSensor keyboard;
    private T status;

    /**
     * Instantiates a new Menu animation.
     *
     * @param keyboard keyboard sensor.
     */
    public MenuAnimation(KeyboardSensor keyboard) {
        this.list = new ArrayList<>();
        this.keyboard = keyboard;
        this.shouldStop = false;
    }

    @Override
    public void addMenuSelection(String key, String text, T returnVal) {
        this.list.add(new Selection<>(key, text, returnVal));
    }

    @Override
    public T getStatus() {
        return this.status;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int xPosition = 120, yPosition = 200;
        drawBackground(d);
        //Set text color
        d.setColor(Color.WHITE);
        yPosition += 100;
        //Show all animation.menu selections.
        for (Selection<T> selection : this.list) {
            String text = "(" + selection.getKey().toUpperCase() + ") - " + selection.getText();
            d.drawText(xPosition, yPosition, text, MENU_FONT_SIZE);
            yPosition += MENU_VERTICAL_SPACING;
        }
        //Listen to user keyboard input for all animation.menu selections.
        for (Selection<T> selection : this.list) {
            String key = selection.getKey();
            if (this.keyboard.isPressed(key.toLowerCase()) || this.keyboard.isPressed(key.toUpperCase())) {
                this.status = selection.getValue();
                this.shouldStop = true;
            }
        }
        handleMenuSound();
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
     * Handle the menu's sound. Waiting for mute/ unmute clicks and playing/ stopping the sound.
     */
    private void handleMenuSound() {
        //Mute or unmute the game on 'm' press.
        MuteManager.toggleMutePress(this.keyboard);
        // Play or stop the menu theme based on the sound state
        if (MuteManager.isSoundEnabled() && !isSoundPlayingNow) {
            SoundConstants.MENU_THEME.loop();
            isSoundPlayingNow = true;
        } else if (!MuteManager.isSoundEnabled() && isSoundPlayingNow) {
            SoundConstants.MENU_THEME.stop();
            isSoundPlayingNow = false;
        }
    }

}
