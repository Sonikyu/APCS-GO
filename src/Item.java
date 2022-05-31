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
import restore.CoderException;

public class Item extends Entity {

	public enum ItemType {
		EMPTY, KEY, HEALPOT, SPEEDPOT
    }
	
	public static String TYPE = "Item";

	private static String[] IMAGE_FILES = {"Empty.png", "Key.png", "HealPot.png", "SpeedPot.png"};
	
	private ItemType object;
	
	/**
     * Initializes an item object.
     * @param object The object that the item is initialzied as. 
     */
	public Item(ItemType object) {
		super(TYPE, 0, IMAGE_FILES);	
		this.object = object;
		this.setImageAtIndex(object.ordinal());
	}
	
	/**
     * Initializes an item object.
     * @param coder The object that creates the entity from a game string.
     */

	public Item(Coder coder) throws CoderException {
		super(coder);
		//this.object = (Object)coder.decodeInt();
	}

	/**
     * Turns the item into a game string.
     * @oaran coder The object that creates the entity from a game string.
     */

	public void encode(Coder coder) {
    	super.encode(coder);
    }
	
	/**
     * Sets the item to an empty item.
     */
	public void setEmpty() {
		this.object = ItemType.EMPTY;
		this.setImageAtIndex(ItemType.EMPTY.ordinal());
	}
	
	public void setItemType(Item.ItemType itemType) {
		this.object = itemType;
	}
	
	/**
     * Gets the item type.
     * @return The item type.
     */

	public ItemType getItemType() {
		return object;
	}

	/**
     * Cycles the item.
     * @param level The current level.
     * @param info The game information.
     */
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		setImageAtIndex(object.ordinal());
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
							SFX.main.run(4);
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
