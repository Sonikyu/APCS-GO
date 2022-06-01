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
 
   private ArrayList<String> SoundList;
   public static final Audio main = new Audio();
   private String root ;
   private int curr;
   public Clip c;
  
/**
 * Creates an Audio player object
 */
   public Audio() {
//
//      SoundList = new ArrayList<String>();         
//
//	   root = "sounds/";
//      
//      //Add Sounds
//      SoundList.add("TitleTheme.wav");
//      SoundList.add("LevelTheme.wav");
//      SoundList.add("BossTheme.wav");
//      
//
//      curr = 0;
   }
   
/**
 * Sets the Audio player to a certain file
* @param x index of the file you want to play
*/
   public void setAudio(int x){
//      if(x < SoundList.size()){
//         File sound = new File(root + SoundList.get(x));
//         curr = x;
//         try {
//            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
//            c = AudioSystem.getClip();
//            c.open(ais);
//            
//         } 
//         catch (Exception e) {
//            System.out.println(e.getMessage());
//         }
//      }

   }

/**
 * 
 * @return the index of current file
 */
   public int getCurr(){
      return curr;
   }

/**
 * Increases curr by one
 */
   public void increaseCurr(){
//      curr ++;
   }


   private void playAudio(){
//      c.start();
//		System.out.println("Audio Playing : " + SoundList.get(curr));
   }

   private void loopAudio() {
//      c.loop(Clip.LOOP_CONTINUOUSLY);
   }

/**
* Stops the audio being played 
*/
   public void stopAudio(){
//      System.out.println("Audio stopped :" + SoundList.get(curr));
//      c.stop();
   }
     

   public void run(int i){
//      main.setAudio(i);
//      main.playAudio();
//      main.loopAudio();	   
   }

   
}