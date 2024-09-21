package game;

import biuoop.DrawSurface;
import geometry.Ball;
import geometry.Point;
import geometry.Rectangle;
import graphics.Sprite;
import collision.Collidable;
import collision.HitListener;
import collision.HitNotifier;
import physics.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * Class to represent a block on the GUI. Will be of a rectangle shape and get a color.
 * @author Yuval Anteby
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle rectangle;
    private Color color;
    private List<HitListener> hitListeners = new ArrayList<>();
    private boolean deathBlock;

    /**
     * Constructor for the block class.
     * @param rectangle     - rectangle that the block is made of.
     * @param color         - color that the block will be filled by.
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
        this.deathBlock = false;
    }

    /**
     * Check if this block is a death block.
     * a hit with a death block will cause the ball to be removed from the game.
     * @return - true if this block is a death block, otherwise false.
     */
    public boolean isDeathBlock() {
        return this.deathBlock;
    }

    /**
     * Set this block as a death block or regular block.
     * @param isDeathBlock - true if this block should be a death block, false if regular block.
     */
    public void setDeathBlock(boolean isDeathBlock) {
        this.deathBlock = isDeathBlock;
    }

    /**
     * Add the block to the game a sprite and a collidable object.
     * @param g     - the game reference we add to.
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Add the background as only a sprite to the game.
     * @param g     - the game reference we add to.
     */
    public void addBackground(Game g) {
        g.addSprite(this);
    }

    /**
     * Check if this block and the provided ball have the same color.
     * @param ball - ball to check it's color.
     * @return - true if the block and ball has the same color, otherwise false.
     */
    public boolean ballColorMatch(Ball ball) {
        return this.getColor().equals(ball.getColor());
    }

    /**
     * Function to remove this block from the game.
     * @param game - game reference to remove the block from.
     */
    public void removeFromGame(Game game) {
        if (game != null) {
            game.removeCollidable(this);
            game.removeSprite(this);
            for (int i = 0; i < this.hitListeners.size(); i++) {
                removeHitListener(this.hitListeners.get(i));
            }
        }
    }

    /**
     * Function to update all hit listeners upon a hit.
     * @param hitter - the ball that hit the block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    @Override
    public Block getCollisionBlock() {
        return this;
    }

    /**
     * Getter for the block's color.
     * @return - color of the block.
     */
    public Color getColor() {
        return color;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if ((collisionPoint == null) || (currentVelocity == null)) {
            throw new IllegalArgumentException("Null exception hit function");
        }
        double dx = currentVelocity.getDx(), dy = currentVelocity.getDy();
        //Check horizontal lines collision.
        if (this.rectangle.getTopLine().isContaining(collisionPoint)
                || this.rectangle.getBottomLine().isContaining(collisionPoint)) {
            dy *= -1;
        }
        //Check vertical lines collision.
        if (this.rectangle.getLeftLine().isContaining(collisionPoint)
                || this.rectangle.getRightLine().isContaining(collisionPoint)) {
            dx *= -1;
        }
        //Remove the ball if the color of the ball is different from the block.
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        return new Velocity(dx, dy);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
        d.setColor(Color.black);
        d.drawRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    @Override
    public void timePassed() {

    }

    @Override
    public String toString() {
        return "Block [rectangle=" + rectangle + ", color=" + color + "]";
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
}