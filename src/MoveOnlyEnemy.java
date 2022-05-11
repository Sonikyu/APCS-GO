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

import java.util.ArrayList;

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
	private int attackStrength;
	
	public MoveOnlyEnemy(int totalXDelta, int totalYDelta, int attackStrength) {
		super(MoveOnlyEnemy.TYPE, MoveOnlyEnemy.MAX_HEALTH, MoveOnlyEnemy.IMAGE_FILE);
		this.totalXDelta = totalXDelta;
		this.totalYDelta = totalYDelta;
		this.attackStrength = attackStrength;
	}
	
	public MoveOnlyEnemy(Coder coder) {
		super(coder);
		this.offsetX = coder.decodeInt();
		this.offsetY = coder.decodeInt();
		this.totalXDelta = coder.decodeInt();
		this.totalYDelta = coder.decodeInt();
		this.attackStrength = coder.decodeInt();
	}
	
	public void encode(Coder coder) {
		super.encode(coder);
		coder.encode(this.offsetX);
		coder.encode(this.offsetY);
		coder.encode(this.totalXDelta);
		coder.encode(this.totalYDelta);
		coder.encode(this.attackStrength);
	}
	
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		if (info.getFrameCount() % 3 == 0) {
			boolean collidedWithWall = false;
			ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
			for (int i = 0; i < visibleEntities.size(); i++) {
				Entity entity = visibleEntities.get(i);
				if (collidesWith(entity)) {
					
					// TODO: Replace with the static variables
					if (entity.getType().equals("WallTile") || entity.getType().equals("DoorTile")) {
						collidedWithWall = true;
						Debugger.main.print(getID() + " hit wall");
					}
					
					else if (entity.getType() == Player.TYPE) {
						if (info.getFrameCount() % 150 == 0) {
							Debugger.main.print(getID() + " attacked " + entity.getID());
							entity.takeDamage(attackStrength);
						}
					} 
				}			
			}
			
			if (offsetY >= totalYDelta || offsetY < 0 ||  collidedWithWall) {
				yDelta *= -1;
			}
			if (offsetX >= totalXDelta || offsetX < 0 || collidedWithWall) {
				xDelta *= -1;
			}
			
			offsetX += xDelta;
			offsetY += yDelta;
			updateYBy(xDelta);
			updateYBy(yDelta);
		}
	}

}
