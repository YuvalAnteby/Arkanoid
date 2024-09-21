package screens;

import animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import util.Constants;

import java.awt.*;

/**
 * Screen that will be showed when the user pause the game.
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * Constructor for the screen.
     * @param keyboard - keyboard sensor.
     */
    public PauseScreen(KeyboardSensor keyboard) {
        this.keyboard = keyboard;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Constants.TEXT_COLOR);
        d.drawText(10, d.getHeight() / 2, Constants.PAUSE_MESSAGE, Constants.PAUSE_FONT_SIZE);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
