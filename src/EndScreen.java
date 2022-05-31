import restore.Coder;
import restore.CoderException;
import restore.Encodable;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: EndScreen.java
//
// Add your name here if you work on this class:

/** @author Johnny 
 * The victory screen.
 * 
*/ 
public class EndScreen extends Entity implements Encodable {
	public static String TYPE = "EndScreen";
	private static String IMAGE_FILE = "EndScreen.png";
	
	/**
	 * Initalizes an EndScreen entity.
	 */
	public EndScreen() {
		super(TYPE, 0, IMAGE_FILE);
	}
	
	public EndScreen(Coder coder) throws CoderException {
		super(coder);
	}
	
	
	public void encode(Coder coder) {
		super.encode(coder);
	}
	
	
	/**
	 * Cycles the entity.
	 * @param level The current level.
	 * @param info The game information.
	 */
	@Override
	public void cycle(Level level, Game.GameInfo info) {
	
	}

}
