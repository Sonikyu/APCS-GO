import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import restore.Coder;
import restore.Encodable;
import restore.CoderException;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Entity.java
//
// Add your name here if you work on this class:
/** @author Ethan Johnny */ 
/**
 * A class that represents a drawable object with behavior.
 */
public abstract class Entity implements ImageObserver, Encodable {
	/**
	 * A unique ID associated with the entity.
	 */
	private final String id;
	/**
	 * The type of entity it is.
	 */
	private String type;
	/**
	 * The health of the entity.
	 */
	private int health;
	/**
	 * The maximum health possible that the entity can have.
	 */
	private final int maxHealth;
	/**
	 * The position of the entity.
	 */
	private int x, y;
	/**
	 * The names of the entity costumes.
	 */
	private String[] imageNames;
	/**
	 * The entity costumes.
	 */
	private Image[] images;
	private int currentImage;
	private Dimension size;
	private boolean visible;
	
	/**
	 * A hack to make the constructor work.
	 * @param str
	 * @return
	 */
	private static String[] singleArray(String str) {
		String[] strs = {str};
		return strs;
	}
	
	/**
	 * Creates a new entity
	 * @param type The class of entity.
	 * @param maxHealth Maximum health allowed to have.
	 * @param imageName Image name of costume.
	 */
	public Entity(String type, int maxHealth, String imageName) {
		this(type, maxHealth, singleArray(imageName));
	}

	/**
	 * Creates a new entity
	 * @param type The class of entity.
	 * @param maxHealth Maximum health allowed to have.
	 * @param imageNames Image names of costumes.
	 */
	public Entity(String type, int maxHealth, String[] imageNames) {
		this.id = IDGen.make(type);
		this.type = type;
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.x = 0;
		this.y = 0;
		
		this.imageNames = imageNames;
		this.images = new Image[imageNames.length];
		this.currentImage = 0;
		for (int i = 0; i < imageNames.length; i++) {
			this.images[i] = new ImageIcon("images/" + imageNames[i]).getImage();
		}
		
		int width = this.images[0].getWidth(this);
		int height = this.images[0].getHeight(this);
		this.size = new Dimension(width, height);
		
		this.visible = true;
	}
	
	/**
	 * Loads an entity from the given coder.
	 * @param coder The coder object the entity is created from.
	 */
	public Entity(Coder coder) throws CoderException {
		String type = coder.decodeString();
		
		int health = coder.decodeInt();		
		int maxHealth = coder.decodeInt();
		int x = coder.decodeInt();
		int y = coder.decodeInt();
		
		int imageCount = coder.decodeInt();
		String[] imageNames = new String[imageCount];
		coder.encode(imageNames.length);
		for (int i = 0; i < imageCount; i++) {
			imageNames[i] = coder.decodeString();
		}
		
		int currentImage = coder.decodeInt();
		boolean isVisible = coder.decodeBoolean();

		this.id = IDGen.make(type);
		this.type = type;
		this.health = health;
		this.maxHealth = maxHealth;
		this.x = x;
		this.y = y;
		
		this.imageNames = imageNames;
		this.images = new Image[imageNames.length];
		this.currentImage = currentImage;
		for (int i = 0; i < imageNames.length; i++) {
			this.images[i] = new ImageIcon("images/" + imageNames[i]).getImage();
		}

		int width = this.images[0].getWidth(this);
		int height = this.images[0].getHeight(this);
		this.size = new Dimension(width, height);
		
		this.visible = isVisible;
	}
	/**
	 * Turns the entity into a game string in the coder.
	 * @param coder The coder object the game is made from.
	 */
	public void encode(Coder coder) {		
		coder.encode(getType());
		coder.encode(getHealth());
		coder.encode(getMaxHealth());
		coder.encode(getX());
		coder.encode(getY());
		
		coder.encode(imageNames.length);
		for (String imageName: imageNames) {
			coder.encode(imageName);
		}
		
		coder.encode(currentImage);
		coder.encode(visible);
	}
	
	/**
	 * Gets the entity's id.
	 * @return The entity's id.
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Gets the entity's type.
	 * @return The entity's type.
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * 
	 * @param type An entity type.
	 * @return Whether the entity if of the type.
	 */
	public boolean isOfType(String type) {
		return getType().equals(type);
	}
	
	/**
	 * 
	 * @param types A list of entity types.
	 * @return Whether the entity is of one of the types in types.
	 */
	public boolean isOfType(String[] types) {
		for (String type: types) {
			 if (getType().equals(type)) {
				 return true;
			 }
		}
		return false;
	}
	
	/**
	 * Gets the entity's health.
	 * @return The entity's health.
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Checks whether entity is alive.
	 * @return Whether the entity is dead.
	 */
	public boolean isDead() {
		return getHealth() == 0;
	}
	
	/**
	 * Called when the entity dies. Subclasses can override.
	 */
	public void whenDead() {
		
	}
	
	/**
	 * Gets the maximum health of entity.
	 * @return The maximum health of entity.
	 */
	public int getMaxHealth() {
		return maxHealth;
	}
	
