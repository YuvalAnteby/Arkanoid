package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import geometry.Ball;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import graphics.Sprite;
import collision.Collidable;
import physics.Velocity;
import util.Constants;

import java.awt.Color;
import java.util.List;

/**
 * Class to represent the user controlled paddle. Balls will interact with it.
 * @author Yuval Anteby
 */
public class Paddle implements Sprite, Collidable {

    private KeyboardSensor keyboard;
    private Block block;
    private final Rectangle shape;
    private GUI gui;
    private GameEnvironment environment;

    /**
     * Constructor for the paddle.
     * @param block         - block to be used as the paddle's shape.
     * @param gui           - gui for the paddle to be drawn on.
     * @param environment   - environment containing the calculation of objects.
     */
    public Paddle(Block block, GUI gui, GameEnvironment environment) {
        this.block = block;
        this.shape = block.getCollisionRectangle();
        this.gui = gui;
        this.keyboard = gui.getKeyboardSensor();
        this.environment = environment;
    }

    /**
     * Move the paddle to the left.
     * Upon reaching the left edge will go to the right.
     */
    public void moveLeft() {
        Point topLeft;
        double guiWidth = this.gui.getDrawSurface().getWidth();
        //Make sure the paddle won't exist the gui.
        if (getCollisionRectangle().getUpperLeft().getX() > 0) {
            topLeft = new Point(getCollisionRectangle().getUpperLeft().getX() - Constants.MOVEMENT_SENSITIVITY,
                    getCollisionRectangle().getUpperLeft().getY());
        } else {
            topLeft = new Point(guiWidth - Constants.BOUNDS_WIDTH - this.shape.getWidth(),
                    this.getCollisionRectangle().getUpperLeft().getY());
        }
        this.shape.setUpperLeft(topLeft);
    }

    /**
     * Move the paddle to the right.
     * Upon reaching the right edge will go to the left.
     */
    public void moveRight() {
        Point topLeft;
        double guiWidth = this.gui.getDrawSurface().getWidth();
        //Make sure the paddle won't exist the gui.
        if (this.getCollisionRectangle().getUpperLeft().getX() + this.shape.getWidth() < guiWidth) {
            topLeft = new Point(this.getCollisionRectangle().getUpperLeft().getX() + Constants.MOVEMENT_SENSITIVITY,
                    this.getCollisionRectangle().getUpperLeft().getY());
        } else {
            topLeft = new Point(0, this.getCollisionRectangle().getUpperLeft().getY());
        }
        this.shape.setUpperLeft(topLeft);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(block.getColor());
        Rectangle thisRec = this.shape;
        d.fillRectangle((int) thisRec.getUpperLeft().getX(), (int) thisRec.getUpperLeft().getY(),
                (int) thisRec.getWidth(), (int) thisRec.getHeight());
        d.setColor(Color.black);
        d.drawRectangle((int) thisRec.getUpperLeft().getX(), (int) thisRec.getUpperLeft().getY(),
                (int) thisRec.getWidth(), (int) thisRec.getHeight());
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
        //Exist game on enter press.
        if (this.keyboard.isPressed(KeyboardSensor.ENTER_KEY)) {
            this.gui.close();
        }
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    @Override
    public Block getCollisionBlock() {
        return new Block(shape, Color.ORANGE);
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
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    @Override
    public String toString() {
        return "paddle: [" + this.shape.toString() + "]";
    }
}
