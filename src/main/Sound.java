package main;

import javafx.scene.media.AudioClip;

public class Sound {

	AudioClip audioClip;
	String[] soundPath = new String[30];
	
	public Sound() {
		soundPath[0] = getClass().getResource("/Sound/BlueBoyAdventure.wav").toString();
		soundPath[1] = getClass().getResource("/Sound/coin.wav").toString();
		soundPath[2] = getClass().getResource("/Sound/powerup.wav").toString();
		soundPath[3] = getClass().getResource("/Sound/unlock.wav").toString();
		soundPath[4] = getClass().getResource("/Sound/fanfare.wav").toString();
		soundPath[5] = getClass().getResource("/Sound/hitmonster.wav").toString();
		soundPath[6] = getClass().getResource("/Sound/receivedamage.wav").toString();
		soundPath[7] = getClass().getResource("/Sound/swingweapon.wav").toString();
		soundPath[8] = getClass().getResource("/Sound/fanfare.wav").toString();
		soundPath[9] = getClass().getResource("/Sound/cursor.wav").toString();
		soundPath[10] = getClass().getResource("/Sound/burning.wav").toString();
	}
	
	public void setFile(int i) {
		try {
			audioClip = new AudioClip(soundPath[i]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		audioClip.play();
	}
	
	public void loop() {
		audioClip.setCycleCount(AudioClip.INDEFINITE);
	}
	
	public void stop() {
		audioClip.stop();
	}
}
