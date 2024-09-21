package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import graphics.BallRemover;
import graphics.BlockRemover;
import geometry.Ball;
import geometry.Point;
import geometry.Rectangle;
import graphics.Sprite;
import graphics.SpriteCollection;
import collision.Collidable;
import physics.Velocity;
import score.ScoreIndicator;
import score.ScoreTrackingListener;
import util.Constants;
import util.Counter;

import java.awt.Color;
import java.util.Random;

/**
 * Class to handle the game's sprites animation and GUI creation.
 * @author Yuval Anteby
 */
public class Game {

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private BlockRemover blockRemover;
    private BallRemover ballRemover;
    private ScoreTrackingListener scoreTrackingListener;
    private ScoreIndicator scoreIndicator;

    /**
     * Constructor for the game, will create a new sprite collection and environment and set the GUI size.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI(Constants.GUI_NAME, Constants.GUI_WIDTH, Constants.GUI_HEIGHT);
    }

    /**
     * Add a new collidable object to the game's environment.
     * @param c - collidable to be added.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add a new sprite object to the game's environment.
     * @param s - sprite to be added.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Remove a collidable object from the game environment.
     * @param c - collidable to be removed.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Remove a sprite object from the game environment.
     * @param s - sprite object to be removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Function to initialize the game's objects.
     * Will create balls, paddle, blocks etc.
     */
    public void initialize() {
        this.blockRemover = new BlockRemover(this, new Counter());
        this.ballRemover = new BallRemover(this, new Counter());
        this.scoreIndicator = new ScoreIndicator(new Counter());
        this.scoreTrackingListener = new ScoreTrackingListener(this.scoreIndicator.getScoreCounter());
        //Create the boundaries of the GUI.
        generateBounds();
        //Create the blocks.
        generateBlocks(Constants.NUM_OF_ROWS, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT,
                Constants.BOUNDS_WIDTH, Constants.BOUNDS_HEIGHT, Constants.GUI_WIDTH);
        //Create the paddle.
        generatePaddle();
        //Create the ball.
        generateBalls();
        this.scoreIndicator.addToGame(this);
    }

    /**
     * Generate blocks to be the boundaries and background of the GUI.
     */
    private void generateBounds() {
        //Background
        Rectangle backgroundRect = new Rectangle(new Point(0, Constants.SCORE_FONT_SIZE), Constants.GUI_WIDTH,
                Constants.GUI_HEIGHT);
        Block backgroundBlock = new Block(backgroundRect, Constants.BACKGROUND_COLOR);
        backgroundBlock.addBackground(this);
        //Left boundary.
        Rectangle leftRec = new Rectangle(new Point(0, Constants.SCORE_FONT_SIZE),
                Constants.BOUNDS_WIDTH, Constants.GUI_HEIGHT);
        Block leftBound = new Block(leftRec, Constants.BOUNDS_COLOR);
        leftBound.addToGame(this);
        //Right boundary.
        Rectangle rightRec = new Rectangle(
                new Point(Constants.GUI_WIDTH - Constants.BOUNDS_HEIGHT, Constants.SCORE_FONT_SIZE),
                Constants.BOUNDS_WIDTH, Constants.GUI_HEIGHT);
        Block rightBound = new Block(rightRec, Constants.BOUNDS_COLOR);
        rightBound.addToGame(this);
        //Upper boundary.
        Rectangle topRec = new Rectangle(new Point(0, Constants.SCORE_FONT_SIZE), Constants.GUI_WIDTH,
                Constants.BOUNDS_HEIGHT);
        Block topBound = new Block(topRec, Constants.BOUNDS_COLOR);
        topBound.addToGame(this);
        //For debugging - regular bottom boundary.
        //Rectangle bottomRec = new Rectangle(new Point(0, Constants.GUI_HEIGHT + 10),Constants.GUI_WIDTH,
        // Constants.BOUNDS_HEIGHT);
        //Bottom boundary.
        Rectangle bottomRec = new Rectangle(new Point(0, Constants.GUI_HEIGHT + 10),
                Constants.GUI_WIDTH, Constants.BOUNDS_HEIGHT);
        Block bottomBound = new Block(bottomRec, Constants.BOUNDS_COLOR);
        bottomBound.setDeathBlock(true);
        bottomBound.addToGame(this);
    }

