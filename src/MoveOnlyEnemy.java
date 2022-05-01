// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Player.java
//
// Add your name here if you work on this class:
/** @author Uday, Johnny */ 

import restore.Coder;

public class MoveOnlyEnemy extends Entity {
	public static String TYPE = "MoveOnlyEnemy";
	private static int MAX_HEALTH = 100;
	private static String IMAGE_FILE = "Enemy.png";
	
	private int xDelta = 1;
	private int yDelta = 1;
	private int offsetX;
	private int offsetY;
	private int totalXDelta;
	private int totalYDelta;
	
	public MoveOnlyEnemy(int totalXDelta, int totalYDelta) {
		super(MoveOnlyEnemy.TYPE, MoveOnlyEnemy.MAX_HEALTH, MoveOnlyEnemy.IMAGE_FILE);
		this.totalXDelta = totalXDelta;
		this.totalYDelta = totalYDelta;
	}
	
	public MoveOnlyEnemy(Coder coder) {
		super(coder);
		this.offsetX = coder.decodeInt();
		this.offsetY = coder.decodeInt();
		this.totalXDelta = coder.decodeInt();
		this.totalYDelta = coder.decodeInt();
	}
	
	public void encode(Coder coder) {
		super.encode(coder);
		coder.encode(this.offsetX);
		coder.encode(this.offsetY);
		coder.encode(this.totalXDelta);
		coder.encode(this.totalYDelta);
	}
	
	@Override
	public void cycle(Game game) {
		if (game.getFrameCount() % 3 == 0) {
			offsetX += xDelta;
			offsetY += yDelta;
			updateYBy(xDelta);
			updateYBy(yDelta);
			if (offsetY >= totalYDelta || offsetY <= 0) {
				yDelta *= -1;
			}
			if (offsetX >= totalXDelta || offsetX <= 0) {
				xDelta *= -1;
			}
		}
	}
}
