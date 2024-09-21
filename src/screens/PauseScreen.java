package screens;

import animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import util.Constants;

import java.awt.*;

public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    public PauseScreen(KeyboardSensor keyboard) {
        this.keyboard = keyboard;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Constants.TEXT_COLOR);
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
