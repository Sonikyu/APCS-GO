import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//TODO turn class into methods to make thread easier
//Still wip, need more storyboarding 

public class Audio extends Thread{
 
   public static void main(String[] args) {
         File file = new File("Audio.java");
         String path = file.getAbsolutePath();
         
         //Use root as the base string for all the sounds
         final String root = path.substring(0, path.length()-10) + "\\sounds";

         
         File background = new File(root + "\\Angry Birds Theme Song.wav");
         
         
         while(true) {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(background);
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
  /*
   
   private ArrayList<String> SoundList;
   private static final Audio main = new Audio();
   
   public Audio(){
      File file = new File("Audio.java");
      String path = file.getAbsolutePath();
         
      //Use root as the base string for all the sounds
      final String root = path.substring(0, path.length()-10) + "\\sounds";
      SoundList.add("Angry Birds Theme Song.wav");
   }
   
   public void playSound(int x){
      if(x < SoundList.size()){
         File sound = new File(SoundList.get(x));
         try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
            Clip c = AudioSystem.getClip();
            c.open(ais); //Clip opens AudioInputStream
            c.start(); //Start playing audio

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

   }

   */
}