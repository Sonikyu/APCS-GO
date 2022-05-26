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
/** @author Johnny Ethan Alex*/ 
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
	
	private static final int ATTACK_COOLDOWN = 100;
	public static final int ATTACK_DURATION = 80;
	private int ATTACK_DAMAGE = 30;
	
	public boolean isAttacking;
	private int lastFrameAttacking;
	
	
	private PlayerDirection pD;

	public enum PlayerDirection { 
	NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST
	}
	
	
	public Player() {
		super(Player.TYPE, Player.MAX_HEALTH, Player.IMAGE_FILES);
		this.xDelta = 0;
		this.yDelta = 0;
		lastFrameAttacked = -ANIMATION_TIME;
		inventory = new Item[INVENTORY_SIZE];
		for (int i = 0; i < INVENTORY_SIZE; i++) {
			inventory[i] = new Item(Item.Object.EMPTY);
		}
		currentSlot = 0;
		pD = PlayerDirection.NORTH;
	}
	
	public Player(Coder coder) {
		super(coder);
		this.xDelta = 0;
		this.yDelta = 0;
		this.lastFrameAttacked = -ANIMATION_TIME;
		inventory = new Item[INVENTORY_SIZE];
		for (int i = 0; i < INVENTORY_SIZE; i++) {
			inventory[i] = new Item(coder);
		}
		currentSlot = coder.decodeInt();
		pD = PlayerDirection.NORTH;
	}
	
	public void encode(Coder coder) {
		super.encode(coder);
		for (int i = 0; i < INVENTORY_SIZE; i++) {
			coder.encode(inventory[i]);
		}
		coder.encode(currentSlot);
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
	
	public int getAttackDamage() {
		return ATTACK_DAMAGE;
	}
	
	public void setAttackDamage(int damageValue) {
		ATTACK_DAMAGE = damageValue;
	}
	
	public PlayerDirection getPlayerDirection() {
		return pD;
	}
	
	public Item[] getInventory() {
		return inventory;
	}
	
	public int getCurrentSlot() {
		return currentSlot;
	}
	
	public void useItem() {
		switch (inventory[currentSlot].getObject()) {
		case EMPTY:
			break;
		case HEALPOT:
			this.heal(30);
			Debugger.main.print("The player healed 30 HP");
			inventory[currentSlot] = new Item(Item.Object.EMPTY);
			break;
		default:
			break;
		}
	}

	public int firstOccur(Item.Object item){
		for(int i = 0; i < inventory.length; i++){
			if(inventory[i].getObject() == item){
				return i;
			}
		}
		return -1;
	}

	public void addItem(Item item){
		int temp = firstOccur(Item.Object.EMPTY);
		if(temp >= 0){
			inventory[temp]=item;
		}
	}

	
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
		
		// Update current inventory slot
		inventoryUpdate(level, info.getKeysDown());
		
		// Player Attack
		playerAttack(level, info.getKeysDown(), (int) info.getFrameCount());
		
		// Check for collisions
		ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
		for (int i = 0; i < visibleEntities.size(); i++) {
			Entity entity = visibleEntities.get(i);
			if (collidesWith(entity)) {
				//Debugger.main.print(this + " collided with " + entity);
				
				// TODO: Replace with the static variables
				if (entity.getType().equals("WallTile")) {
					revertLastMovement();
				}
				else if (entity.getType().equals("DoorTile")) {
					if (inventory[currentSlot].getType().equals("Key")) {
						useItem();
						entity.setImageAtIndex(1);
					}
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
		super.takeDamage(change);
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
			this.pD = PlayerDirection.NORTH;
			if (!this.isOnScreen(windowSize) && getY() < 0) {
				this.setPosition(getX(), (int) windowSize.getHeight());
				level.moveRoomUp();
				
			}
		}
		if (keysDown.contains(KeyEvent.VK_DOWN)) {
			yDelta += Player.PLAYER_SPEED;
			this.pD = PlayerDirection.SOUTH;
			if (!this.isOnScreen(windowSize) && getY() + getHeight() > windowSize.getHeight()) {
				this.setPosition(getX(), -getHeight());
				level.moveRoomDown();
			}
		}
		if (keysDown.contains(KeyEvent.VK_LEFT)) {
			xDelta -= Player.PLAYER_SPEED;
			this.pD = PlayerDirection.WEST;
			if (!this.isOnScreen(windowSize) && getX() < 0) {
				this.setPosition((int) windowSize.getWidth(), getY());
				level.moveRoomLeft();
			}
		}
		if (keysDown.contains(KeyEvent.VK_RIGHT)) {
			xDelta += Player.PLAYER_SPEED;
			this.pD = PlayerDirection.EAST;
			if (!this.isOnScreen(windowSize) && getX() + getWidth() > windowSize.getWidth()) {
				this.setPosition(-getWidth(), getY());
				level.moveRoomRight();
			}
		}
		if (keysDown.contains(KeyEvent.VK_SPACE)) {
			useItem();
		}
		
		updateXBy(xDelta);
		updateYBy(yDelta);
	}
	
	public void inventoryUpdate(Level level, HashSet<Integer> keysDown) {
		
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
	}
	
	public void playerAttack(Level level, HashSet<Integer> keysDown, int frameCount) {
		if (frameCount - lastFrameAttacking > ATTACK_DURATION) {
			isAttacking = false;
		}
		if (keysDown.contains(KeyEvent.VK_X)) {
			if (frameCount - lastFrameAttacking > ATTACK_COOLDOWN) {
				isAttacking = true;
				lastFrameAttacking = frameCount;
			}
		}
	}
	
	public int getLastFrameAttacking() {
		return lastFrameAttacking;
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
 