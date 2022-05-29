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

	public enum ItemType {
		EMPTY, KEY, HEALPOT, SPEEDPOT
    }

	private static String[] IMAGE_FILES = {"Empty.png", "Key.png", "HealPot.png", "SpeedPot.png"};
	
	private ItemType object;
	
	public Item(ItemType object) {
		super("Item", 0, IMAGE_FILES);	
		this.object = object;
		this.setImageAtIndex(object.ordinal());
	}
	
	public Item(Coder coder) {
		super(coder);
		//this.object = (Object)coder.decodeInt();
	}

	public void encode(Coder coder) {
    	super.encode(coder);
    }
	
	public void setEmpty() {
		this.object = ItemType.EMPTY;
		this.setImageAtIndex(ItemType.EMPTY.ordinal());
	}
	
	public ItemType getItemType() {
		return object;
	}
		
//TODO make constructor take in all types, make image files array of image files

	@Override
	public void cycle(Level level, Game.GameInfo info) {
		
		if (this.object != ItemType.EMPTY){
			ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
				for (int i = 0; i < visibleEntities.size(); i++) {
					Entity entity = visibleEntities.get(i);
					if (collidesWith(entity)) {
						if (entity.isOfType(Player.TYPE)) {
							Player p = (Player) entity;						
							int temp = p.firstOccur(ItemType.EMPTY);							
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
