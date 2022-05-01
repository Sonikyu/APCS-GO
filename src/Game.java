import java.awt.Graphics2D;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import restore.Coder;
import restore.Encodable;

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
public class Game implements Encodable {
	public static int VER_BREAK = 0;
	public static int VER_BACK = 0;
	
	private int verBack = VER_BACK;
	private ArrayList<Entity> entities;
	private HashSet<Integer> keysDown;
	private Dimension size;
	private long frameCount;
	
	public Game(Dimension size) {
		this.entities = new ArrayList<Entity>();
		this.keysDown = new HashSet<Integer>();
		this.size = size;
		this.frameCount = 0;
		
		initialDebug();
	}
	
	public Game(Coder coder) {
		this.entities = new ArrayList<Entity>();
		this.keysDown = new HashSet<Integer>();
		
		int verBreak = coder.decodeInt();
		int verBack = coder.decodeInt();
		if (Game.VER_BREAK != verBreak) {
			coder.setError("Incompatible game versions");
			return;
		}
		
		int width = coder.decodeInt();
		int height = coder.decodeInt();
		
		this.size = new Dimension(width, height);
		this.verBack = verBack;
		
		this.frameCount = coder.decodeLong();
		
		int entityCount = coder.decodeInt();
		System.out.println("Entity Count:" + entityCount);
		for (int i = 0; i < entityCount; i++) {
			Entity entity = EntityDecoder.decode(coder);
			if (entity != null) {
				this.addEntity(entity);
			}
		}
	}
	
	public void encode(Coder coder) {
		coder.encode(Game.VER_BREAK);
		coder.encode(Game.VER_BACK);
		
		coder.encode((int)getSize().getWidth());
		coder.encode((int)getSize().getHeight());
		
		coder.encode((long)getFrameCount());
		
		coder.encode(entities.size());
		for (int i = 0; i < entities.size(); i++) {
			coder.encode(entities.get(i));
		}
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
		System.out.println("entities[" + entities.size() + "] = " + entity);
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
		
		System.out.println("Game v" + VER_BREAK + "." + verBack + "\n==========");
		System.out.println("Window Size: " + size.getWidth() + "px by " + size.getHeight() + "px");
	}
}
