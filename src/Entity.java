import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
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
/** @authors Ethan */ 
public abstract class Entity implements ImageObserver {
	private final String id;
	private String type;
	private int health;
	private final int maxHealth;
	private int x, y;
	private Image[] images;
	private int currentImage;
	private Dimension size;
	private boolean visible;
	
	/*
	 * @deprecated Use other constructor instead.
	 */
	@Deprecated
	public Entity(String id, String type, int maxHealth, String imageName) {
		this.id = id;
		this.type = type;
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.x = 0;
		this.y = 0;
		this.images = new Image[1];
		this.images[0] = new ImageIcon("images/" + imageName).getImage();
		this.currentImage = 0;
		
		int width = this.images[0].getWidth(this);
		int height = this.images[0].getHeight(this);
		this.size = new Dimension(width, height);
		
		this.visible = true;
	}
	
	/*
	 * Precondition: imageNames.length > 0
	 */
	public Entity(String id, String type, int maxHealth, String[] imageNames) {		
		this.id = id;
		this.type = type;
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.x = 0;
		this.y = 0;
		
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
	
	public String getID() {
		return id;
	}
	
	public String getType() {
		return type;
	}
	
	public int getHealth() {
		return health;
	}
	
	public boolean isDead() {
		return getHealth() == 0;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void show() {
		visible = true;
	}
	
	public void hide() {
		visible = false;
	}
	
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
	
	public int getWidth() {
		return (int)size.getWidth();
	}
	
	public int getHeight() {
		return (int)size.getHeight();
	}
	
	public void paint(Graphics2D g) {
        g.drawImage(images[currentImage], x, y, this);
	}
	
	public void setFirstImage() {
		currentImage = 0;
	}
	
	public void setImageAtIndex(int index) {
		currentImage = index;
	}
	
	public void setNextImage() {
		currentImage++;
		if (currentImage >= images.length) {
			currentImage = 0;
		}
	}
	
	public void setPreviousImage() {
		currentImage--;
		if (currentImage < 0) {
			currentImage = images.length - 1;
		}
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void updateXBy(int delta) {
		x += delta;
	}
	
	public void updateYBy(int delta) {
		y += delta;
	}
	
	public void heal(int change) {
		if (health + change > maxHealth) {
			health = maxHealth;
		} else {
			health += change;
		}
	}
	
	public void takeDamage(int change) {
		if (health - change < 0) {
			health = 0;
		} else {
			health -= change;
		}
	}
	
	public void placeOnTile(/* Room room, int row, int col */) {
		// this.x = room.calculateX(row, col);
		// this.y = room.calculateY(row, col);
	}
			
			
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
	
	public abstract void cycle(Game game);

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
//		size = new Dimension(width, height);
//		sizeIsLoaded = true;
		return false;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + "[id=" + id + ", type=" + type + ", health=" + health + "/" + maxHealth + ", position=(" + x + ", " + y + "), visible=" + visible + "]"; 
	}
}