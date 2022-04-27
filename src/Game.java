import java.awt.Graphics2D;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.HashSet;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Game.java
//
// Add your name here if you work on this class:
/** @authors Ethan */ 
public class Game {
	ArrayList<Entity> entities;
	HashSet<Integer> keysDown;
	Dimension size;
	
	public Game(Dimension size) {
		this.entities = new ArrayList();
		this.keysDown = new HashSet<Integer>();
		this.size = size;
	}
	
	public Dimension getSize() {
		return size;
	}
	
	public ArrayList<Entity> getVisibleEntities() {
		ArrayList<Entity> visibleEntities = new ArrayList<Entity>();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.isVisible()) {
				visibleEntities.add(entity);
			}
		}
		return visibleEntities;
	}
	
	public HashSet<Integer> getKeysDown() {
		return keysDown;
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void removeEntity(String id) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getID().equals(id)) {
				entities.remove(i);
				break;
			}
		}
	}
	
	public void paint(Graphics2D g) {	
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.isVisible()) {
				entity.paint(g);
			}
		}
	}
	
	public void cycle() {
		// Testing code
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.isVisible()) {
				entity.cycle(this);
			}
		}
	}
	
	// https://stackoverflow.com/questions/37490551/disabling-key-repeat-in-swing
	
	public void keyPressed(KeyEvent key) {
		int keycode = key.getKeyCode();
		if (0 <= keycode && keycode <= 0xFFFF) {
	        if (keysDown.contains(keycode)) return;
	        keysDown.add(keycode);
	    }

	}
	
	public void keyReleased(KeyEvent key) {
		int keycode = key.getKeyCode();
	    if (0 <= keycode && keycode <= 0xFFFF) {
	        if (!keysDown.contains(keycode)) return;
	        keysDown.remove(keycode);
	    }
	}
}
