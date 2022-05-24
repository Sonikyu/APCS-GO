import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import restore.Coder;
import restore.Encodable;
import java.awt.Graphics2D;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Entity.java
//
// Add your name here if you work on this class:
/** @author Ethan */ 
public abstract class Entity implements ImageObserver, Encodable {
	private final String id;
	private String type;
	private int health;
	private final int maxHealth;
	private int x, y;
	private String[] imageNames;
	private Image[] images;
	private int currentImage;
	private Dimension size;
	private boolean visible;
	
	private static String[] singleArray(String str) {
		String[] strs = {str};
		return strs;
	}
	
	/**
	 * Creates a new entity
	 * @param type The class of entity
	 * @param maxHealth Maximum health allowed to have
	 * @param imageName Image name of costume
	 */
	public Entity(String type, int maxHealth, String imageName) {
		this(type, maxHealth, singleArray(imageName));
	}

	/**
	 * Creates a new entity
	 * @param type The class of entity
	 * @param maxHealth Maximum health allowed to have
	 * @param imageNames Image names of costumes
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
	 * Loads an entity from the given coder
	 * @param coder
	 */
	public Entity(Coder coder) {
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
	 * Gets the entity's id
	 * @return
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Gets the entity's type
	 * @return
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the entity's health
	 * @return
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Checks whether entity is alive
	 * @return Whether the entity is dead
	 */
	public boolean isDead() {
		return getHealth() == 0;
	}
	
	/**
	 * Gets the maximum health of entity
	 * @return
	 */
	public int getMaxHealth() {
		return maxHealth;
	}
	
	/**
	 * Gets the entity's x position
	 * @return
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the entity's y position
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Gets the entity's visibility
	 * @return Whether the entity is currently visible on screen
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Makes the entity visible
	 */
	public void show() {
		visible = true;
	}
	
	/**
	 * Makes the entity invisible
	 */
	public void hide() {
		visible = false;
	}
	
	/**
	 * Checks if the entity is in the specified bounds
	 * @param containerSize The container in which the entity should be checked
	 * @return Whether the entity is on screen
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
	 * Gets the entity's width
	 * @return
	 */
	public int getWidth() {
		return (int)size.getWidth();
	}
	
	/**
	 * Gets the entity's height
	 * @return
	 */
	public int getHeight() {
		return (int)size.getHeight();
	}
	
	public void paint(Graphics2D g) {
        g.drawImage(images[currentImage], x, y, this);
	}
	
	/**
	 * Sets the entity's first costume
	 */
	public void setFirstImage() {
		currentImage = 0;
	}
	
	/**
	 * Sets a specific costume of the entity
	 * @param index The index of the costume to set
	 */
	public void setImageAtIndex(int index) {
		currentImage = index;
	}
	
	/**
	 * Moves to the next costume, wrapping to the front if necessary
	 */
	public void setNextImage() {
		currentImage++;
		if (currentImage >= images.length) {
			currentImage = 0;
		}
	}
	
	/**
	 * Moves to the previous costume, wrapping to the back if necessary
	 */
	public void setPreviousImage() {
		currentImage--;
		if (currentImage < 0) {
			currentImage = images.length - 1;
		}
	}
	
	/**
	 * Sets the entity's new position
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Changes the entity's x position
	 * @param delta How much to change the x position by
	 */
	public void updateXBy(int delta) {
		x += delta;
	}
	
	/**
	 * Changes the entity's y position
	 * @param delta How much to change the y position by
	 */
	public void updateYBy(int delta) {
		y += delta;
	}
	
	/**
	 * Heals the entity
	 * @param change How much to heal the entity by
	 */
	public void heal(int change) {
		if (health + change > maxHealth) {
			health = maxHealth;
		} else {
			health += change;
		}
	}
	
	/**
	 * Hurts the entity
	 * @param change How much to hurt the entity by
	 */
	public void takeDamage(int change) {
		if (health - change < 0) {
			health = 0;
		} else {
			health -= change;
		}
	}
		
			
	/**
	 * Whether the entity collides with another
	 * @param otherEntity The entity to check collision with
	 * @return
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
	
	public abstract void cycle(Level level, Game.GameInfo info);

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
//		size = new Dimension(width, height);
//		sizeIsLoaded = true;
		return false;
	}
	
	@Override
	public String toString() {
		return id + "[health=" + health + ", at=(" + x + ", " + y + "), " + (visible ? "visible" : "not visible") + "]"; 
	}
}