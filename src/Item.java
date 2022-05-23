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

	public enum Object {
        KEY, EMPTY
    }

	public static String[] TYPES = {"Key","NoItem", "HealPot" };
	private static String[][] IMAGE_FILES = {{"Key.png", "Empty.png"}, {"Empty.png"}, {"HealPot.png", "Empty.png"}};
	
	public Item(Object Item) {
		super(TYPES[Item.ordinal()], 0, IMAGE_FILES[Item.ordinal()]);	
        show();
	}
	
	public Item(Coder coder) {
		super(coder);
        show();
    
	}

	public void encode(Coder coder) {
    	super.encode(coder);
    }
	
//TODO make constructor take in all types, make image files array of image files

	@Override
	public void cycle(Level level, Game.GameInfo info) {
		
		if(! this.getType().equals("NoItem")){
			ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
				for (int i = 0; i < visibleEntities.size(); i++) {
					Entity entity = visibleEntities.get(i);
					if (collidesWith(entity)) {
						if (entity.getType().equals("Player")) {
							Player p = (Player) entity;
							int temp = p.firstOccur("NoItem");							
							if(temp >= 0){
								p.addItem(this); 
								Debugger.main.print("Player obtained " + this);
								hide();
							}
							else{
								Debugger.main.print("Full inventory, didn't pick up " + this);
							}
						}

					}
			}
		}
	}
	
}
