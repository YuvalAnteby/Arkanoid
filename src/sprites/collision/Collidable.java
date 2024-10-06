package sprites.collision;

import game.Block;
import sprites.geometry.Ball;
import sprites.geometry.Point;
import sprites.geometry.Rectangle;
import sprites.Velocity;

/**
 * Interface to be used for objects that take part in collisions. (E.G: blocks and balls).
 * @author Yuval Anteby
 */
public interface Collidable {

    /**
     * Gets the rectangle involved in the sprites.collision.
     * @return - rectangle of the sprites.collision.
     */
    Rectangle getCollisionRectangle();

    /**
     * Gets the block involved in the sprites.collision.
     * @return - block of the sprites.collision.
     */
    Block getCollisionBlock();

    /**
     * Notifies the object that a sprites.collision occurred at the given point with the specified velocity.
     * Will create a new velocity based on the hit.
     * @param hitter            - ball that hit.
     * @param collisionPoint    - the point at which the sprites.collision occurred.
     * @param currentVelocity   - the velocity of the object at the time of sprites.collision.
     * @return - the new Velocity of the object after the sprites.collision.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
