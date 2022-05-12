import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
    public static void main(String[] args) {
        //This gets the path to the project, but not into /src for eclipse
        String path = new File("").getAbsolutePath() + "\\StartScreen.wav";
        //Make a File object with a path to the audio file.
        File sound = new File("C:/Users/User/Downloads/Angry Birds Theme Song.wav");
        while(2==2) {
	        try {
	            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
	            Clip c = AudioSystem.getClip();
	            c.open(ais); //Clip opens AudioInputStream
	            c.start(); //Start playing audio
	
	            //sleep thread for length of the song
	            Thread.sleep((int)(c.getMicrosecondLength() * 0.001));
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
        }
    }
}

