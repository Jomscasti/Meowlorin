package Home;

import java.net.URL;

import javax.sound.sampled.*;
import javax.sound.sampled.Clip;

public class Sound {
	
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		
		soundURL[0]= getClass().getResource("/sound/bgmusic_antopia.wav");
		soundURL[1]= getClass().getResource("/sound/eat.wav");
		soundURL[2]= getClass().getResource("/sound/pickup.wav");
		soundURL[3]= getClass().getResource("/sound/fixhole.wav");
		soundURL[4] = getClass().getResource("/sound/Flip.wav");
		soundURL[5] = getClass().getResource("/sound/catmeow1.wav");
		soundURL[6] = getClass().getResource("/sound/bgmusic1.wav");

    }


	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		}catch(Exception e) {
			
		}
	}
	
	public void play() {
		
		clip.start();
	}
	
	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		if (clip != null) {
            clip.stop();
        }
	}
	
	
}
