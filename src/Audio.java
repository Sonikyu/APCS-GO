import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//TODO turn class into methods to make thread easier
//Still wip, need more storyboarding 

public class Audio extends Thread{
 
   private ArrayList<String> SoundList ;
   public static final Audio main = new Audio();
   private String root ;
   public Audio(){
      File file = new File("Audio.java");
      String path = file.getAbsolutePath();
      SoundList = new ArrayList<String>();         
      //Use root as the base string for all the sounds
      root = path.substring(0, path.length()-10) + "\\sounds\\";
      SoundList.add("Angry Birds Theme Song.wav");
   }
   
   public void playSound(int x){
      if(x < SoundList.size()){
         File sound = new File(root + SoundList.get(x));
         try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
            Clip c = AudioSystem.getClip();
            c.open(ais); //Clip opens AudioInputStream
            c.start(); //Start playing audio
			System.out.println("Playing : " + SoundList.get(x));
            //sleep thread for length of the song
            Thread.sleep((int)(c.getMicrosecondLength() * 0.001));
         } 
         catch (Exception e) {
            System.out.println(e.getMessage());
         }
      }

   }
     

   public void run(){
      //TODO fill with 
	   main.playSound(0);
	   

   }

   
}