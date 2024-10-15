package game;

import game.animation.Animation;
import game.animation.AnimationRunner;
import game.animation.CountdownAnimation;
import game.animation.KeyPressStoppableAnimation;
import game.animation.PauseScreen;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.levels.LevelInformation;
import sprites.BallRemover;
import sprites.BlockRemover;
import sprites.Sprite;
import sprites.SpriteCollection;
import sprites.Velocity;
import sprites.collision.Collidable;
import sprites.geometry.Ball;
import sprites.geometry.Rectangle;
import sprites.indicators.LevelNameIndicator;
import sprites.indicators.LivesIndicator;
import sprites.indicators.ScoreIndicator;
import sprites.indicators.ScoreTrackingListener;
import util.Counter;
import util.MuteManager;
import util.SoundConstants;

import java.util.List;

import static util.GameConstants.GUI_HEIGHT;
import static util.GameConstants.GUI_WIDTH;
import static util.SpriteConstants.BOUND_COLOR;
import static util.SpriteConstants.BOUND_HEIGHT;
import static util.SpriteConstants.BOUND_WIDTH;
import static util.SpriteConstants.INDICATOR_TXT_SIZE;
import static util.SpriteConstants.PADDLE_COLOR;
import static util.SpriteConstants.PADDLE_HEIGHT;

/**
 * Class to handle the game's sprites animation and GUI creation.
 *
 * @author Yuval Anteby
 */
public class GameLevel implements Animation {

    /**
     * Animation and graphics variables.
     */
    private final GUI gui;
    private AnimationRunner runner;
    private boolean running;
    private final SpriteCollection sprites;
    private Paddle paddle;

    /**
     * Counters for this level and entire game.
     */
    private final Counter scoreCount, blocksRemaining, livesRemaining;
    /**
     * Game management variables.
     */
    private final GameEnvironment environment;
    private BallRemover ballRemover;
    private final LevelInformation levelInformation;
    private final KeyboardSensor keyboard;
    private String levelStatus = "playing";

