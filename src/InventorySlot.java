// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: InventorySlot.java
//
// Add your name here if you work on this class:
/** @author Johnny*/ 

import java.awt.Graphics2D;
import java.util.ArrayList;

import restore.Coder;
import restore.Encodable;

public class InventorySlot extends Entity implements Encodable {
	private static String TYPE = "InventorySlot";
	private static String[] IMAGE_FILES = {"EmptySlot.png", "SelectedSlot.png"};
	
	private Item item;

	/**
	 * Initializes an inventory slot entity.
	 * @param item The item the inventory slot will hold.
	 */
	public InventorySlot(Item item) {
		super(InventorySlot.TYPE, 0, InventorySlot.IMAGE_FILES);
		this.item = item;
	}
	
	/**
	 * Initializes an inventory slot entity.
	 * @param coder The object that creates the entity from a game string.
	 */
	public InventorySlot(Coder coder) {
		super(coder);
		this.item = new Item(coder);
	}
	
	/**
	 * Turns the inventory slot into a game string.
	 * @param coder The object htat creates the entity from a game string.
	 */
	@Override
	public void encode(Coder coder) {
		super.encode(coder);
		item.encode(coder);
	}
	
	/**
	 * Sets the item that the inventory slot will hold.
	 * @param item The item that the inventory slot will hold.
	 */
	public void setSlotItem(Item item) {
		this.item = item;
	}
	/**
	 * Get the type of the item that is in the inventory slot.
	 * @return The type of the item.
	 */
	public String getSlotItemName() {
		return item.getType();
	}
	
//	@Override
//	public void setPosition(int x, int y) {
//		super.setPosition(x, y);
//		item.setPosition(this.getX() + (this.getWidth() - item.getWidth())/2, this.getY() + (this.getHeight() - item.getHeight())/2);
//	}
	
	/**
	 * Cycles the inventory slot.
	 * @param level The current level.
	 * @param info The game information.
	 */
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		
	}
	
	/**
	 * Paints the inventory slot.
	 * @param g The graphics object the inventory slot is painted to.
	 */
	@Override
	public void paint(Graphics2D g) {
		super.paint(g);
		item.setPosition(this.getX() + (this.getWidth() - item.getWidth())/2, this.getY() + (this.getHeight() - item.getHeight())/2);
		item.paint(g);
	}
}
