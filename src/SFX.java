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

public class SFX extends Thread{
 
   private ArrayList<String> SoundList ;
   public static final SFX main = new SFX();
   private int curr;
   private String root ;
   public Clip c;
   public SFX(){

      SoundList = new ArrayList<String>();         

	   root = "sounds/";
      
      //Add Sounds
      SoundList.add("Null.wav");
      SoundList.add("LevelTheme.wav");
      SoundList.add("BossTheme.wav");

      SoundList.add("DoorOpen.wav");
      SoundList.add("ItemObtained.wav");
      SoundList.add("ItemUsed.wav");
      SoundList.add("PlayerAttack.wav"); 
      SoundList.add("PlayerDamaged.wav");

      curr = 0;
   }
   
   public void setAudio(int x){
      if(x < SoundList.size()){
         File sound = new File(root + SoundList.get(x));
         curr = x;
         try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
            c = AudioSystem.getClip();
            c.open(ais);
            
         } 
         catch (Exception e) {
            System.out.println(e.getMessage());
         }
      }

   }

   public void playAudio(){
      c.start(); //Start playing audio
		System.out.println("Audio Playing : " + SoundList.get(curr));
      //sleep thread for length of the song
      //Thread.sleep((int)(c.getMicrosecondLength() * 0.001));
   }

   public void loopAudio(){
      c.loop(Clip.LOOP_CONTINUOUSLY);
   }

   public void stopAudio(){
      System.out.println("Audio stopped :" + SoundList.get(curr));
      c.stop();
   }
     

   public void run(int i){
	      main.setAudio(i);
         main.playAudio();	   

   }

   
}