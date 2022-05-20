// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Player.java
//
// Add your name here if you work on this class:
/** @author Johnny*/ 



import restore.Coder;

public class Heart extends Entity {

	private static String TYPE = "Heart";
	private static String[] IMAGE_FILES = {"EmptyHeart.png", "HalfHeart.png", "FullHeart.png"};
	
	public Heart() {
		super(Heart.TYPE, 0, Heart.IMAGE_FILES);	
	}
	
	public Heart(Coder coder) {
		super(coder);
	}

	public void encode(Coder coder) {
    	super.encode(coder);
    }
	
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		
	}
	
}
