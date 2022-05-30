import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: Audio.java
//
//Add your name here if you work on this class:
/** @author Alex */ 

public class Audio extends Thread{
 
   private ArrayList<String> SoundList ;
   public static final Audio main = new Audio();
   private int curr;
   private String root ;
   public Audio(){
//      File file = new File("Audio.java");
//      String path = file.getAbsolutePath();
      SoundList = new ArrayList<String>();         
//      //Use root as the base string for all the sounds
//     root = path.substring(0, path.length()-10) + "\\sounds\\";
	   root = "sounds/";
      
      //Add Sounds

      SoundList.add("Angry Birds Theme Song.wav");

      curr = 0;
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

   public void changeAudio(int i){
      curr = i;
   }
     

   public void run(){
      //TODO fill with
      int now = curr;
      while(now == curr){
	      main.playSound(now);
      }
	   

   }

   
}