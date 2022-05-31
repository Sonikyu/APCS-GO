// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: MoveOnlyEnemy.java
//
// Add your name here if you work on this class:
/** @author Uday, Johnny */ 

import java.util.ArrayList;
import restore.Coder;
import restore.CoderException;

public class MoveOnlyEnemy extends Entity {
	public static String TYPE = "MoveOnlyEnemy";
	private static int MAX_HEALTH = 100;
	private static String IMAGE_FILE = "Enemy.png";
	
	private int xDelta;
	private int yDelta;
	private int offsetX;
	private int offsetY;
	private int totalXDelta;
	private int totalYDelta;
	private int attackStrength;
	private int attackDelay = 150;
	private long lastFrameAttacked;
	
	public MoveOnlyEnemy(int totalXDelta, int totalYDelta, int attackStrength, int xDelta, int yDelta) {
		super(MoveOnlyEnemy.TYPE, MoveOnlyEnemy.MAX_HEALTH, MoveOnlyEnemy.IMAGE_FILE);
		this.xDelta = xDelta;
		this.yDelta = yDelta;
		this.totalXDelta = totalXDelta;
		this.totalYDelta = totalYDelta;
		this.attackStrength = attackStrength;

		if (totalXDelta < 0) {
			//this.offsetX = totalXDelta;
			this.updateXBy(totalXDelta);
			totalXDelta = -totalXDelta;
			//this.xDelta *= -1;
		} else {
			this.offsetX = 0;
		}
		
		if (totalYDelta < 0) {
			//this.offsetY = totalYDelta;
			this.updateYBy(totalYDelta);
			totalYDelta = -totalYDelta;
			//this.yDelta *= -1;
		} else {
			this.offsetY = 0;
		}
	}
	
	public MoveOnlyEnemy(Coder coder) throws CoderException {
		super(coder);
		this.xDelta = 1;
		this.yDelta = 1;
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
	public boolean shouldShow() {
		return !isDead();
	}
	
	@Override
	public void whenDead() {
		SFX.main.run(SFX.Sound.ENTITYKILLED); //DIE NOISE
		hide();
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
					if (entity.isOfType("WallTile") || entity.isOfType("DoorTile")) {
						collidedWithWall = true;
						Debugger.main.print(getID() + " hit wall");
					}
					
					else if (entity.isOfType(Player.TYPE)) {
						if (info.getFrameCount() - lastFrameAttacked >= attackDelay) {
							Debugger.main.print(getID() + " attacked " + entity.getID());
							Player p = (Player) entity;
							p.takeDamage(info, attackStrength);
							lastFrameAttacked = info.getFrameCount();
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