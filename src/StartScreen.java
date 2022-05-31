import restore.Coder;
import restore.CoderException;
import restore.Encodable;

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

public class StartScreen extends Entity implements Encodable {
	public static String TYPE = "StartScreen";
	private static String IMAGE_FILE =  "StartScreen.png";
	
	/**
	 * Initializes a StartScreen entity.
	 */
	public StartScreen() {
		super(TYPE, 0, IMAGE_FILE);
	}
	
	
	public StartScreen(Coder coder) throws CoderException {
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
		if (info.getKeysDown().size() > 0) {
			info.startGame();
		}
	}
}
