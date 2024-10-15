package game;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import sprites.geometry.Ball;
import sprites.geometry.Line;
import sprites.geometry.Point;
import sprites.geometry.Rectangle;
import sprites.Sprite;
import sprites.collision.Collidable;
import sprites.Velocity;
import util.MuteManager;

import java.awt.Color;
import java.util.List;

import static util.GameConstants.GUI_WIDTH;
import static util.SpriteConstants.BOUND_WIDTH;

/**
 * Class to represent the user controlled paddle. Balls will interact with it.
 *
 * @author Yuval Anteby
 */
public class Paddle implements Sprite, Collidable {

    private final KeyboardSensor keyboard;
    private final Block block;
    private final Rectangle shape;
    private final int movementSensitivity;

    /**
     * Constructor for the paddle.
     *
     * @param rec                 rectangle of the paddle.
     * @param color               color of the paddle.
     * @param keyboard            Keyboard sensor.
     * @param movementSensitivity movement speed of the paddle, the higher the fast.
     */
    public Paddle(Rectangle rec, Color color, KeyboardSensor keyboard, int movementSensitivity) {
        this.shape = rec;
        this.block = new Block(rec, color);
        this.keyboard = keyboard;
        this.movementSensitivity = movementSensitivity;
    }


    /**
     * Check if pressing keys for moving left.
     * By default, the user can use the WASD and arrows keys.
     *
     * @return true if user pressed the keys for left movement, otherwise false.
     */
    private boolean isPressingLeft() {
        return this.keyboard.isPressed("a") || this.keyboard.isPressed("A")
                || this.keyboard.isPressed(KeyboardSensor.LEFT_KEY);
    }

    /**
     * Check if pressing keys for moving right.
     *
     * @return true if user pressed the keys for right movement, otherwise false.
     */
    private boolean isPressingRight() {
        return this.keyboard.isPressed("d") || this.keyboard.isPressed("D")
                || this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY);
    }

    /**
     * Move the paddle to the left.
     */
    private void moveLeft() {
        //Make sure the paddle won't exit the GUI and boundaries.
        if (getCollisionRectangle().getUpperLeft().getX() > BOUND_WIDTH) {
            double newX = getCollisionRectangle().getUpperLeft().getX() - movementSensitivity;
            double newY = getCollisionRectangle().getUpperLeft().getY();
            this.shape.setUpperLeft(new Point(newX, newY));
        }
    }

    /**
     * Move the paddle to the right.
     */
    private void moveRight() {
        double maxX = GUI_WIDTH - BOUND_WIDTH;
        //Make sure the paddle won't exist the GUI and boundaries.
        if (this.getCollisionRectangle().getUpperLeft().getX() + this.shape.getWidth() < maxX) {
            double newX = this.getCollisionRectangle().getUpperLeft().getX() + movementSensitivity;
            double newY = this.getCollisionRectangle().getUpperLeft().getY();
            this.shape.setUpperLeft(new Point(newX, newY));
        }
    }

    /**
     * Change the paddle's location to be centered horizontally.
     */
    public void movePaddleToCenter() {
        double newX = (GUI_WIDTH - this.shape.getWidth()) / 2;
        double newY = shape.getUpperLeft().getY();
        this.shape.setUpperLeft(new Point(newX, newY));
    }

    @Override
    public void drawOn(DrawSurface d) {
        this.block.drawOn(d);
    }

    @Override
    public void timePassed() {
        //Paddle movement
        if (isPressingLeft()) {
            moveLeft();
        }
        if (isPressingRight()) {
            moveRight();
        }
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    @Override
    public Block getCollisionBlock() {
        return new Block(shape, this.block.getColor());
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (collisionPoint == null) {
            return currentVelocity;
        }
        if (MuteManager.isSoundEnabled()) {
            this.block.playRandomImpactSound();
        }
        //divide the top line to 5 parts
        List<Line> divide = this.shape.getTopLine().divideTo5();
        // check if the point is on the top line
        if (this.shape.getTopLine().isContaining(collisionPoint) && currentVelocity.getDy() > 0) {
            for (int j = 1; j < 6; j++) {
                //Check which zone was hit.
                if (divide.get(j - 1).isContaining(collisionPoint)) {
                    //Change velocity according to hit. The rights zone will change the angle to the right. (same with
                    //the left zones). The closer to the paddle's center the closer the angle will be to 90 (upwards).
                    return switch (j) {
                        case 1 -> Velocity.fromAngleAndSpeed(300, currentVelocity.getSpeed());
                        case 2 -> Velocity.fromAngleAndSpeed(330, currentVelocity.getSpeed());
                        case 3 -> new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
                        case 4 -> Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
                        case 5 -> Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
                        default -> currentVelocity;
                    };
                }
            }
        } else if (this.shape.getRightLine().isContaining(collisionPoint)
                || this.shape.getLeftLine().isContaining(collisionPoint)) {
            //On side lines invert the dx of the velocity.
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        return currentVelocity;
    }

    /**
     * Add the block to the game a sprite and a collidable object.
     *
     * @param g the game reference we add to.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    @Override
    public String toString() {
        return "paddle: [" + this.shape.toString() + "]";
    }
}
