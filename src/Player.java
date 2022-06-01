import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.Dimension;
import java.awt.Graphics2D;
import restore.Coder;
import restore.CoderException;

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
	public enum Direction {
		NORTH, EAST, SOUTH, WEST
	}
	
	public static String TYPE = "Player";
	private static int MAX_HEALTH = 100;
	private static String[] IMAGE_FILES = {"Player.png", "PlayerDamageStage1.png"};
	private static int PLAYER_SPEED = 1;

	private Heart[] healthBar;
	private TimerDisplay timer;

	private int xDelta;
	private int yDelta;

	private long lastFrameAttacked;

	//time for player to cycle through animation
	private static final int ANIMATION_TIME = 100;

	private InventorySlot[] inventory;
	private int currentSlot;
	public static final int INVENTORY_SIZE = 9;

	private PlayerWeapon weapon;
	private static final int ATTACK_COOLDOWN = 100;
	public static final int ATTACK_DURATION = 20;
	private static int attackDamage = 30;

	public boolean isAttacking;
	private long lastFrameAttacking;

	private boolean speedUp = false;
	private long spedUpFrame;


	/**
	 * Initalizes the player entity.
	 */
	public Player() {
		super(Player.TYPE, Player.MAX_HEALTH, Player.IMAGE_FILES);
		this.xDelta = 0;
		this.yDelta = 0;
		lastFrameAttacked = -ANIMATION_TIME;
		currentSlot = 0;
		this.weapon = new PlayerWeapon(this.attackDamage);
		setUpHealthAndInventoryAndTimer();
	}

	public Player(Coder coder) throws CoderException {
		super(coder);
		this.xDelta = 0;
		this.yDelta = 0;
		this.lastFrameAttacked = -ANIMATION_TIME;
		currentSlot = coder.decodeInt();
		this.weapon = new PlayerWeapon(coder);
		setUpHealthAndInventoryAndTimer();
	}

	public void encode(Coder coder) {
		super.encode(coder);
		for (int i = 0; i < INVENTORY_SIZE; i++) {
			coder.encode(inventory[i]);
		}
		coder.encode(currentSlot);
		weapon.encode(coder);
	}

	/**
	 *  Gets the number of half-hearts to be displayed.
	 *  @return The number of half-hearts to be displayed.
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
	
	/**
	 * Respawns the player.
	 */
	public void respawn() {
		setHealth(MAX_HEALTH);
		for (int i = 0; i < inventory.length; i++) {
			inventory[i].getSlotItem().setEmpty();
		}
	}

	/**
	 * Gets the player's damage value.
	 * @return The player's damage value.
	 */
	public int getAttackDamage() {
		return attackDamage;
	}

	/**
	 * Sets the player's damage value.
	 * @param damageValue The player's new damage value.
	 */
	public void setAttackDamage(int damageValue) {
		attackDamage = damageValue;
	}


	/**
	 * Gets the current slot index.
	 * @return The index of the current slot.
	 */
	public int getCurrentSlot() {
		return currentSlot;
	}

	/**
	 * Uses the item in the current slot.
	 * @param frameCount The current frame of the game.
	 */
	public void useItem(long frameCount) {
		switch (inventory[currentSlot].getSlotItem().getItemType()) {
		case EMPTY:
			break;
		case HEALPOT:
			this.heal(30);
			Debugger.main.print("The player healed 30 HP");
			inventory[currentSlot].getSlotItem().setEmpty();
			Debugger.main.print("The item slot is now empty");
			SFX.main.run(SFX.Sound.ITEMUSED);
			break;
		case SPEEDPOT:
			speedUp = true;
			spedUpFrame = frameCount;
			inventory[currentSlot].getSlotItem().setEmpty();
			SFX.main.run(SFX.Sound.ITEMUSED);
			break;
		case LUCKPOT:
			if(Math.random() > 0.5) {
				this.heal(30);
			}
			else {
				this.takeDamage(30);
			}
			break;
//		case KEY:
//			inventory[currentSlot].getSlotItem().setEmpty();
		default:
			break;
		}
	}


	/**
	 * Gets the first occurrance of an item in the inventory.
	 * @param item The type of item.
	 * @return The index of the first item of the desired type.
	 */
	public int firstOccur(Item.ItemType item){
		for(int i = 0; i < inventory.length; i++){
			if (inventory[i].getSlotItem().getItemType() == item){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Adds an item to the inventory.
	 * @param item The item to be added to the inventory.
	 */
	public void addItem(Item item){
		int temp = firstOccur(Item.ItemType.EMPTY);
		if(temp >= 0){
			inventory[temp].setSlotItem(item);
		}
	}

	/**
	 * Updates the weapon's visibility and direction.
	 * @param frameCount The frame of the current game.
	 */
	public void updateWeapon(long frameCount) {
		if (isAttacking) {
			weapon.setPosition(this);
			weapon.show();
		}
		else {
			weapon.hide();
		}
		
	}

	/**
	 * Initializes the health bar, inventory bar, and timer.
	 */
	private void setUpHealthAndInventoryAndTimer() {
		healthBar = new Heart[10];
		for (int i = 0; i < healthBar.length; i++) {
			Heart h = new Heart();
			h.setPosition(10 + i * h.getWidth(), 10);
			healthBar[i] = h;
		}
		inventory = new InventorySlot[Player.INVENTORY_SIZE];
		for (int i = 0; i < Player.INVENTORY_SIZE; i++) {
			inventory[i] = new InventorySlot(new Item(Item.ItemType.EMPTY)); 
			inventory[i].setPosition(242 + i * inventory[i].getWidth(), 565);
		}
		timer = new TimerDisplay();
	}
	
	/**
	 * Updates the health bar.
	 */
	private void updateHearts() {
		int health = getHeartCount();
		for (int i = 0; i < healthBar.length; i++) {
			if (health >= 2) {
				healthBar[i].setImageAtIndex(2);
				health -= 2;
			}
			else if (health == 1) {
				healthBar[i].setImageAtIndex(1);
				health--;
			}
			else {
				healthBar[i].setImageAtIndex(0);
			}
		}
	}
	
	/**
	 * Updates the inventory bar.
	 */
	public void updateInventoryBar() {
		int slotNum = getCurrentSlot();
		for (int i = 0; i < inventory.length; i++) {
			if (slotNum == i) {
				inventory[i].setImageAtIndex(1);
			}
			else {
				inventory[i].setImageAtIndex(0);
			}
		}
	}
	
	
	/**
	 * Paints the weapon, player, health bar, inventorybar, and timer entities.
	 * @param g The graphics object the entities are painted to.
	 */
	@Override
	public void paint(Graphics2D g) {
		if (weapon.isVisible()) {
			weapon.paint(g);
		}
		super.paint(g);
		for (int i = 0; i < healthBar.length; i++) {
			Heart heart = healthBar[i];
			if (heart.isVisible()) {
				heart.paint(g);
			}
		}
		for (int i = 0; i < inventory.length; i++) {
			InventorySlot slot = inventory[i];
			if (slot.isVisible()) {
				slot.paint(g);
			}
		}
		if (timer.isVisible()) {
			timer.paint(g);
		}
	}

	/**
	 * Cycles the weapon, player, health bar, inventory bar, and timer entities.
	 * @param level The current level.
	 * @param info The game information.
	 */
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

		// Cycle for Hearts 
		updateHearts();
		for (int i = 0; i < healthBar.length; i++) {
			healthBar[i].cycle(level, info);
		}
		
		// Get current item slot
		updateInventoryBar();
		for (int i = 0; i < inventory.length; i++) {
			inventory[i].cycle(level, info);
		}

		// Player Attack & Check for collisions
		playerAttack(level, info.getKeysDown(), info.getFrameCount());
		if (isDead()) info.restartLevel();
		for (int i = 0; i < visibleEntities.size(); i++) {
			Entity entity = visibleEntities.get(i);


			if (entity.isOfType("DoorTile")) {

				double distance = Math.sqrt(Math.pow(entity.getX() - getX(), 2) + Math.pow(entity.getY() - getY(), 2));
				boolean hasKey = false;
				if (inventory[currentSlot].getSlotItem().getItemType() == (Item.ItemType.KEY)){
					hasKey = true;
				}
				if(hasKey && distance < 50){
					DoorTile t = (DoorTile) entity;
					if(!t.isOpen()){
						inventory[currentSlot].getSlotItem().setEmpty();
						t.setOpen(true);
						t.setImageAtIndex(1);
						SFX.main.run(SFX.Sound.DOOROPEN);
					}
				}
			}

			if (collidesWith(entity) ) {
				if (entity.isOfType("LevelUpTile")) {
					for (int j = 0; j < inventory.length; j++) {
						inventory[j].getSlotItem().setEmpty();
					}
					info.nextLevel();
				}
				else if (entity.isOfType("GrassTile")) {
					info.endGame();
				}
			}
		}

		// Move with arrow keys
		moveOnKeys(level, info.getKeysDown(), info.getSize(), visibleEntities);
		swapRoom(level, info.getKeysDown(), info.getSize());

	}

	/**
	 * Determines if the player needs to revert its last movement.
	 * @param visibleEntites An ArrayList of visible entities in the current room.
	 */
	boolean shouldRevertMovement(ArrayList<Entity> visibleEntities) {
		for (int i = 0; i < visibleEntities.size(); i++) {
			Entity entity = visibleEntities.get(i);
			if (collidesWith(entity)) {
				// TODO: Replace with the static variables
				if (entity.isOfType("DoorTile")){
					DoorTile t = (DoorTile) entity;
					if (t.isOpen()) {
						continue;
					}
				}
				if (entity.isOfType(Tile.WALL_TYPES) || entity.isOfType(BreakableTile.TYPE)) {
					return true;
				}
				if (entity.isOfType(SwitchDoor.TYPE) && !((SwitchDoor)entity).isOpen()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Heals the player by a certain value.
	 * @param change The value the player is healed by.
	 */
	@Override
	public void heal(int change) {
		super.heal(change);
		Debugger.main.print(getID() + " healed " + change + ", now at " + getHealth());
	}

	/**
	 * Damages the player by a certain value.
	 * @param info The game information.
	 * @param change The value the player is damaged by.
	 */
	public void takeDamage(Game.GameInfo info, int change) {
		super.takeDamage(change);
		lastFrameAttacked = (int) info.getFrameCount();
		SFX.main.run(SFX.Sound.PLAYERDAMAGED);
		if (isDead()) {
			Debugger.main.print(getID() + " is dead and cannot be damaged further");
		} else {
			Debugger.main.print(getID() + " took " + change + " damage points" + ", now at " + getHealth());
		}
	}

	/**
	 * Moves the player horizontally.
	 * @param level The current level.
	 * @param keysDown A hashmap of current keys pressed.
	 * @param windowSize The dimensions of the game.
	 */
	private void moveOnKeys(Level level, HashSet<Integer> keysDown, Dimension windowSize, ArrayList<Entity> visibleEntities) {
		if (keysDown.contains(KeyEvent.VK_A)) {
			xDelta -= Player.PLAYER_SPEED;
			updateXBy(xDelta);
			if (shouldRevertMovement(visibleEntities)) {
				revertLastHorizontalMovement();
			}
		}
		if (keysDown.contains(KeyEvent.VK_D)) {
			xDelta += Player.PLAYER_SPEED;
			updateXBy(xDelta);
			if (shouldRevertMovement(visibleEntities)) {
				revertLastHorizontalMovement();
			}
		}
		if (keysDown.contains(KeyEvent.VK_W)) {
			yDelta -= Player.PLAYER_SPEED;
			updateYBy(yDelta);
			if (shouldRevertMovement(visibleEntities)) {
				revertLastVerticalMovement();
			}
		}
		if (keysDown.contains(KeyEvent.VK_S)) {
			yDelta += Player.PLAYER_SPEED;
			updateYBy(yDelta);
			if (shouldRevertMovement(visibleEntities)) {
				revertLastVerticalMovement();
			}
		}
	}

	/**
	 * Swaps between rooms if the player leaves a room. 
	 * @param level The current level.
	 * @param keysDown A hashmap of the keys currently pressed.
	 * @param windowSize The dimensions of the game.
	 */
	public void swapRoom(Level level, HashSet<Integer> keysDown, Dimension windowSize) {
		if (getX() + 3 > windowSize.getWidth()) {
			this.setPosition(-getWidth() + 3, getY());
			level.moveRoomRight();
		}
		else if (getX() < -getWidth() + 3) {
			this.setPosition((int) windowSize.getWidth() - 3, getY());
			level.moveRoomLeft();
		}
		if (getY() < -3) {
			this.setPosition(getX(), (int) windowSize.getHeight() - 3);
			level.moveRoomUp();
		}
		else if (getY() + 3 > windowSize.getHeight()) {
			this.setPosition(getX(), -3);
			level.moveRoomDown();
		}
		
	}
	
	/**
	 * Updates the inventory.
	 * @param level The current level.
	 * @param keysDown A hashmap of keys currently pressed.
	 * @param frameCount The frame number of the game.
	 */
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

	/**
	 * Makes the player attack.
	 * @param level The current level.
	 * @param keysDown A hashmap of keys currently pressed.
	 * @param l The frame number of the game.
	 */
	public void playerAttack(Level level, HashSet<Integer> keysDown, long l) {
		if (l - lastFrameAttacking > ATTACK_DURATION) {
			isAttacking = false;
		}
		if (l - lastFrameAttacking > Player.ATTACK_DURATION) {
			if (keysDown.contains(KeyEvent.VK_UP)) {
				if (l - lastFrameAttacking > ATTACK_COOLDOWN) {
					isAttacking = true;
					lastFrameAttacking = l;
					weapon.setDirection(PlayerWeapon.AttackDirection.NORTH);
					SFX.main.run(SFX.Sound.PLAYERATTACK);
				}
			}
			if (keysDown.contains(KeyEvent.VK_RIGHT)) {
				if (l - lastFrameAttacking > ATTACK_COOLDOWN) {
					isAttacking = true;
					lastFrameAttacking = l;
					weapon.setDirection(PlayerWeapon.AttackDirection.EAST);
					SFX.main.run(SFX.Sound.PLAYERATTACK);
				}
			}
			if (keysDown.contains(KeyEvent.VK_DOWN)) {
				if (l - lastFrameAttacking > ATTACK_COOLDOWN) {
					isAttacking = true;
					lastFrameAttacking = l;
					weapon.setDirection(PlayerWeapon.AttackDirection.SOUTH);
					SFX.main.run(SFX.Sound.PLAYERATTACK);
				}
			}
			if (keysDown.contains(KeyEvent.VK_LEFT)) {
				if (l - lastFrameAttacking > ATTACK_COOLDOWN) {
					isAttacking = true;
					lastFrameAttacking = l;
					weapon.setDirection(PlayerWeapon.AttackDirection.WEST);
					SFX.main.run(SFX.Sound.PLAYERATTACK);
				}
			}
		}
	}


	/**
	 * Reverts the last horizontal movement made by the player.
	 */
	private void revertLastHorizontalMovement() {
		xDelta *= -1;
		updateXBy(xDelta);
	}

	/**
	 * Reverts the last vertical movement made by the player.
	 */
	private void revertLastVerticalMovement() {
		yDelta *= -1;
		updateYBy(yDelta);
	}
	
}
