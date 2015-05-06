package androidGame;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.Random;

public class Sound {
	static Random sound = new Random();
	static int rSound = sound.nextInt(4) +1; 
	
	public static final AudioClip MAIN = Applet.newAudioClip(Sound.class.getResource("file" + rSound + ".wav"));
	public static final AudioClip GUN = Applet.newAudioClip(Sound.class.getResource("gun.wav"));
	public static final AudioClip DIE = Applet.newAudioClip(Sound.class.getResource("death.au"));
	public static final AudioClip WIN = Applet.newAudioClip(Sound.class.getResource("win.au"));
}
