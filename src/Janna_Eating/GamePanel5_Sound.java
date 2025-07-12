package Janna_Eating;
import java.net.URL;
import javax.sound.sampled.*;

public class GamePanel5_Sound {

    private Clip clip;
    private URL[] soundURL = new URL[30];

    public GamePanel5_Sound() {
        // Initialize sound URLs
        soundURL[0] = getClass().getResource("/sound_bg.wav");
        soundURL[1] = getClass().getResource("/start_sound.wav");
        soundURL[2] = getClass().getResource("/gameOver_sound.wav");
        soundURL[3] = getClass().getResource("/cat1_sound.wav");
        soundURL[4] = getClass().getResource("/cat2_sound.wav");
        soundURL[5] = getClass().getResource("/cat3_sound.wav");
    }

    public void setSound(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound() {
        if (clip != null) {
            clip.start();
        }
    }

    public void loopSound() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void stopLooping() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0); 
        }
    }
}
