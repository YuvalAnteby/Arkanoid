package collision;

import geometry.Point;

/**
 * Class to contain a list of objects which use collidable interface.
 * @author Yuval Anteby
 */
public class CollisionInfo {

    private final Point collisionPoint;
    private final Collidable collidable;

    /**
     * Constructor for the class.
     * @param collisionPoint - the point of collision.
     * @param collidable     - amn object we can collide with
     */
    public CollisionInfo(Point collisionPoint, Collidable collidable) {
        this.collisionPoint = collisionPoint;
        this.collidable = collidable;
    }

    /**
     * Get the point at which the collision occurred.
     * @return - point of collision.
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * Get the object which took part of the collision.
     * @return - object that we collided with.
     */
    public Collidable collisionObject() {
        return collidable;
    }
}
