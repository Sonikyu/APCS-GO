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
 
   public static final SFX main = new SFX();
   private int curr;
   private String root ;
   public Clip c;

   public enum Sound {
      ENTITYKILLED, DOOROPEN, ITEMOBTAINED, ITEMUSED, PLAYERATTACK, PLAYERDAMAGED
   }

   private static final String[] files = {"MonsterDie.wav", "DoorOpen.wav", "ItemObtained.wav", "ItemUsed.wav", "PlayerAttack.wav", "Oof.wav"};

   public SFX(){
    

	   root = "sounds/";
      
      curr = 0;
   }

   public void setAudio(Sound file){
      if(file.ordinal() < files.length){
         File sound = new File(root + files[file.ordinal()]);
         curr = file.ordinal();
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
   }

   public void loopAudio(){
      c.loop(Clip.LOOP_CONTINUOUSLY);
   }

   public void stopAudio(){
      c.stop();
   }
     


   public void run(Sound file){
      main.setAudio(file);
      main.playAudio();	   

}

   
}