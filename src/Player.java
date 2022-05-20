import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.Dimension;
import restore.Coder;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Player.java
//
// Add your name here if you work on this class:
/** @author Johnny Ethan */ 
public class Player extends Entity {
	public static String TYPE = "Player";
	private static int MAX_HEALTH = 100;
	private static String[] IMAGE_FILES = {"Player.png", "PlayerDamageStage1.png"};
	private static int PLAYER_SPEED = 1;
	
	public static final int INVENTORY_SIZE = 9;

	private int xDelta;
	private int yDelta;
	
	private int lastFrameAttacked;
	
	//time for player to cycle through animation
	private static final int ANIMATION_TIME = 100;

	private Item[] inventory;
	private int currentSlot;
	
	private playerDirection pD;

	public enum playerDirection { 
	NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST
	}
	
	
	public Player() {
		super(Player.TYPE, Player.MAX_HEALTH, Player.IMAGE_FILES);
		this.xDelta = 0;
		this.yDelta = 0;
		lastFrameAttacked = -ANIMATION_TIME;
		inventory = new Item[INVENTORY_SIZE];
		for (int i = 0; i < INVENTORY_SIZE; i++) {
			inventory[i] = new Item(Item.ItemType.Key);
		}
		currentSlot = 0;
	}
	
	public Player(Coder coder) {
		super(coder);
		this.xDelta = 0;
		this.yDelta = 0;
		this.lastFrameAttacked = -ANIMATION_TIME;
	}
	
	public void encode(Coder coder) {
		super.encode(coder);
	}
	
	/**
	 *  Gets player heart count for display
	 */
	public int getHeartCount() {
		int health = getHealth();
		if (health > 0) {
			if (health % 5 != 0) {
				return health/5 + 1;
			}
			return health/5;
		}
		return 0;
	}
	
	public playerDirection getPlayerDirection() {
		return pD;
	}
	
	public Item[] getInventory() {
		return inventory;
	}
	
	public int getCurrentSlot() {
		return currentSlot;
	}
	
//	public boolean pickUpItem(Item item) {
//		//TODO: pickupItem
//	}
	
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		// Player does not move automatically
		xDelta = 0;
		yDelta = 0;
		
		// Determine player costume (damaged or not)
		if ((int) info.getFrameCount() - lastFrameAttacked < ANIMATION_TIME) {
			this.setImageAtIndex(1);
		}
		else {
			this.setImageAtIndex(0);
		}
		
		// Move with arrow keys
		moveOnKeys(level, info.getKeysDown(), info.getSize());
				
		// Check for collisions
		ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
		for (int i = 0; i < visibleEntities.size(); i++) {
			Entity entity = visibleEntities.get(i);
			if (collidesWith(entity)) {
				//Debugger.main.print(this + " collided with " + entity);
				
				// TODO: Replace with the static variables
				if (entity.getType().equals("WallTile") || entity.getType().equals("DoorTile")) {
					revertLastMovement();
				}
			}
		}
	}
	
	@Override
	public void heal(int change) {
		super.heal(change);
		Debugger.main.print(getID() + " healed " + change + ", now at " + getHealth());
	}
	
	public void takeDamage(Game.GameInfo info, int change) {
		super.takeDamage(info, change);
		lastFrameAttacked = (int) info.getFrameCount();
		if (isDead()) {
			Debugger.main.print(getID() + " is dead and cannot be damaged further");
		} else {
			Debugger.main.print(getID() + " took " + change + " damage points" + ", now at " + getHealth());
		}
	}
	
	private void moveOnKeys(Level level, HashSet<Integer> keysDown, Dimension windowSize) {
		if (keysDown.contains(KeyEvent.VK_UP)) {
			yDelta -= Player.PLAYER_SPEED;
			if (!this.isOnScreen(windowSize) && getY() < 0) {
				this.setPosition(getX(), (int) windowSize.getHeight());
				level.moveRoomUp();
			}
		}
		if (keysDown.contains(KeyEvent.VK_DOWN)) {
			yDelta += Player.PLAYER_SPEED;
			if (!this.isOnScreen(windowSize) && getY() + getHeight() > windowSize.getHeight()) {
				this.setPosition(getX(), -getHeight());
				level.moveRoomDown();
			}
		}
		if (keysDown.contains(KeyEvent.VK_LEFT)) {
			xDelta -= Player.PLAYER_SPEED;
			if (!this.isOnScreen(windowSize) && getX() < 0) {
				this.setPosition((int) windowSize.getWidth(), getY());
				level.moveRoomLeft();
			}
		}
		if (keysDown.contains(KeyEvent.VK_RIGHT)) {
			xDelta += Player.PLAYER_SPEED;
			if (!this.isOnScreen(windowSize) && getX() + getWidth() > windowSize.getWidth()) {
				this.setPosition(-getWidth(), getY());
				level.moveRoomRight();
			}
		}
		if (keysDown.contains(KeyEvent.VK_1)) {
			currentSlot = 0;
		}
		if (keysDown.contains(KeyEvent.VK_2)) {
			currentSlot = 1;
		}
		if (keysDown.contains(KeyEvent.VK_3)) {
			currentSlot = 2;
		}
		if (keysDown.contains(KeyEvent.VK_4)) {
			currentSlot = 3;
		}
		if (keysDown.contains(KeyEvent.VK_5)) {
			currentSlot = 4;
		}
		if (keysDown.contains(KeyEvent.VK_6)) {
			currentSlot = 5;
		}
		if (keysDown.contains(KeyEvent.VK_7)) {
			currentSlot = 6;
		}
		if (keysDown.contains(KeyEvent.VK_8)) {
			currentSlot = 7;
		}
		if (keysDown.contains(KeyEvent.VK_9)) {
			currentSlot = 8;
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
 