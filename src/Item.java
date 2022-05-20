// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Item.java
//
// Add your name here if you work on this class:
/** @author Alex */

import java.util.ArrayList;
import java.util.Arrays;

import restore.Coder;

public class Item extends Entity {

	public enum ItemType {
        Empty, Key
    }
	
	private static String[] itemTypes = {"Empty", "Key"};
	private static String[] IMAGE_FILES = {"Empty.png", "Key.png"};

	
	public Item(ItemType type) {
		super(itemTypes[type.ordinal()], 0, IMAGE_FILES[type.ordinal()]);	
        show();
	}
	
	public Item(Coder coder) {
		super(coder);
        show();
	}

	public void encode(Coder coder) {
    	super.encode(coder);
    }
	
	
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
		for (int i = 0; i < visibleEntities.size(); i++) {
			Entity entity = visibleEntities.get(i);
			if (collidesWith(entity)) {
				//Debugger.main.print(this + " collided with " + entity);
				
				// TODO: Replace with the static variables
				if (entity.getType().equals("Player")) {
                    hide();
                    //inventory call to add object
				}
			}
		}
	}
	
}