    /**
     * Constructor for the game, will create a new sprite collection and environment and set the GUI size.
     *
     * @param gui          GUI in use of the game.
     * @param lvlInfo      information needed about the level.
     * @param scoreCounter counter of the score.
     * @param livesCounter counter of the user's lives.
     */
    public GameLevel(GUI gui, LevelInformation lvlInfo, Counter scoreCounter, Counter livesCounter) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.levelInformation = lvlInfo;
        this.gui = gui;
        this.keyboard = gui.getKeyboardSensor();
        this.scoreCount = scoreCounter;
        this.livesRemaining = livesCounter;
        this.blocksRemaining = new Counter();
    }

    /**
     * Add a new collidable object to the game's environment.
     *
     * @param c collidable to be added.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add a new sprite object to the game's environment.
     *
     * @param s sprite to be added.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Remove a collidable object from the game environment.
     *
     * @param c collidable to be removed.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Remove a sprite object from the game environment.
     *
     * @param s sprite object to be removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Function to initialize the game's objects.
     * Will create balls, paddle, blocks etc.
     */
    public void initialize() {
        addSprite(levelInformation.getBackground());
        initIndicatorsTracking();
        //Create the boundaries of the GUI.
        generateBounds();
        generateDeathZone();
        //Create the blocks.
        generateBlocks();
        //Create the paddle.
        generatePaddle();
        //Create the ball.
        generateBalls();
    }

    /**
     * Initialize variables related to the indicators at the top of the screen.
     */
    private void initIndicatorsTracking() {
        ScoreIndicator scoreIndicator = new ScoreIndicator(scoreCount);
        scoreIndicator.addToGame(this);
        LevelNameIndicator levelNameIndicator = new LevelNameIndicator(levelInformation.levelName());
        levelNameIndicator.addToGame(this);
        LivesIndicator livesIndicator = new LivesIndicator(livesRemaining);
        livesIndicator.addToGame(this);
    }

    /**
     * Generate the death zone of the balls.
     * in case we don't want a death zone we should swap the commented code.
     */
    private void generateDeathZone() {
        this.ballRemover = new BallRemover(this, new Counter());
        Block deathZone = new Block(new Rectangle(0, GUI_HEIGHT + 10, GUI_WIDTH, BOUND_HEIGHT), BOUND_COLOR);
        deathZone.setDeathBlock(true);
        deathZone.addToGame(this);
        //Regular bottom boundary:
        //Rectangle bottomRec = new Rectangle(new Point(0, guiHeight + 10), guiWidth, height);
    }

    /**
     * Generate blocks to be the boundaries of the GUI.
     */
    private void generateBounds() {
        Block[] bounds = {
                //Left Boundary
                new Block(new Rectangle(0, INDICATOR_TXT_SIZE, BOUND_WIDTH, GUI_HEIGHT), BOUND_COLOR),
                //Right boundary
                new Block(new Rectangle(GUI_WIDTH - BOUND_HEIGHT, INDICATOR_TXT_SIZE, BOUND_WIDTH, GUI_HEIGHT),
                        BOUND_COLOR),
                //Top boundary
                new Block(new Rectangle(0, INDICATOR_TXT_SIZE, GUI_WIDTH, BOUND_HEIGHT), BOUND_COLOR)
        };
        for (Block b : bounds) {
            b.setBoundary(true);
            b.addToGame(this);
        }
    }

    /**
     * Generate balls for the level.
     */
    private void generateBalls() {
        List<Velocity> velocities = levelInformation.initialBallVelocities();
        int numOfBalls = levelInformation.numberOfBalls();
        for (int i = 0; i < numOfBalls; i++) {
            Ball b = new Ball(velocities.get(i));
            //Add the ball to the game mechanics.
            b.addToGame(this);
            b.setGameEnvironment(this.environment);
            b.addHitListener(this.ballRemover);
            this.ballRemover.getRemainingBalls().increase(1);
        }
    }

    /**
     * Generate the paddle for the level.
     */
    public void generatePaddle() {
        //Starting value will be centered horizontally at the bottom of the screen.
        int spawnX = (GUI_WIDTH - levelInformation.paddleWidth()) / 2;
        int spawnY = GUI_HEIGHT - BOUND_HEIGHT - PADDLE_HEIGHT;
        Rectangle paddleRect = new Rectangle(spawnX, spawnY, levelInformation.paddleWidth(), PADDLE_HEIGHT);
        paddle = new Paddle(paddleRect, PADDLE_COLOR, keyboard, levelInformation.paddleSpeed());
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
        this.running = true;
        if (MuteManager.isSoundEnabled()) {
            SoundConstants.LEVEL_START.play();
        }
        this.runner.run(new CountdownAnimation(2, 3, sprites));
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
        //Handle victory or defeat of the player.
        if (blocksRemaining.getValue() <= 0) {
            handleVictory();
        } else if (ballRemover.noBallsRemain()) {
            handleNoBallsRemaining();
        }
        //Check for keyboard inputs (e.g. pausing the game).
        keyboardClickCheck();
    }

    /**
     * Handle the player's victory of this level.
     */
    private void handleVictory() {
        //Add extra points for clearing all the blocks (if needed).
        this.scoreCount.increase(100);
        System.out.println("Player won! " + scoreCount.getValue());
        this.running = false;
        this.levelStatus = "win";
    }

    /**
     * Handle the player's defeat of this level.
     */
    private void handleDefeat() {
        this.running = false;
        this.levelStatus = "lose";
        System.out.println("Player lost. " + scoreCount.getValue());
    }

    /**
     * Handle the case where no balls remain on screen.
     * If the user still has lives left then the level will continue with new balls. Otherwise, the player has lost.
     */
    private void handleNoBallsRemaining() {
        if (livesRemaining.getValue() <= 0) {
            handleDefeat();
        } else {
            if (MuteManager.isSoundEnabled()) {
                SoundConstants.EXTRA_LIFE.play();
            }
            //Restart the level.
            this.paddle.movePaddleToCenter();
            generateBalls();
            livesRemaining.decrease(1);
            this.runner.run(new CountdownAnimation(2, 3, sprites));
        }
    }

    /**
     * Function to enable all clicks by the user using the keyboard that aren't paddle movement.
     */
    private void keyboardClickCheck() {
        //Pause the game on 'p' press.
        if (this.keyboard.isPressed("p") || this.keyboard.isPressed("P")) {
            KeyPressStoppableAnimation pauseStoppableAnimation = new KeyPressStoppableAnimation(keyboard,
                    "SPACE", "Q" ,new PauseScreen());
            this.runner.run(pauseStoppableAnimation);
            //Check if the user wants to quit the game.
            if (pauseStoppableAnimation.DidPressSecondKey()) {
                this.levelStatus = "lose";
                this.running = false;
            }
        }
        //Mute or unmute the game on 'm' press.
        MuteManager.toggleMutePress(this.keyboard);
    }

    /**
     * @return 'win' if the user won the level, 'lose' if the user lost. Otherwise - 'playing'
     */
    public String getLevelStatus() {
        return levelStatus;
    }
}
