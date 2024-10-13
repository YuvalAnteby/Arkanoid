package sprites.geometry;

import biuoop.DrawSurface;
import sprites.collision.HitListener;
import sprites.collision.HitNotifier;
import game.Block;
import game.GameLevel;
import game.GameEnvironment;
import sprites.Sprite;
import sprites.collision.CollisionInfo;
import sprites.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static util.GameConstants.GUI_WIDTH;
import static util.SpriteConstants.BALL_RADIUS;
import static util.SpriteConstants.BOUND_HEIGHT;
import static util.SpriteConstants.BOUND_WIDTH;
import static util.SpriteConstants.DEFAULT_COLOR;
import static util.SpriteConstants.X_BALL;
import static util.SpriteConstants.Y_BALL;

/**
 * Class to represent a ball in the GUI.
 *
 * @author Yuval Anteby
 */
public class Ball implements Sprite, HitNotifier {
    private Point center;
    private final int radius;
    private Color color;
    private Velocity velocity;
    private GameEnvironment environment;
    private final List<HitListener> hitListeners = new ArrayList<>();

    /**
     * Constructor for the ball class using integers for the center point.
     *
     * @param x        x value of the center.
     * @param y        y value of the center.
     * @param radius   ball's radius.
     * @param color    color of the ball to be filled by.
     * @param velocity the velocity of the ball for animations.
     */
    public Ball(int x, int y, int radius, Color color, Velocity velocity) {
        this.center = new Point(x, y);
        this.radius = radius;
        this.color = color;
        this.velocity = velocity;
    }

    /**
     * Constructor for the ball class using default values.
     * default: X = half the screen's width, Y = above the paddle with spacing between them, radius = 8, color = white.
     *
     * @param velocity the velocity of the ball for animations.
     */
    public Ball(Velocity velocity) {
        this(X_BALL, Y_BALL, BALL_RADIUS, DEFAULT_COLOR, velocity);
    }

    /**
     * Get the x value of the center of this ball.
     *
     * @return x integer value of the center point.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Get the y value of the center of this ball.
     *
     * @return y integer value of the center point.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Get the radius size of this ball.
     *
     * @return integer value of this ball's radius.
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     * Get the color of the ball.
     *
     * @return color of the ball.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Setter for the ball's color.
     *
     * @param color new color for the ball.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Get the ball's velocity variable.
     *
     * @return the ball's velocity.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Change the game environment variable of the ball.
     *
     * @param gameEnvironment new game environment for the ball.
     */
    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.environment = gameEnvironment;
    }

    /**
     * Change the center point of the ball according to the current velocity.
     */
    public void moveOneStep() {
        //Make sure we have a game environment set for the ball.
        if (environment == null) {
            return;
        }
        //Calculate the trajectory of the ball and get info for potential sprites.collision.
        Line path = new Line(center, this.velocity.applyToPoint(center));
        CollisionInfo hitInfo = this.environment.getClosestCollision(path);
        if (hitInfo == null) {
            //No sprites.collision was detected, keep moving.
            this.center = this.getVelocity().applyToPoint(this.center);
        } else {
            //Check if we hit a death block.
            if (hitInfo.collisionObject().getCollisionBlock().isDeathBlock()) {
                notifyExit(hitInfo.collisionObject().getCollisionBlock(), this);
            }
            // Calculate the new velocity after hitting an object.
            Velocity newVelocity = hitInfo.collisionObject().hit(this, hitInfo.collisionPoint(), this.velocity);
            // Adjust the position to be slightly away from the sprites.collision point.
            this.center = moveToCollision(hitInfo.collisionPoint(), this.velocity);
            this.velocity = newVelocity;
        }
        checkBoundaryCollision();
    }

    /**
     * Move the ball to collision point and adjust to prevent sticking to blocks.
     *
     * @param collisionPoint point of sprites.collision.
     * @param velocity       current velocity of the ball.
     * @return the new center point of the ball.
     */
    public Point moveToCollision(Point collisionPoint, Velocity velocity) {
        double adjustedX = collisionPoint.getX();
        double adjustedY = collisionPoint.getY();
        if (velocity.getDx() < 0) {
            adjustedX += this.radius;
        } else if (velocity.getDx() > 0) {
            adjustedX -= this.radius;
        }
        if (velocity.getDy() < 0) {
            adjustedY += this.radius;
        } else if (velocity.getDy() > 0) {
            adjustedY -= this.radius;
        }
        return new Point(adjustedX, adjustedY);
    }

    /**
     * Check collision on GUI boundaries and adjust accordingly the center and velocity.
     * The ball must stay entirely in the GUI, including the boundaries.
     */
    private void checkBoundaryCollision() {
        //Check top boundary.
        int minY = BOUND_HEIGHT + this.radius;
        if (this.center.getY() <= minY) {
            this.velocity.setDy(-this.velocity.getDy());
            this.center.setY(this.radius + BOUND_HEIGHT);
        }
        //Check right boundary.
        int maxX = GUI_WIDTH - BOUND_WIDTH - this.radius;
        if (this.center.getX() >= maxX) {
            this.velocity.setDx(-this.velocity.getDx());
            this.center.setX(GUI_WIDTH - this.radius - BOUND_WIDTH);
        }
        //Check left boundary.
        int minX = BOUND_WIDTH + this.radius;
        if (this.center.getX() <= minX) {
            this.velocity.setDx(-this.velocity.getDx());
            this.center.setX(this.radius + BOUND_WIDTH);
        }
    }

    /**
     * Add the ball to the game as a sprite.
     *
     * @param g instance of a game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Function to update all hit listeners upon a ball's GUI exit.
     *
     * @param beingHit the death block that the ball hit.
     * @param exitBall the ball that exist the GUI.
     */
    private void notifyExit(Block beingHit, Ball exitBall) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.onHit(beingHit, exitBall);
        }
    }

    /**
     * Function to remove this ball from the game.
     *
     * @param gameLevel game reference to remove the ball from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        if (gameLevel != null) {
            gameLevel.removeSprite(this);
            //Changing to iterator for (enhanced for) requires further code changes for it.
            for (int i = 0; i < this.hitListeners.size(); i++) {
                removeHitListener(this.hitListeners.get(i));
            }
        }
    }

    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.getRadius());
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.getRadius());
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    @Override
    public void addHitListener(HitListener hl) {
        if (hl != null) {
            this.hitListeners.add(hl);
        }
    }

    @Override
    public void removeHitListener(HitListener hl) {
        if (hl != null) {
            this.hitListeners.remove(hl);
        }
    }

    @Override
    public String toString() {
        return "center: " + center + ", r: " + radius + ", color: " + color + ", velocity: " + velocity;
    }
}

