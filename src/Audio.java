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
   private String root ;
   private int curr;
   public Clip c;
   
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

   public int getCurr(){
      return curr;
   }

   public void increaseCurr(){
//      curr ++;
   }

   public void playSound(int x) {
	   
   }
   
   public void playAudio(){
//      c.start();
//		System.out.println("Audio Playing : " + SoundList.get(curr));
   }

   public void loopAudio() {
//      c.loop(Clip.LOOP_CONTINUOUSLY);
   }

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