import java.awt.event.KeyEvent;
import java.util.HashSet;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Player.java
//
// Add your name here if you work on this class:
/** @authors Ethan */ 
public class Player extends Entity {
	public static String ID = "Player";
	public static String TYPE = "Player";
	private static int MAX_HEALTH = 100;
	private static String IMAGE_FILE = "star.png";
	
	Player() {
		super(Player.ID, Player.TYPE, Player.MAX_HEALTH, Player.IMAGE_FILE);
	}
	
	public int getHeartCount() {
		return getHealth() / 10;
	}
	
	public void cycle(Game game) {
		HashSet<Integer> keysDown = game.getKeysDown();
		if (keysDown.contains(KeyEvent.VK_UP)) {
			if (getY() > 0) {
				updateYBy(-2);
			}
		} else if (keysDown.contains(KeyEvent.VK_DOWN)) {
			if (getY() + getSize().getHeight() < game.getSize().getHeight()) {
				updateYBy(2);
			}
		}
		if (keysDown.contains(KeyEvent.VK_LEFT)) {
			if (getX() > 0) {
				updateXBy(-2);
			}
		} else if (keysDown.contains(KeyEvent.VK_RIGHT)) {
			if (getX() + getSize().getWidth() < game.getSize().getWidth()) {
				updateXBy(2);
			}
		}
	}
}
/*
 * Entity en = entities.get(i);

 * */
 