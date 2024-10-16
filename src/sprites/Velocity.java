package sprites;

import sprites.geometry.Point;

/**
 * Class to represent velocity, Specifies the change in position on the `x` and the `y` axis.
 *
 * @author Yuval Anteby
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor for the velocity class using speed for x and y-axis.
     *
     * @param dx x-axis speed.
     * @param dy y-axis speed.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Get the velocity of the x-axis.
     *
     * @return double value of the velocity for x-axis.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Get the velocity of the y-axis.
     *
     * @return double value of the velocity for y-axis.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Set the velocity using angle and speed.
     * using vectors and sin, cos definitions we set values to the dx,dy variables (assuming up is 0 degrees).
     *
     * @param angle the angle in degrees.
     * @param speed speed's vector length.
     * @return the new velocity.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.cos(Math.toRadians(angle - 90)) * speed;
        double dy = Math.sin(Math.toRadians(angle - 90)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Get the speed vector length.
     *
     * @return double of the vector's length.
     */
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Get for the angle of velocity vector.
     *
     * @return double of the angle.
     */
    public double getAngle() {
        // atan2 returns the angle in radians
        double angleRadians = Math.atan2(dy, dx);
        // Convert to degrees
        return Math.toDegrees(angleRadians);
    }

    /**
     * Change a point's coordinates according to the velocity.
     * taking a point at (x,y) and changing the coordinates to (x+dx, y+dy).
     *
     * @param p the point we would like to change its coordinates.
     * @return the new point after changes.
     */
    public Point applyToPoint(Point p) {
        if (p == null) {
            return null;
        }
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    @Override
    public String toString() {
        return "Speed: " + getSpeed() + " Angle: " + getAngle();
    }
}
