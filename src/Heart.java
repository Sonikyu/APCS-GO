// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Heart.java
//
// Add your name here if you work on this class:
/** @author Johnny*/ 



import restore.Coder;

/**
 * Thump thump! Heart is one of the little hearts on the top left that show health.
 */
public class Heart extends Entity {

	private static String TYPE = "Heart";
	private static String[] IMAGE_FILES = {"EmptyHeart.png", "HalfHeart.png", "FullHeart.png"};
	
	/**
	 * Initializes a heart entity.
	 */
	public Heart() {
		super(Heart.TYPE, 0, Heart.IMAGE_FILES);	
	}
	
	/**
	 * Initializes a heart entity.
	 * @param coder The object that creates the entity from a game string.
	 */
	public Heart(Coder coder) {
		super(coder);
	}

	/**
	 * Turns the entity into a game string.
	 * @param coder The object the entity from a game string.
	 */
	public void encode(Coder coder) {
    	super.encode(coder);
    }
	
	/**
	 * Cycles the heart.
	 */
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		
	}
	
}
