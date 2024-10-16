package util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static util.MuteManager.isSoundEnabled;


/**
 * Class to handle playing sounds (.wav files) using java built in tools.
 *
 * @author Yuval Anteby
 */
public class SoundPlayer {

    private Clip clip;
    private boolean shouldPlay = true;
    private final String filePath;
    private final float volumeLevel;

    /**
     * Constructor for SoundPlayer. Loads the sound file into a clip.
     * Note: the built-in library works better with .wav files.
     *
     * @param filePath    the path to the sound file (e.g. "sounds/example.wav").
     * @param volumeLevel a value between 0.0f (mute) and 1.0f (maximum volume).
     */
    public SoundPlayer(String filePath, float volumeLevel) {
        this.filePath = filePath;
        this.volumeLevel = volumeLevel;
        initializeClip();
    }

    /**
     * Constructor for SoundPlayer. Loads the sound file into a clip.
     * Note: the built-in library works better with .wav files.
     *
     * @param filePath the path to the sound file (e.g. "sounds/example.wav").
     */
    public SoundPlayer(String filePath) {
        this(filePath, SoundConstants.DEFAULT_VOLUME_LEVEL);
    }

    /**
     * Reload a sound file that was closed.
     */
    private void initializeClip() {
        try {
            // Load the sound file
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            // Get a new clip resource
            clip = AudioSystem.getClip();
            // Open the audio stream in the clip
            clip.open(audioStream);
            //Set the volume.
            setVolume(volumeLevel);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            Logger logger = Logger.getLogger(SoundPlayer.class.getName());
            logger.log(Level.SEVERE, "Error reloading sound: " + filePath, e);
        }

    }

    /**
     * Play the loaded sound file once.
     * Should be used for files needed to play when calling them from doOneFrame function.
     */
    public void playOnce() {
        if (isSoundEnabled() && clip != null && shouldPlay) {
            // Check if the clip is closed, and reinitialize if necessary
            if (!clip.isOpen()) {
                initializeClip();
            }
            // Reset the clip to the start
            clip.setFramePosition(0);
            clip.start();
            shouldPlay = false;
        }
    }

    /**
     * Play the loaded sound file.
     */
    public void play() {
        if (isSoundEnabled()) {
            // Check if the clip is closed, and reinitialize if necessary
            if (!clip.isOpen()) {
                initializeClip();
            }
            // Reset the clip to the start and play
            clip.setFramePosition(0);
            clip.start();
            shouldPlay = true;
        }
    }

    /**
     * Stop the sound if it's playing.
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.setFramePosition(0);
            clip.close();  // Free up the resources after stopping
        }
    }


    /**
     * Loop the sound continuously.
     */
    public void loop() {
        if (clip != null && isSoundEnabled()) {
            // Check if the clip is closed, and reinitialize if necessary
            if (!clip.isOpen()) {
                initializeClip();
            }
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Adjust the volume of the sound.
     *
     * @param volume a value between 0.0f (mute) and 1.0f (maximum volume).
     */
    public void setVolume(float volume) {
        if (clip != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // Convert the volume level (0.0 to 1.0) to decibels (-80.0 to 6.0 dB)
            float min = volumeControl.getMinimum();  // -80.0 dB (mute)
            float max = volumeControl.getMaximum();  // +6.0 dB
            float dB = min + (max - min) * volume;   // scale volume to dB
            volumeControl.setValue(dB);
        }
    }
}
