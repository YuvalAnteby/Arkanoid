package levels;

import animation.Velocity;
import game.Block;
import graphics.Sprite;

import java.util.List;

public class WideEasy implements LevelInformation {


    @Override
    public int numberOfBalls() {
        return 0;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return List.of();
    }

    @Override
    public int paddleSpeed() {
        return 0;
    }

    @Override
    public int paddleWidth() {
        return 0;
    }

    @Override
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public Sprite getBackground() {
        return null;
    }

    @Override
    public List<Block> blocks() {
        return List.of();
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 0;
    }
}
