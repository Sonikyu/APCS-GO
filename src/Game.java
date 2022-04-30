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
/** @author Ethan */ 
public class Game {
	private ArrayList<Entity> entities;
	private HashSet<Integer> keysDown;
	private Dimension size;
	private long frameCount;
	
	public Game(Dimension size) {
		this.entities = new ArrayList();
		this.keysDown = new HashSet<Integer>();
		this.size = size;
		this.frameCount = 0;
		
		initialDebug();
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
	
	public long getFrameCount() {
		return frameCount;
	}
	
	public void addEntity(Entity entity) {
		Debugger.main.print("entities[" + entities.size() + "] = " + entity);
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
		frameCount++;
		//debugger.print("Game Loop");
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
	
	private void initialDebug() {
		Debugger.main.start();
		
		Debugger.main.print("Game Debug\n==========");
		
		Debugger.main.print("Window Size: " + size.getWidth() + "px by " + size.getHeight() + "px");
	}
}