    /**
     * Generate balls for the game.
     */
    private void generateBalls() {
        this.ballRemover.getRemainingBalls().increase(Constants.BALLS_AMOUNT);
        Ball[] ballsArray = new Ball[Constants.BALLS_AMOUNT];
        for (int i = 0; i < ballsArray.length; i++) {
            Point startPos = Point.randomPoint(Constants.MIN_X, Constants.MAX_X, Constants.MIN_Y, Constants.MAX_Y);
            Ball ball = new Ball(startPos, Constants.DEFAULT_RADIUS, randomColor(), Velocity.randomVelocity());
            ball.setGameEnvironment(this.environment);
            ball.addToGame(this);
            ball.addHitListener(this.ballRemover);
        }
    }

    /**
     * Generate the paddle for the game.
     * Location will be based on the size of the paddle and the size of the bounds.
     */
    public void generatePaddle() {
        Point topLeft = new Point((double) (Constants.GUI_WIDTH - Constants.PADDLE_WIDTH) / 2,
                Constants.GUI_HEIGHT - Constants.BOUNDS_HEIGHT -  Constants.PADDLE_HEIGHT);
        Rectangle paddleRec = new Rectangle(topLeft, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT);
        Paddle paddle = new Paddle(new Block(paddleRec, Constants.PADDLE_COLOR), this.gui, this.environment);
        paddle.addToGame(this);
    }

    /**
     * Generate blocks to be on the top part of the gui.
     *
     * @param rowsAmount - amount of rows of blocks to create.
     * @param blockW     - width of the blocks.
     * @param blockH     - height of the blocks.
     * @param boundW     - width of the GUI boundaries.
     * @param boundH     - height of the GUI boundaries.
     * @param guiWidth   - total width of the gui.
     */
    public void generateBlocks(int rowsAmount, int blockW, int blockH, int boundW, int boundH, int guiWidth) {
        //Generate several rows and columns of random colored blocks.
        for (int j = 0; j < rowsAmount; j++) {
            //generate row of blocks.
            Color color = randomColor();
            for (int i = 0; i < rowsAmount * 2 - j; i++) {
                double xValue = guiWidth - ((i + 1) * blockW + boundW);
                double yValue = (j + 3) * blockH + boundH + 1;
                Rectangle rec = new Rectangle(new Point(xValue, yValue), blockW, blockH);
                Block block = new Block(rec, color);
                block.addHitListener(this.blockRemover);
                block.addHitListener(this.scoreTrackingListener);
                block.addToGame(this);
            }
        }
        //Calculate the amount of blocks and update the counter.
        this.blockRemover.getRemainingBlocks().increase((3 * rowsAmount * rowsAmount + rowsAmount) / 2);
    }

    /**
     * Function to start the animation of the game.
     */
    public void run() {
        //Start the GUI and sleeper.
        Sleeper sleeper = new Sleeper();
        //Set FPS.
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        //Start animation. End the animation when there are no blocks remaining.
        while (blockRemover.getRemainingBlocks().getValue() > 0 && ballRemover.getRemainingBalls().getValue() > 0) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
        //Add extra points for clearing all the blocks (if needed).
        if (blockRemover.getRemainingBlocks().getValue() <= 0) {
            this.scoreTrackingListener.levelCleared();
        }
        if (ballRemover.getRemainingBalls().getValue() <= 0) {
            System.out.println("Player lost. " + scoreTrackingListener.toString());
        }
        if (blockRemover.getRemainingBlocks().getValue() <= 0) {
            System.out.println("Player won! " + scoreTrackingListener.toString());
        }
        //Close the gui window.
        this.gui.close();
    }

    /**
     * Create a random color for the balls.
     * Will generate 3 random numbers for RGB of the color.
     * @return - random color.
     */
    private Color randomColor() {
        Random random = new Random();
        // Generates a value between 0 and 255 for red, green and blue.
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new Color(red, green, blue);
    }

}