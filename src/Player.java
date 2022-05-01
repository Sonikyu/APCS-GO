import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.Dimension;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Player.java
//
// Add your name here if you work on this class:
/** @author Ethan */ 
public class Player extends Entity {
	public static String TYPE = "Player";
	private static int MAX_HEALTH = 100;
	private static String IMAGE_FILE = "Player.png";
	private static int PLAYER_SPEED = 1;

	private int xDelta;
	private int yDelta;
	
	Player() {
		super(Player.TYPE, Player.MAX_HEALTH, Player.IMAGE_FILE);
		this.xDelta = 0;
		this.yDelta = 0;
	}
	
	/**
	 *  Gets player heart count for display
	 */
	public int getHeartCount() {
		// A player has ten hearts
		return getHealth() / 10;
	}
	
	@Override
	public void cycle(Game game) {
		// Player does not move automatically
		xDelta = 0;
		yDelta = 0;
		
		// Move with arrow keys
		moveOnKeys(game.getKeysDown(), game.getSize());
				
		// Check for collisions
		ArrayList<Entity> visibleEntities = game.getVisibleEntities();
		for (int i = 0; i < visibleEntities.size(); i++) {
			Entity entity = visibleEntities.get(i);
			if (collidesWith(entity)) {
				Debugger.main.print(this + " collided with " + entity);
				
				// TODO: Replace with the static variables
				if (entity.getType() == "WallTile" || entity.getType() == "DoorTile") {
					revertLastMovement();
				}
			}
		}
	}
	
	@Override
	public void heal(int change) {
		super.heal(change);
		Debugger.main.print(this + " healed " + change);
	}
	
	@Override
	public void takeDamage(int change) {
		super.takeDamage(change);
		Debugger.main.print(this + " took " + change + " damage points");
	}
	
	private void moveOnKeys(HashSet<Integer> keysDown, Dimension windowSize) {
		if (keysDown.contains(KeyEvent.VK_UP)) {
			if (getY() > 0) {
				yDelta = -Player.PLAYER_SPEED;
			}
		}
		if (keysDown.contains(KeyEvent.VK_DOWN)) {
			if (getY() + getHeight() < windowSize.getHeight()) {
				yDelta = Player.PLAYER_SPEED;
			}
		}
		if (keysDown.contains(KeyEvent.VK_LEFT)) {
			if (getX() > 0) {
				xDelta -= Player.PLAYER_SPEED;
			}
		}
		if (keysDown.contains(KeyEvent.VK_RIGHT)) {
			if (getX() + getWidth() < windowSize.getWidth()) {
				xDelta += Player.PLAYER_SPEED;
			}
		}
		
		updateXBy(xDelta);
		updateYBy(yDelta);
	}
	
	/**
	 * Reverts the last movement made by the player
	 */
	private void revertLastMovement() {
		xDelta *= -1;
		yDelta *= -1;
		updateXBy(xDelta);
		updateYBy(yDelta);
	}
}
 