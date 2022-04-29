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
/** @authors Ethan */ 
public class Player extends Entity {
	public static String ID = "Player";
	public static String TYPE = "Player";
	private static int MAX_HEALTH = 100;
	private static String IMAGE_FILE = "star.png";
	private static int PLAYER_SPEED = 1;

	private int framesDisabled;
	private int xDelta;
	private int yDelta;
	
	Player() {
		super(Player.ID, Player.TYPE, Player.MAX_HEALTH, Player.IMAGE_FILE);
		this.framesDisabled = 0;
		this.xDelta = 0;
		this.yDelta = 0;
	}
	
	public int getHeartCount() {
		return getHealth() / 10;
	}
	
	@Override
	public void cycle(Game game) {
		xDelta = 0;
		yDelta = 0;
		
		if (framesDisabled == 0) {
			// Move with arrow keys
			moveOnKeys(game.getKeysDown(), game.getSize());
		}
				
		ArrayList<Entity> visibleEntities = game.getVisibleEntities();
		for (int i = 0; i < visibleEntities.size(); i++) {
			Entity entity = visibleEntities.get(i);
			if (collidesWith(entity)) {
				Debugger.main.print("Player collided with " + entity);
				
				// TODO: Replace with the static variables
				if (entity.getType() == "WallTile" || entity.getType() == "DoorTile") {
					revertLastMovement();
				} else if (entity.getType() == "Enemy") {
					// Enemy enemy = (Enemy)entity;
					// harm(entity.getDamage());
					revertLastMovement();
					disableMovementForFrames(100);
				}
			}			
		}
		
		if (framesDisabled > 0) {
			framesDisabled--;
		}
	}
	
	@Override
	public void heal(int change) {
		super.heal(change);
		Debugger.main.print("Played healed " + change + " health points, currently: " + this);
	}
	
	@Override
	public void takeDamage(int change) {
		super.takeDamage(change);
		Debugger.main.print("Played took " + change + " damage points, currently: " + this);
	}
	
	private void moveOnKeys(HashSet<Integer> keysDown, Dimension windowSize) {
		if (keysDown.contains(KeyEvent.VK_UP)) {
			if (getY() > 0) {
				yDelta = -Player.PLAYER_SPEED;
			}
		} else if (keysDown.contains(KeyEvent.VK_DOWN)) {
			if (getY() + getHeight() < windowSize.getHeight()) {
				yDelta = Player.PLAYER_SPEED;
			}
		}
		if (keysDown.contains(KeyEvent.VK_LEFT)) {
			if (getX() > 0) {
				xDelta -= Player.PLAYER_SPEED;
			}
		} else if (keysDown.contains(KeyEvent.VK_RIGHT)) {
			if (getX() + getWidth() < windowSize.getWidth()) {
				xDelta += Player.PLAYER_SPEED;
			}
		}
		
		updateXBy(xDelta);
		updateYBy(yDelta);
	}
	
	private void revertLastMovement() {
		xDelta = -xDelta;
		yDelta = -yDelta;
		updateXBy(xDelta);
		updateYBy(yDelta);
	}
	
	private void disableMovementForFrames(int frames) {
		framesDisabled = frames;
	}
}
 