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
import restore.Coder;

public class Item extends Entity {

	private static String TYPE = "Heart";
	private static String[] IMAGE_FILES = {"Key.png", "Empty.png"};
	
	public Item() {
		super(Item.TYPE, 0, Item.IMAGE_FILES);	
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
