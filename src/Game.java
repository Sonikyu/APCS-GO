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
	public static final int VER_MAJ = 0;
	public static final int VER_BREAK = 1;
	
	
	private int verBreak = VER_BREAK;
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
		
		Integer verMaj = coder.decodeInt(); if (coder.hasError()) { coder.setErrorMsg("Unknown major version"); return; }
		Integer verBreak = coder.decodeInt(); if (coder.hasError()) { coder.setErrorMsg("Unknown minor version"); return; }
		if (Game.VER_MAJ != verMaj || Game.VER_BREAK != verBreak) {
			coder.setErrorMsg("Incompatible game versions");
			return;
		}
		
		Integer width = coder.decodeInt(); if (coder.hasError()) { coder.setErrorMsg("Failed to decode width"); return; }
		Integer height = coder.decodeInt(); if (coder.hasError()) { coder.setErrorMsg("Failed to decode height"); return; }
		
		this.size = new Dimension(width, height);
		this.verBreak = verBreak;
		
		Long frameCount = coder.decodeLong(); if (coder.hasError()) { coder.setErrorMsg("Failed to decode frame count"); return; }
		this.frameCount = frameCount;
		
		Integer entityCount = coder.decodeInt(); if (coder.hasError()) { coder.setErrorMsg("Failed to decode number of entities"); return; }
		System.out.println("Entity Count:" + entityCount);
		for (int i = 0; i < entityCount; i++) {
			Entity entity = EntityDecoder.decode(coder);
			if (entity == null) {
				if (!coder.hasError()) {
					coder.setErrorMsg("Unknown entity");
				}
				return;
			}
			this.addEntity(entity);
		}
	}
	
	public void encode(Coder coder) {
		coder.encode(Game.VER_MAJ);
		coder.encode(Game.VER_BREAK);
		
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
	
	/**
	 * 
	 * @param id
	 * @return The entity with the given id or null if none exists.
	 */
	public Entity getEntityByID(String id) {
		for (Entity e: entities) {
			if (e.getID().equals(id)) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * For example, to get the player, use: game.getEntitiesByType(Player.TYPE).get(0)
	 * @param type
	 * @return A list of visible entities of the given type.
	 */
	public ArrayList<Entity> getEntitiesByType(String type) {
		ArrayList<Entity> entitiesByType = new ArrayList<Entity>();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.isVisible() && entity.getType().equals(type)) {
				entitiesByType.add(entity);
			}
		}
		return entitiesByType;
	}
	
	public void addEntity(Entity entity) {
		System.out.println("entities[" + entities.size() + "] = " + entity);
		entities.add(entity);
	}
	
	public void removeEntityByID(String id) {
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
		
		System.out.println("Game v" + VER_MAJ + "." + verBreak + "\n==========");
		System.out.println("Window Size: " + size.getWidth() + "px by " + size.getHeight() + "px");
	}
}
