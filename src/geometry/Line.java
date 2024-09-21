package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a line in 2D made by two points.
 * @author Yuval Anteby
 */
public class Line {
    //Threshold for double calculation.
    private static final double THRESHOLD = 0.0001;

    private final Point start;
    private final Point end;

    /**
     * Constructor for the line class made by two points.
     * @param start - First point. (where the line starts).
     * @param end - Second point. (where the line ends).
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
    }

    /**
     * Constructor for the line class made by 2 pairs of x,y.
     * @param x1 - x value of where the line starts.
     * @param y1 - y value of where the line starts.
     * @param x2 - x value of where the line ends.
     * @param y2 - y value of where the line ends.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Function to calculate the length of the line. (the distance between the start and end points).
     * @return - the length of the line in double.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Function to get the middle point of our line.
     * @return - point in the middle of the line (between start and end points)
     */
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2, (this.start.getY() + this.end.getY()) / 2);
    }

    /**
     * Getter for the start point.
     *
     * @return - start point of the line.
     */
    public Point start() {
        return new Point(this.start.getX(), this.start.getY());
    }

    /**
     * Getter for the end point.
     *
     * @return - end point of the line.
     */
    public Point end() {
        return new Point(this.end.getX(), this.end.getY());
    }

    /**
     * Function to calculate the incline of our line.
     * (meant for calculation purposes).
     * Using the incline calculation from math classes: m = (y1 - y2) / (x1 - x2)
     * @return - value of the incline in double.
     */
    public double incline() {
        if (this.start.getX() == this.end.getX()) {
            return Double.POSITIVE_INFINITY;
        }
        return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
    }

    /**
     * Function to find the y value once x value is 0 regardless of if it's between the start and end point or not.
     * (meant for calculation purposes).
     * Using the equation: m = (y1 - yB) / (x1 - 0) we get to this calculation
     * @return - value of y in double.
     */
    public double x0Point() {
        if (this.incline() == Double.POSITIVE_INFINITY) {
            return Double.NaN;
        }
        return this.start.getY() - this.incline() * this.start.getX();
    }

    /**
     * Find the min x value of the line.
     * @return - double value of min x.
     */
    public double minX() {
        return Math.min(this.start.getX(), this.end.getX());
    }

    /**
     * Find the max x value of the line.
     * @return - double value of max x.
     */
    public double maxX() {
        return Math.max(this.start.getX(), this.end.getX());
    }

    /**
     * Find the min y value of the line.
     * @return - double value of min y.
     */
    public double minY() {
        return Math.min(this.start.getY(), this.end.getY());
    }

    /**
     * Find the max y value of the line.
     * @return - double value of max y.
     */
    public double maxY() {
        return Math.max(this.start.getY(), this.end.getY());
    }

    /**
     * Check if the line is parallel to Y-axis.
     *
     * @return - true if the line is parallel to Y-axis, otherwise false.
     */
    public boolean isParallelY() {
        return Math.abs(this.start.getX() - this.end.getX()) < THRESHOLD;
    }

    /**
     * Check if the line is parallel to X-axis.
     *
     * @return - true if the line is parallel to X-axis, otherwise false.
     */
    public boolean isParallelX() {
        return Math.abs(this.start.getY() - this.end.getY()) < THRESHOLD;
    }

    /**
     * Checks if a point is within the range of the finite line segment.
     *
     * @param point - point to be checked.
     * @return - true if the point is within the range, otherwise false.
     */
    public boolean inLineRange(Point point) {
        return ((point.getX() - minX() >= -THRESHOLD && maxX() - point.getX() >= -THRESHOLD)
                && (point.getY() - minY() >= -THRESHOLD && maxY() - point.getY() >= -THRESHOLD));
    }

    /**
     * Checks if a point is on the infinite line.
     *
     * @param point - point to be checked.
     * @return - true if the point is on the line, otherwise false.
     */
    public boolean isOnInfiniteLine(Point point) {
        if (Math.abs(incline() * point.getX() + x0Point() - point.getY()) <= THRESHOLD) {
            return this.inLineRange(point);
        }
        return false;
    }

    /**
     * Find the intersection point between 2 lines.
     * Using determinant calculations from linear algebra we can find if lines are intersection and where.
     * @param other - line to check intersection with.
     * @return - if exists the intersection point, otherwise null.
     */
    public Point intersectionWith(Line other) {
        //This line (AB) represented as a1x + b1y = c1.
        double a1 = this.end.getY() - this.start.getY();
        double b1 = this.start.getX() - this.end.getX();
        double c1 = a1 * this.start.getX() + b1 * this.start.getY();
        //Other line (CD) represented as a2x + b2y = c2.
        double a2 = other.end.getY() - other.start.getY();
        double b2 = other.start.getX() - other.end.getX();
        double c2 = a2 * other.start.getX() + b2 * other.start.getY();
        //Calculate the determinant.
        double determinant = a1 * b2 - a2 * b1;

        if (determinant == 0) {
            // Lines are parallel - no intersections.
            return null;
        } else {
            double x = (b2 * c1 - b1 * c2) / determinant;
            double y = (a1 * c2 - a2 * c1) / determinant;
            Point intersection = new Point(x, y);
            // Check if the intersection point is on both line segments.
            if (this.isContaining(intersection) && other.isContaining(intersection)) {
                return intersection;
            } else {
                return null;
            }
        }
    }

    /**
     * Check if this line intersects with another line.
     *
     * @param other - line to be checked with.
     * @return - true if the lines intersect, otherwise false.
     */
    public boolean isIntersecting(Line other) {
        Point intersectionPoint;
        //In case one of the points is on the other line there is an intersections.
        if (other.isOnInfiniteLine(this.start) || other.isOnInfiniteLine(this.end)) {
            return true;
        }
        if (this.isOnInfiniteLine(other.start) || this.isOnInfiniteLine(other.end)) {
            return true;
        }
        //If both lines are not parallel to X and Y-axis or each other, then we can calculate using regular lines.
        if (!this.isParallelY() && !this.isParallelX()
                && !other.isParallelY() && !other.isParallelX()) {
            if (Math.abs(this.incline() - other.incline()) > THRESHOLD) {
                intersectionPoint = this.intersectionPointForRegularLines(other);
                if (intersectionPoint != null) {
                    return true;
                }
            }
        }
        //in case one or both are parallel to one or both axis.
        if (this.isParallelY() || this.isParallelX() || other.isParallelY() || other.isParallelX()) {
            intersectionPoint = this.findIntersectionParallelAxis(other);
            if (intersectionPoint != null) {
                return true;
            }
            return this.isContaining(other);
        }
        return false;
    }

    /**
     * Checks if a line finite segment is within the range of another line finite segment.
     *
     * @param other - line to be checked with.
     * @return - true if the other line segment is within this line, false otherwise.
     */
    public boolean isContaining(Line other) {
        if (Math.abs(this.incline() - other.incline()) >= THRESHOLD
                || Math.abs(this.x0Point() - other.x0Point()) >= THRESHOLD) {
            return false;
        }
        return ((this.inLineRange(other.start()) || this.inLineRange(other.end()))
                || (other.inLineRange(this.start()) || other.inLineRange(this.end())));
    }

    /**
     * Find intersection points between lines when one or both are parallel to X,Y-axis.
     * We will take into account cases where one or both is parallel to any of the axis.
     * @param other - line to be checked with.
     * @return - intersection point if exists, otherwise null.
     */
    public Point findIntersectionParallelAxis(Line other) {
        Point intersectionPoint;
        //The case which this line is parallel to Y-axis.
        if (this.isParallelY()) {
            //In case both lines parallel to Y-axis they need to have the same x value of points.
            if (other.isParallelY()) {
                if (Math.abs(this.start.getX() - other.start().getX()) > THRESHOLD) {
                    return null;
                }
                //Check intersection of lines with equal inclines.
                return this.intersectionSameIncline(other);
            }
            //If our is parallel to Y-axis and the other to X-axis we check if lines have this point in finite segment.
            if (other.isParallelX()) {
                intersectionPoint = new Point(this.start().getX(), other.start().getY());
                if (other.inLineRange(intersectionPoint)) {
                    return intersectionPoint;
                }
                return null;
            }
            //The other line is regular line, we use the formula y=mx+b to calculate if lines have this point
            // in finite segment.
            intersectionPoint = new Point(this.start().getX(),
                    other.incline() * this.start().getX() + other.x0Point());
            if (other.inLineRange(intersectionPoint) && this.inLineRange(intersectionPoint)) {
                return intersectionPoint;
            }
            return null;
        }
        //The case which this line is parallel to X-axis.
        if (this.isParallelX()) {
            //In case both lines parallel to X-axis they need to have the same y value of points.
            if (other.isParallelX()) {
                if (Math.abs(this.start.getY() - other.start().getY()) > THRESHOLD) {
                    return null;
                }
                //Check intersection of lines with equal inclines.
                return this.intersectionSameIncline(other);
            }
            //If our is parallel to X-axis and the other to Y-axis we check if lines have this point in finite segment.
            if (other.isParallelY()) {
                Point intersection3 = new Point(other.start().getX(), this.start().getY());
                if (other.inLineRange(intersection3) && this.inLineRange(intersection3)) {
                    return intersection3;
                }
                return null;
            }
            //The other line is regular line, we use the formula y=mx+b to calculate if lines have this point
            // in finite segment.
            intersectionPoint = new Point((this.start().getY() - other.x0Point()) / other.incline(),
                    this.start().getY());
            if (this.inLineRange(intersectionPoint) && other.inLineRange(intersectionPoint)) {
                return intersectionPoint;
            }
            return null;
        }
        return null;
    }

    /**
     * Finds the closest intersection point to the start of the line with a given rectangle.
     *
     * @param rectangle - the rectangle to check for intersections with.
     * @return - closest intersection point, otherwise null if there are no intersections.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rectangle) {
        List<Point> intersections = rectangle.intersectionPoints(this);
        if (intersections.isEmpty()) {
            return null;
        }
        double closestDistance = intersections.get(0).distance(this.start);
        Point closest = intersections.get(0);
        for (Point point : intersections) {
            if (point.distance(this.start) < closestDistance) {
                closestDistance = point.distance(this.start);
                closest = point;
            }
        }
        return closest;
    }

    /**
     * Find the intersection point for lines with equal inclines.
     * We will check if a point of one line is on the other's finite segment.
     * @param other - line to be checked with.
     * @return - if there is an intersection returns the point, otherwise null.
     */
    public Point intersectionSameIncline(Line other) {
        if (this.start.equals(other.start()) && !(this.inLineRange(other.end()) && !(other.inLineRange(this.end())))) {
            return this.start;
        }
        if (this.end.equals(other.end()) && !(this.inLineRange(other.start()) || other.inLineRange(this.start()))) {
            return this.end;
        }
        if (this.start.equals(other.end()) && !(this.inLineRange(other.start()) || other.inLineRange(this.end()))) {
            return this.start;
        }
        if (this.end.equals(other.start()) && !(this.inLineRange(other.end()) || other.inLineRange(this.start()))) {
            return this.end;
        }
        return null;
    }

    /**
     * Finds the intersection point for line which are not parallel to axis or each other.
     *
     * @param other - line to be checked with.
     * @return - if there is an intersection returns the point, otherwise null.
     */
    public Point intersectionPointForRegularLines(Line other) {
        //Check if start/ end points are the same between the lines, if they are then it's the intersection point.
        if (this.start.equals(other.start())) {
            return this.start;
        }
        if (this.start.equals(other.end())) {
            return this.start;
        }
        if (this.end.equals(other.end())) {
            return this.end;
        }
        if (this.end.equals(other.start())) {
            return this.end;
        }
        //use the formula y=mx+b to calculate the x and y values of intersection points.
        double intersectionX = (other.x0Point() - this.x0Point()) / (this.incline() - other.incline());
        double intersectionY = this.incline() * intersectionX + this.x0Point();
        Point intersection = new Point(intersectionX, intersectionY);
        //If the point is on both the finite segments of the lines then it's an intersection point.
        if (this.inLineRange(intersection) && other.inLineRange(intersection)) {
            return intersection;
        }
        return null;
    }

    /**
     * Checks if a point is on the finite line.
     *
     * @param point - point to be checked with.
     * @return - true if the point lies on the line segment, otherwise false.
     */
    public boolean isContaining(Point point) {
        if (this.isParallelY()) {
            //For parallel to Y-axis make sure the x values are equal.
            if (Math.abs(point.getX() - this.start.getX()) > THRESHOLD) {
                return false;
            }
            //Make sure the y value is in range of the finite line.
            return point.getY() >= minY() - THRESHOLD && point.getY() <= maxY() + THRESHOLD;
        }
        if (this.isParallelX()) {
            //For parallel to X-axis make sure the y values are equal.
            if (Math.abs(point.getY() - this.start.getY()) > THRESHOLD) {
                return false;
            }
            //Make sure the x value is in range of the finite line.
            return point.getX() >= minX() - THRESHOLD && point.getX() <= maxX() + THRESHOLD;
        }
        //Use the formula y=mx+b to find if putting the point in our line matches and in range of finite line.
        double expectedY = incline() * point.getX() + x0Point();
        return Math.abs(point.getY() - expectedY) <= THRESHOLD
                && point.getX() >= minX() - THRESHOLD && point.getX() <= maxX() + THRESHOLD
                && point.getY() >= minY() - THRESHOLD && point.getY() <= maxY() + THRESHOLD;
    }

    /**
     * divide line to 5 parts for paddle.
     * @return - list of lines made of the 5 zones.
     */
    public List<Line> divideTo5() {
        List<Line> zones = new ArrayList<>();
        Point p1 = this.start();
        double size = Math.abs(this.end.getX() - this.start.getX()) / 5;
        for (int i = 0; i < 5; i++) {
            Point p2 = new Point(p1.getX() + size, this.start.getY());
            zones.add(new Line(p1, p2));
            p1 = p2;
        }
        //return list of lines
        return zones;
    }

}