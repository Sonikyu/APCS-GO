// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Johnny.java
//
// Add your name here if you work on this class:

/** @author Johnny 
 * The victory screen.
 * 
*/ 
public class EndScreen extends Entity {
	public static String TYPE = "EndScreen";
	private static String IMAGE_FILE =  "EndScreen.png";
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	public EndScreen() {
		super(TYPE, 0, IMAGE_FILE);
	}
	
	

	@Override
	public void cycle(Level level, Game.GameInfo info) {
	
	}

}
