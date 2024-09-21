package geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to represent a rectangle.
 * @author Yuval Anteby
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Constructor to create new rectangle.
     * @param upperLeft - point variable of the top left point.
     * @param width - width of the rectangle.
     * @param height - height of rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Getter for the width of the rectangle.
     * @return - double value of the width.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Getter for the height of the rectangle.
     * @return - double value of the height.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Getter for the top left point of the rectangle.
     * @return - point variable of the top left point.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Getter for the top right point of the rectangle.
     * @return - point variable of the top right point.
     */
    public Point getUpperRight() {
        return new Point(this.upperLeft.getX() + this.width, this.getUpperLeft().getY());
    }

    /**
     * Getter for the bottom right point of the rectangle.
     * @return - point variable of the bottom right point.
     */
    public Point getBottomRight() {
        return new Point(this.upperLeft.getX() + this.width, this.getUpperLeft().getY() + this.height);
    }

    /**
     * Getter for the bottom left point of the rectangle.
     * @return - point variable of the bottom left point.
     */
    public Point getBottomLeft() {
        return new Point(this.upperLeft.getX(), this.getUpperLeft().getY() + this.height);
    }

    /**
     * Setter for the upper left point of the rectangle.
     * @param newUpperLeft - new upper left point to be set.
     */
    public void setUpperLeft(Point newUpperLeft) {
        this.upperLeft = newUpperLeft;
    }

    /**
     * Create a line instance using the top left and top right points of the rectangle.
     * @return - line of the top of the rectangle.
     */
    public Line getTopLine() {
        return new Line(this.upperLeft, getUpperRight());
    }

    /**
     * Create a line instance using the top right and bottom right points of the rectangle.
     * @return - line of the right of the rectangle.
     */
    public Line getRightLine() {
        return new Line(getUpperRight(), getBottomRight());
    }

    /**
     * Create a line instance using the bottom left and bottom right points of the rectangle.
     * @return - line of the bottom of the rectangle.
     */
    public Line getBottomLine() {
        return new Line(getBottomLeft(), getBottomRight());
    }

    /**
     * Create a line instance using the top left and bottom left points of the rectangle.
     * @return - line of the left of the rectangle.
     */
    public Line getLeftLine() {
        return new Line(this.upperLeft, getBottomLeft());
    }

    /**
     * Find points of intersection between this rectangle and line.
     * In case of infinite intersection points of one line of the rectangle with this line return the point closest to
     * the start point of the line provided.
     * @param line - a line to check intersection with rectangle.
     * @return - list of intersection point between the rectangle and provided line.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersections = new ArrayList<>();
        List<Line> sides = Arrays.asList(getLeftLine(), getRightLine(), getTopLine(), getBottomLine());

        for (Line side : sides) {
            Point intersection = side.intersectionWith(line);
            if (intersection != null) {
                intersections.add(intersection);
            }
        }
        return intersections;
    }

    @Override
    public String toString() {
        return "Rectangle [upperLeft=" + upperLeft + ", width=" + width + ", height=" + height + "]";
    }
}