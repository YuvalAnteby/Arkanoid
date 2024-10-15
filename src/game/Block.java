package game;

import biuoop.DrawSurface;
import sprites.geometry.Ball;
import sprites.geometry.Point;
import sprites.geometry.Rectangle;
import sprites.Sprite;
import sprites.collision.Collidable;
import sprites.collision.HitListener;
import sprites.collision.HitNotifier;
import sprites.Velocity;
import util.MuteManager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static util.SoundConstants.HITS_SOUNDS;


/**
 * Class to represent a block on the GUI. Will be of a rectangle shape and get a color.
 *
 * @author Yuval Anteby
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private final Rectangle rectangle;
    private final Color color;
    private final List<HitListener> hitListeners = new ArrayList<>();
    private boolean deathBlock;
    private boolean boundary;

    /**
     * Constructor for the block class.
     *
     * @param rectangle rectangle that the block is made of.
     * @param color     color that the block will be filled by.
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
        this.deathBlock = false;
        this.boundary = false;
    }

    /**
     * Check if this block is a death block.
     * a hit with a death block will cause the ball to be removed from the game.
     *
     * @return true if this block is a death block, otherwise false.
     */
    public boolean isDeathBlock() {
        return this.deathBlock;
    }

    /**
     * Set this block as a death block or regular block.
     *
     * @param isDeathBlock true if this block should be a death block, false if regular block.
     */
    public void setDeathBlock(boolean isDeathBlock) {
        this.deathBlock = isDeathBlock;
    }

    /**
     * Change this block to be a boundary block or not.
     *
     * @param isBoundary true if the block is a boundary block, otherwise false.
     */
    public void setBoundary(boolean isBoundary) {
        this.boundary = isBoundary;
    }

    /**
     * Add the block to the game a sprite and a collidable object.
     *
     * @param g the game reference we add to.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Check if this block and the provided ball have the same color.
     *
     * @param ball ball to check it's color.
     * @return true if the block and ball has the same color, otherwise false.
     */
    public boolean ballColorMatch(Ball ball) {
        return this.getColor().equals(ball.getColor());
    }

    /**
     * Function to remove this block from the game.
     *
     * @param gameLevel game reference to remove the block from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        if (gameLevel != null) {
            gameLevel.removeCollidable(this);
            gameLevel.removeSprite(this);
            //Changing to iterator for (enhanced for) requires further code changes for it.
            for (int i = 0; i < this.hitListeners.size(); i++) {
                removeHitListener(this.hitListeners.get(i));
            }
        }
    }

    /**
     * Function to update all hit listeners upon a hit.
     *
     * @param hitter the ball that hit the block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.onHit(this, hitter);
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
     *
     * @return color of the block.
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
        //Play a random impact sound if sound is enabled.
        if (MuteManager.isSoundEnabled()) {
            playRandomImpactSound();
        }
        //Remove the ball if the color of the ball is different from the block.
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        return new Velocity(dx, dy);
    }

    /**
     * Play a random impact sound.
     * Will choose one from the array in SoundConstants class.
     */
    public void playRandomImpactSound() {
        Random rnd = new Random();
        int index = rnd.nextInt(HITS_SOUNDS.length);
        HITS_SOUNDS[index].play();
    }
    @Override
    public void drawOn(DrawSurface d) {
        int xTopLeft = (int) rectangle.getUpperLeft().getX(), yTopLeft = (int) rectangle.getUpperLeft().getY();
        int width = (int) rectangle.getWidth(), height = (int) rectangle.getHeight();
        d.setColor(this.color);
        d.fillRectangle(xTopLeft, yTopLeft, width, height);
        //If the block is a boundary no need for the black borders.
        if (!boundary) {
            d.setColor(Color.black);
            d.drawRectangle(xTopLeft, yTopLeft, width, height);
        }
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
