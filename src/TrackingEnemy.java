// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: TrackingEnemy.java
//
// Add your name here if you work on this class:
/** @author Uday*/ 

import java.util.ArrayList;

import restore.Coder;

public class TrackingEnemy extends Entity{
	public static String TYPE = "TrackingEnemy";
	private static int MAX_HEALTH = 100;
	private static String IMAGE_FILE = "Enemy.png";
	
	private int range;
	private int xTether;
	private int yTether;
	private int attackStrength;
	private int stunDuration;
	private int attackDelay;
	private int refreshSpeed;
	private long frameCount;
	
	private long lastFrameInRadius = 0;
	private long lastFrameAttacked = 0;
	private long lastFrameStunned = 0;
	private boolean stunned = false;
	private boolean transitioning = false;
	
	public TrackingEnemy(int range, int xTether, int yTether, int attackStrength, int stunDuration, int attackDelay, int refreshSpeed) {
		super(TrackingEnemy.TYPE, TrackingEnemy.MAX_HEALTH, TrackingEnemy.IMAGE_FILE);
		this.range = range;
		this.xTether = xTether;
		this.yTether = yTether;
		this.attackStrength = attackStrength;
		this.stunDuration = stunDuration;
		this.attackDelay = attackDelay;
		this.refreshSpeed = refreshSpeed;
	}
	
	public TrackingEnemy(Coder coder) {
		super(coder);
		this.range = coder.decodeInt();
		this.xTether = coder.decodeInt();
		this.yTether = coder.decodeInt();
		this.attackStrength = coder.decodeInt();
		this.stunDuration = coder.decodeInt();
		this.attackDelay = coder.decodeInt();
		this.refreshSpeed = coder.decodeInt();
		this.frameCount = coder.decodeLong();
		this.lastFrameInRadius = coder.decodeLong();
		this.lastFrameAttacked = coder.decodeLong();
		this.lastFrameStunned = coder.decodeLong();
		this.stunned = coder.decodeBoolean();
		this.transitioning = coder.decodeBoolean();
	}
	
	public void encode(Coder coder) {
		super.encode(coder);
		coder.encode(this.range);
		coder.encode(this.xTether);
		coder.encode(this.yTether);
		coder.encode(this.attackStrength);
		coder.encode(this.stunDuration);
		coder.encode(this.attackDelay);
		coder.encode(this.refreshSpeed);
		coder.encode(this.frameCount);
		coder.encode(this.lastFrameInRadius);
		coder.encode(this.lastFrameAttacked);
		coder.encode(this.lastFrameStunned);
		coder.encode(this.stunned);
		coder.encode(this.transitioning);
	}
	
	@Override
	public boolean shouldShow() {
		return !isDead();
	}
	
	@Override
	public void whenDead() {
		hide();
	}
	
	public void move(int xOffset, int yOffset) {
		if (xOffset < 0) {
			updateXBy(1);
		}
		
		if (xOffset > 0) {
			updateXBy(-1);
		}
		
		if (yOffset < 0) {
			updateYBy(1);
		}
		if (yOffset > 0) {
			updateYBy(-1);
		}
	}
	
	public void cycle(Level level, Game.GameInfo info) {
		if (info.getFrameCount() % refreshSpeed != 0) {
			frameCount = info.getFrameCount();
			ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
			if (transitioning) {
				int xOffset = getX() - xTether;
				int yOffset = getY() - yTether;
				if (xOffset == 0 && yOffset == 0) {
					stunned = false;
					transitioning = false;
				}
				else {
					move(xOffset, yOffset);
				}
			}
			else {
				for (int i = 0; i < visibleEntities.size(); i++) {
					Entity entity = visibleEntities.get(i);
					if (collidesWith(entity)) {
						if (entity.isOfType("WallTile")) {
							if (stunned) {
								if (frameCount - lastFrameStunned >= stunDuration) {
									transitioning = true;
								}
							}
							else {
								Debugger.main.print(getID() + " is stunned ");
								stunned = true;
								lastFrameStunned = frameCount;
							}
						}
						else if (entity.isOfType(Player.TYPE)){
							if (frameCount - lastFrameAttacked >= attackDelay) {
								Debugger.main.print(getID() + " attacked " + entity.getID());
								Player p = (Player) entity;
								p.takeDamage(info, attackStrength);
								lastFrameAttacked = frameCount;
							}
						}
					}
					if (!stunned) {
						double distance = Math.sqrt(Math.pow(entity.getX() - xTether, 2) + Math.pow(entity.getY() - yTether, 2));
						if (entity.getType() == Player.TYPE &&  distance <= range){
							int xOffset = getX() - entity.getX();
							int yOffset = getY() - entity.getY();
							lastFrameInRadius = frameCount;
							move(xOffset, yOffset);
							
						}
						else if (entity.getType() == Player.TYPE &&  distance > range){
							if (frameCount - lastFrameInRadius >= 200) {
								if (getX() - xTether != 0 || getY() - yTether != 0) {
									int xOffset = getX() - xTether;
									int yOffset = getY() - yTether;
									move(xOffset, yOffset);
									
								}
							}
						}
					}
				}
			}
		}
	}
}