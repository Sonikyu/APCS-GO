
// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: StartScreen.java
//
// Add your name here if you work on this class:
/** @author Johnny */ 

public class StartScreen extends Entity {
	public static String TYPE = "StartScreen";
	private static String IMAGE_FILE =  "StartScreen.png";
	
	public StartScreen() {
		super(TYPE, 0, IMAGE_FILE);
	}
	
	

	@Override
	public void cycle(Level level, Game.GameInfo info) {
		if (info.getKeysDown().size() > 0) {
			info.startGame();
		}
	}
}
