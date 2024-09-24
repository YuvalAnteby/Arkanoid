package game;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import graphics.*;
import geometry.Ball;
import geometry.Point;
import geometry.Rectangle;
import collision.Collidable;
import animation.Velocity;
import levels.LevelInformation;
import score.ScoreIndicator;
import score.ScoreTrackingListener;
import screens.PauseScreen;
import util.Constants;
import util.Counter;

import java.awt.Color;
import java.util.Random;

/**
 * Class to handle the game's sprites animation and GUI creation.
 *
 * @author Yuval Anteby
 */
public class GameLevel implements Animation {
    /**
     * Animation and graphics.
     */
    private final GUI gui;
    private AnimationRunner runner;
    private boolean running;
    private final SpriteCollection sprites;

    /**
     * Score.
     */
    private ScoreTrackingListener scoreTrackingListener;
    private final Counter scoreCount;
    private final Counter blocksRemaining = new Counter();

    /**
     * Game management.
     */
    private final GameEnvironment environment;
    private BallRemover ballRemover;
    private final LevelInformation levelInformation;
    private final KeyboardSensor keyboard;
    private String levelStatus = "playing";

    /**
     * Constructor for the game, will create a new sprite collection and environment and set the GUI size.
     * TODO parameters.
     */
    public GameLevel(GUI gui, KeyboardSensor keyboard, LevelInformation lvlInfo, AnimationRunner animationRunner,
                     Counter scoreCounter) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.levelInformation = lvlInfo;
        this.gui = gui;
        this.keyboard = keyboard;
        this.scoreCount = scoreCounter;

    }

    /**
     * Add a new collidable object to the game's environment.
     *
     * @param c - collidable to be added.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add a new sprite object to the game's environment.
     *
     * @param s - sprite to be added.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Remove a collidable object from the game environment.
     *
     * @param c - collidable to be removed.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Remove a sprite object from the game environment.
     *
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

        initScoreTracking();
        //Create the boundaries of the GUI.
        generateBounds();
        generateDeathZone();
        //Create the blocks.
        generateBlocks();
        //Create the paddle.
        generatePaddle();
        //Create the ball.
        generateBalls();
        addSprite(levelInformation.getBackground());
    }

    /**
     * Initialize variables related to the user's score.
     */
    private void initScoreTracking() {
        ScoreIndicator scoreIndicator = new ScoreIndicator(new Counter());
        scoreIndicator.addToGame(this);
    }

    /**
     * Generate the death zone of the balls.
     * in case we don't want a death zone we should swap the commented code.
     */
    private void generateDeathZone() {
        this.ballRemover = new BallRemover(this, new Counter());
        final Color color = Constants.BOUNDS_COLOR;
        final int guiWidth = Constants.GUI_WIDTH, guiHeight = Constants.GUI_HEIGHT;
        final int boundHeight = Constants.BOUNDS_HEIGHT;
        Block deathZone = new Block(new Rectangle(new Point(0, guiHeight + 10), guiWidth, boundHeight), color);
        deathZone.setDeathBlock(true);
        deathZone.addToGame(this);
        //Regular bottom boundary:
        //Rectangle bottomRec = new Rectangle(new Point(0, guiHeight + 10), guiWidth, height);
    }

    /**
     * Generate blocks to be the boundaries and background of the GUI.
     */
    private void generateBounds() {
        final Color color = Constants.BOUNDS_COLOR;
        final int boundWidth = Constants.BOUNDS_WIDTH, boundHeight = Constants.BOUNDS_HEIGHT;
        final int guiWidth = Constants.GUI_WIDTH, guiHeight = Constants.GUI_HEIGHT;
        final int fontSize = Constants.SCORE_FONT_SIZE;
        //Background
        Rectangle backgroundRect = new Rectangle(new Point(0, fontSize), guiWidth, guiHeight);
        Block backgroundBlock = new Block(backgroundRect, Constants.BACKGROUND_COLOR);
        backgroundBlock.addBackground(this);

        Block[] bounds = {
                new Block(new Rectangle(new Point(0, fontSize), boundWidth, guiHeight), color),
                new Block(new Rectangle(new Point(guiWidth - boundHeight, fontSize), boundWidth, guiHeight), color),
                new Block(new Rectangle(new Point(0, fontSize), guiWidth, boundHeight), color),
                new Block(new Rectangle(new Point(0, fontSize), guiWidth, boundHeight), color)

        };
        for (Block b : bounds) {
            b.addToGame(this);
        }
    }

    /**
     * Generate balls for the level.
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
     * Generate the paddle for the level.
     * Location will be based on the size of the paddle and the size of the bounds.
     */
    public void generatePaddle() {
        //Starting value will be centered horizontally at the bottom of the screen.
        double spawnX = (double) (Constants.GUI_WIDTH - Constants.PADDLE_WIDTH) / 2;
        double spawnY = Constants.GUI_HEIGHT - Constants.BOUNDS_HEIGHT - Constants.PADDLE_HEIGHT;
        int width = Constants.PADDLE_WIDTH, height = Constants.PADDLE_HEIGHT;
        Color color = Constants.PADDLE_COLOR;

        Rectangle paddleRec = new Rectangle(new Point(spawnX, spawnY), width, height);
        Paddle paddle = new Paddle(new Block(paddleRec, color), this.gui, this.environment);
        paddle.addToGame(this);
    }

    /**
     * Generate blocks to be on the top part of the GUI.
     */
    public void generateBlocks() {
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(scoreCount);
        BlockRemover blockRemover = new BlockRemover(this, blocksRemaining);
        for (Block block : levelInformation.blocks()) {
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTrackingListener);
            blocksRemaining.increase(1);
            block.addToGame(this);
        }
    }

    /**
     * Function to start the animation of the game.
     */
    public void run() {
        //Start animation. End the animation when there are no blocks remaining.
        this.runner = new AnimationRunner(this.gui);
        this.runner.run(new CountdownAnimation(2, 3, sprites));
        this.running = true;
        this.runner.run(this);
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        if (blocksRemaining.getValue() <= 0) {
            //Add extra points for clearing all the blocks (if needed).
            this.scoreCount.increase(100);
            System.out.println("Player won! " + scoreCount.getValue());
            this.running = false;
            this.levelStatus = "win";
        } else if (ballRemover.noBallsRemain()) {
            this.running = false;
            this.levelStatus = "lose";
            System.out.println("Player lost. " + scoreCount.getValue());
        }
        keyboardClickCheck();
    }

    /**
     * Create a random color for the balls.
     * Will generate 3 random numbers for RGB of the color.
     *
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

    /**
     * Function to enable all clicks by the user using the keyboard that aren't paddle movement.
     */
    private void keyboardClickCheck() {
        //Pause the game on 'p' press.
        if (this.keyboard.isPressed("p") || this.keyboard.isPressed("P")) {
            this.runner.run(new PauseScreen(this.keyboard));
        }
        //Exit game on 'enter' press.
        if (this.keyboard.isPressed(KeyboardSensor.ENTER_KEY)) {
            this.gui.close();
        }
    }

    /**
     * @return - 'win' if the user won the level, 'lose' if the user lost. Otherwise - 'playing'
     */
    public String getLevelStatus() {
        return levelStatus;
    }
}