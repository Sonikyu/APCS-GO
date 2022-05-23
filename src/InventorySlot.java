// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Player.java
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

	public InventorySlot(Item item) {
		super(InventorySlot.TYPE, 0, InventorySlot.IMAGE_FILES);
		this.item = item;
	}
	
	public InventorySlot(Coder coder) {
		super(coder);
		this.item = new Item(coder);
	}
	
	@Override
	public void encode(Coder coder) {
		super.encode(coder);
		item.encode(coder);
	}
	
	public void setSlotItem(Item item) {
		this.item = item;
	}
	
	public String getSlotItemName() {
		return item.getType();
	}
	
//	@Override
//	public void setPosition(int x, int y) {
//		super.setPosition(x, y);
//		item.setPosition(this.getX() + (this.getWidth() - item.getWidth())/2, this.getY() + (this.getHeight() - item.getHeight())/2);
//	}

	@Override
	public void cycle(Level level, Game.GameInfo info) {
		
	}
	@Override
	public void paint(Graphics2D g) {
		super.paint(g);
		item.setPosition(this.getX() + (this.getWidth() - item.getWidth())/2, this.getY() + (this.getHeight() - item.getHeight())/2);
		item.paint(g);
	}
}
