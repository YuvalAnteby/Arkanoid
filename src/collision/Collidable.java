package collision;

import game.Block;
import geometry.Ball;
import geometry.Point;
import geometry.Rectangle;
import physics.Velocity;

/**
 * Interface to be used for objects that take part in collisions. (E.G: blocks and balls).
 * @author Yuval Anteby
 */
public interface Collidable {

    /**
     * Gets the rectangle involved in the collision.
     * @return - rectangle of the collision.
     */
    Rectangle getCollisionRectangle();

    /**
     * Gets the block involved in the collision.
     * @return - block of the collision.
     */
    Block getCollisionBlock();

    /**
     * Notifies the object that a collision occurred at the given point with the specified velocity.
     * Will create a new velocity based on the hit.
     * @param hitter            - ball that hit.
     * @param collisionPoint    - the point at which the collision occurred.
     * @param currentVelocity   - the velocity of the object at the time of collision.
     * @return - the new Velocity of the object after the collision.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
