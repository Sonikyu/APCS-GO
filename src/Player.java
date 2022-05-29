import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.Dimension;
import java.awt.Graphics2D;

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
	
	private PlayerWeapon weapon;
	private static final int ATTACK_COOLDOWN = 100;
	public static final int ATTACK_DURATION = 20;
	private int attackDamage = 30;
	
	public boolean isAttacking;
	private int lastFrameAttacking;
	
	private boolean speedUp = false;
	private long spedUpFrame;
	
	
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
			inventory[i] = new Item(Item.ItemType.EMPTY);
		}
		currentSlot = 0;
		pD = PlayerDirection.NORTH;
		this.weapon = new PlayerWeapon(pD, this.attackDamage);
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
	
	public void respawn() {
		setHealth(MAX_HEALTH);
		for (int i = 0; i < inventory.length; i++) {
			inventory[i].setEmpty();
		}
	}
	
	public int getAttackDamage() {
		return attackDamage;
	}
	
	public void setAttackDamage(int damageValue) {
		attackDamage = damageValue;
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
	
	public void useItem(long frameCount) {
		switch (inventory[currentSlot].getItemType()) {
		case EMPTY:
			break;
		case HEALPOT:
			this.heal(30);
			Debugger.main.print("The player healed 30 HP");
			inventory[currentSlot].setEmpty();
			Debugger.main.print("The item slot is now empty");
			break;
		case SPEEDPOT:
			speedUp = true;
			spedUpFrame = frameCount;
			inventory[currentSlot].setEmpty();
		default:
			break;
		}
	}
	

	public int firstOccur(Item.ItemType item){
		for(int i = 0; i < inventory.length; i++){
			if(inventory[i].getItemType() == item){
				return i;
			}
		}
		return -1;
	}

	public void addItem(Item item){
		int temp = firstOccur(Item.ItemType.EMPTY);
		if(temp >= 0){
			inventory[temp]=item;
		}
	}
	
	public void updateWeapon(long frameCount) {
		if (isAttacking) {
			weapon.setPosition(this);
			weapon.show();
		}
		else {
			weapon.hide();
		}
		if (frameCount - lastFrameAttacking > Player.ATTACK_DURATION) {
			weapon.setDirection(pD);
		}
		
		
	}
	
	@Override
	public void paint(Graphics2D g) {
		if (weapon.isVisible()) {
			weapon.paint(g);
		}
		super.paint(g);
	}
	
	
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		// Player does not move automatically
		xDelta = 0;
		yDelta = 0;
		
		
		long frameCount = info.getFrameCount();
		
		if (speedUp) {
			if (frameCount - spedUpFrame <= 200) {
				PLAYER_SPEED = 2;
			}
			else {
				speedUp = false;
				PLAYER_SPEED = 1;
			}
		}
		// Determine player costume (damaged or not)
		if (frameCount - lastFrameAttacked < ANIMATION_TIME) {
			this.setImageAtIndex(1);
		}
		else {
			this.setImageAtIndex(0);
		}
		
		final ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
		
		// Update current inventory slot
		inventoryUpdate(level, info.getKeysDown(), frameCount);
		
		// Update player attack
		updateWeapon(frameCount);
		weapon.cycle(level, info);
		
		// Player Attack & Check for collisions
		playerAttack(level, info.getKeysDown(), (int) info.getFrameCount());
		if (isDead()) info.restartLevel(); 
		for (int i = 0; i < visibleEntities.size(); i++) {
			Entity entity = visibleEntities.get(i);
			if (collidesWith(entity)) {				
				if (entity.isOfType("DoorTile")) {
					if (inventory[currentSlot].isOfType("Key")) {
						useItem(info.getFrameCount());
						entity.setImageAtIndex(1);
					}
				}
				else if (entity.isOfType("LevelUpTile")) {
					for (int j = 0; j < inventory.length; j++) {
						inventory[j].setEmpty();
					}
					info.nextLevel();
				}
				else if (entity.isOfType("GrassTile")) {
					info.endGame();
				}
			}
		}
		
		// Move with arrow keys
		moveOnKeysHorizontal(level, info.getKeysDown(), info.getSize());
		if (shouldRevertMovement(visibleEntities)) {
			revertLastHorizontalMovement();
		}
		moveOnKeysVertical(level, info.getKeysDown(), info.getSize());
		if (shouldRevertMovement(visibleEntities)) {
			revertLastVerticalMovement();
		}
	}
	
	boolean shouldRevertMovement(ArrayList<Entity> visibleEntities) {
		for (int i = 0; i < visibleEntities.size(); i++) {
			Entity entity = visibleEntities.get(i);
			if (collidesWith(entity)) {				
				// TODO: Replace with the static variables
				if (entity.isOfType("WallTile")) {
					return true;
				} else if (entity.isOfType(SwitchDoor.TYPE) && !((SwitchDoor)entity).isOpen()) {
					return true;
				}
				else if (entity.isOfType("DoorTile")) {
					return true;
				}
			}
		}
		return false;
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
		
	private void moveOnKeysHorizontal(Level level, HashSet<Integer> keysDown, Dimension windowSize) {
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
		updateXBy(xDelta);
	}
	
	private void moveOnKeysVertical(Level level, HashSet<Integer> keysDown, Dimension windowSize) {
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
		updateYBy(yDelta);
	}
	
	public void inventoryUpdate(Level level, HashSet<Integer> keysDown, long frameCount) {
		if (keysDown.contains(KeyEvent.VK_ENTER)) {
			useItem(frameCount);
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
	}
	
	public void playerAttack(Level level, HashSet<Integer> keysDown, int frameCount) {
		if (frameCount - lastFrameAttacking > ATTACK_DURATION) {
			isAttacking = false;
		}
		if (keysDown.contains(KeyEvent.VK_SPACE)) {
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
	private void revertLastHorizontalMovement() {
		xDelta *= -1;
		updateXBy(xDelta);
	}
	
	private void revertLastVerticalMovement() {
		yDelta *= -1;
		updateYBy(yDelta);
	}
}
 