	/**
	 * Gets the entity's x position.
	 * @return The entity's x position.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the entity's y position.
	 * @return The entity's y position.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Gets the entity's visibility.
	 * @return Whether the entity is currently visible on screen.
	 */
	public boolean isVisible() {
		return visible;
	}
	/**
	 * Determines if the entity should be visible or not.
	 * @return Whether the entity should be visible or invisible.
	 */
	public boolean shouldShow() {
		return true;
	}
	
	/**
	 * Makes the entity visible.
	 */
	public void show() {
		if (shouldShow()) {
			visible = true;
		}
	}
	
	/**
	 * Makes the entity invisible.
	 */
	public void hide() {
		visible = false;
	}
	
	/**
	 * Checks if the entity is in the specified bounds.
	 * @param containerSize The container in which the entity should be checked.
	 * @return Whether the entity is on screen.
	 */
	public boolean isOnScreen(Dimension containerSize) {
		return (x + size.width > 0 && y + size.height > 0)							// top left
				&& (x < containerSize.getWidth() && y < containerSize.getHeight());	// bottom right
	}
	
	/*
	 * @deprecated Use getWidth() and getHeight() instead.  
	 */
	@Deprecated
	public Dimension getSize() {
		return size;
	}
	
	/**
	 * Gets the entity's width.
	 * @return The entity's width.
	 */
	public int getWidth() {
		return (int)size.getWidth();
	}
	
	/**
	 * Gets the entity's height.
	 * @return The entity's height.
	 */
	public int getHeight() {
		return (int)size.getHeight();
	}
	
	/**
	 * Paints the entity.
	 * @param g The graphics object to paint to.
	 */
	public void paint(Graphics2D g) {
        g.drawImage(images[currentImage], x, y, this);
	}
	
	/**
	 * Sets the entity's first costume.
	 */
	public void setFirstImage() {
		currentImage = 0;
	}
	
	/**
	 * Sets a specific costume of the entity.
	 * @param index The index of the costume to set.
	 */
	public void setImageAtIndex(int index) {
		currentImage = index;
	}
	
	/**
	 * Moves to the next costume, wrapping to the front if necessary.
	 */
	public void setNextImage() {
		currentImage++;
		if (currentImage >= images.length) {
			currentImage = 0;
		}
	}
	
	/**
	 * Moves to the previous costume, wrapping to the back if necessary.
	 */
	public void setPreviousImage() {
		currentImage--;
		if (currentImage < 0) {
			currentImage = images.length - 1;
		}
	}
	
	/**
	 * Sets the entity's new position.
	 * @param x The x position the entity is set to.
	 * @param y The y position the entity is set to.
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Changes the entity's x position.
	 * @param delta How much to change the x position by.
	 */
	public void updateXBy(int delta) {
		x += delta;
	}
	
	/**
	 * Changes the entity's y position.
	 * @param delta How much to change the y position by.
	 */
	public void updateYBy(int delta) {
		y += delta;
	}
	
	/**
	 * Heals the entity.
	 * @param change How much to heal the entity by.
	 */
	public void heal(int change) {
		if (health + change > maxHealth) {
			health = maxHealth;
		} else {
			health += change;
		}
	}
	/**
	 * Sets the health of the entity.
	 * @param health The health the entity is set to.
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	
	/**
	 * Hurts the entity.
	 * @param change How much to hurt the entity by.
	 */
	public void takeDamage(int change) {
		if (health - change < 0) {
			health = 0;
			whenDead();
		} else {
			health -= change;
		}
	}
		
			
	/**
	 * Whether the entity collides with another. This uses rectangle-rectangle collisions, assuming that all entities are rectangles.
	 * @param otherEntity The entity to check collision with.
	 * @return Whether the entity is colliding with the other entity.
	 */
	// https://jeffreythompson.org/collision-detection/rect-rect.php
	public boolean collidesWith(Entity otherEntity) {
		if (id.equals(otherEntity.id)) {
			return false;
		}
		return (x < otherEntity.x + otherEntity.getWidth())			// Is the RIGHT edge of r1 to the RIGHT of the LEFT edge of r2?
				&& (x + getWidth() > otherEntity.x)					// Is the LEFT edge of r1 to the LEFT of the RIGHT edge of r2?
				&& (y < otherEntity.y + otherEntity.getHeight())	// Is the BOTTOM edge of r1 BELOW the TOP edge of r2?
				&& (y + getHeight() > otherEntity.y);				// Is the TOP edge of r1 ABOVE the BOTTOM edge of r2?
	}
	
	/**
	 * Cycles the entity.
	 * @param level The current level.
	 * @param info The game information.
	 */
	public abstract void cycle(Level level, Game.GameInfo info);

	/**
	 * Checks if the image needs to be updated.
	 * @param img The image that is painted.
	 * @param infoflags The index of the flag of the image.
	 * @param x The x position of the image.
	 * @param y The y position of the iamge.
	 * @param width The width of the image.
	 * @param height The height of the image.
	 */
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
//		size = new Dimension(width, height);
//		sizeIsLoaded = true;
		return false;
	}
	
	/**
	 * Creates a string for the entity.
	 * @return The entity as a string.
	 */
	@Override
	public String toString() {
		return id + "[health=" + health + ", at=(" + x + ", " + y + "), " + (visible ? "visible" : "not visible") + "]"; 
	}
}