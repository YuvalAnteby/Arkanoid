package game;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Ball;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import graphics.Sprite;
import collision.Collidable;
import animation.Velocity;
import util.Constants;

import java.awt.Color;
import java.util.List;

/**
 * Class to represent the user controlled paddle. Balls will interact with it.
 * @author Yuval Anteby
 */
public class Paddle implements Sprite, Collidable {

    private final KeyboardSensor keyboard;
    private final Block block;
    private final Rectangle shape;
    private final int movementSensitivity;

    /**
     * Constructor for the paddle.
     * @param rec           - rectangle of the paddle.
     * @param color         - color of the paddle.
     * @param keyboard      - Keyboard sensor.
     */
    public Paddle(Rectangle rec, Color color, KeyboardSensor keyboard, int movementSensitivity) {
        this.shape = rec;
        this.block = new Block(rec, color);
        this.keyboard = keyboard;
        this.movementSensitivity = movementSensitivity;
    }

    /**
     * Move the paddle to the left.
     */
    public void moveLeft() {
        double minX = Constants.BOUNDS_WIDTH;
        //Make sure the paddle won't exit the GUI and boundaries.
        if (getCollisionRectangle().getUpperLeft().getX() > minX) {
            double newX = getCollisionRectangle().getUpperLeft().getX() - movementSensitivity;
            double newY = getCollisionRectangle().getUpperLeft().getY();
            this.shape.setUpperLeft(new Point(newX, newY));
        }
    }

    /**
     * Move the paddle to the right.
     */
    public void moveRight() {
        double maxX = Constants.GUI_WIDTH - Constants.BOUNDS_WIDTH;
        //Make sure the paddle won't exist the GUI and boundaries.
        if (this.getCollisionRectangle().getUpperLeft().getX() + this.shape.getWidth() < maxX) {
            double newX = this.getCollisionRectangle().getUpperLeft().getX() + movementSensitivity;
            double newY =  this.getCollisionRectangle().getUpperLeft().getY();
            this.shape.setUpperLeft(new Point(newX, newY));
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        this.block.drawOn(d);
    }

    @Override
    public void timePassed() {
        //Paddle movement
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY) || this.keyboard.isPressed("a")
                || this.keyboard.isPressed("A")) {
            moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY) || this.keyboard.isPressed("d")
                || this.keyboard.isPressed("D")) {
            moveRight();
        }
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    @Override
    public Block getCollisionBlock() {
        return new Block(shape, Constants.PADDLE_COLOR);
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (collisionPoint == null) {
            return currentVelocity;
        }
        //divide the top line to 5 parts
        List<Line> divide = this.shape.getTopLine().divideTo5();
        // check if the point is on the top line
        if (this.shape.getTopLine().isContaining(collisionPoint) && currentVelocity.getDy() > 0) {
            for (int j = 1; j < 6; j++) {
                //Check which zone was hit.
                if (divide.get(j - 1).isContaining(collisionPoint)) {
                    //Change the velocity according to the zone's hit.
                    switch (j) {
                        case 1:
                            return Velocity.fromAngleAndSpeed(300, currentVelocity.getSpeed());
                        case 2:
                            return Velocity.fromAngleAndSpeed(330, currentVelocity.getSpeed());
                        case 3:
                            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
                        case 4:
                            return Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
                        case 5:
                            return Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
                        default:
                            return currentVelocity;
                    }
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
     * @param g     - the game reference we add to.
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
