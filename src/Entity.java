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
	private Image image;
	private Dimension size;
	private boolean visible;
	
	public Entity(String id, String type, int maxHealth, String imageName) {
		this.id = id;
		this.type = type;
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.x = 0;
		this.y = 0;
		this.image = new ImageIcon("images/" + imageName).getImage();
		
		int width = this.image.getWidth(this);
		int height = this.image.getHeight(this);
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
	
	public Dimension getSize() {
		return size;
	}
	
	public void paint(Graphics2D g) {
        g.drawImage(image, x, y, this);
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
		// Tile tile = room.getTile(row, col);
		// this.x = tile.calculateX();
		// this.y = tile.calculateY();
	}
	
	public boolean collidesWith(Entity otherEntity) {
		return false;
	}
	
	/*
	 * 1. move as you would if there is no collisions
	 * 2. check if colliding with something (i.e. moved into wall)
	 * 3. move back
	 * 4. draw
	 * */
	
	public abstract void cycle(Game game);

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
//		size = new Dimension(width, height);
//		sizeIsLoaded = true;
		return false;
	}
}