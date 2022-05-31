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
import restore.CoderException;

public class TrackingEnemy extends Entity{
	public static String TYPE = "TrackingEnemy";
	private static int MAX_HEALTH = 100;
	private static String[] IMAGE_FILES = {"TrackingEnemy.png", "TrackingEnemyCharging.png", "TrackingEnemyStunned.png"};
	
	private int range;
	private int xTether;
	private int yTether;
	private int attackStrength;
	private int stunTransitionDuration;
	private int stunStationaryDuration;
	private int attackDelay;
	private int refreshSpeed;
	
	private long lastFrameInRadius = 0;
	private long lastFrameAttacked = 0;
	private long lastFrameStunned = 0;
	
	private long stationaryFrame = 0;
	private boolean stationary = false;
	
	private boolean stunned = false;
	private boolean transitioning = false;
	
	public TrackingEnemy(int range, int xTether, int yTether, int attackStrength, int stunTransitionDuration, int stunStationaryDuration, int attackDelay, int refreshSpeed) {
		super(TrackingEnemy.TYPE, TrackingEnemy.MAX_HEALTH, TrackingEnemy.IMAGE_FILES);
		this.range = range;
		this.xTether = xTether;
		this.yTether = yTether;
		this.attackStrength = attackStrength;
		this.stunTransitionDuration = stunTransitionDuration;
		this.stunStationaryDuration = stunStationaryDuration;
		this.attackDelay = attackDelay;
		this.refreshSpeed = refreshSpeed;
	}
	
	public TrackingEnemy(int rangeInTiles, int colTether, int rowTether, int attackStrength, int stunTransitionDuration, int stunStationaryDuration) {
		this(rangeInTiles * Tile.WIDTH, colTether * Tile.WIDTH, rowTether * Tile.HEIGHT, attackStrength, stunTransitionDuration, stunStationaryDuration, 150, 3);
	}
	
	public TrackingEnemy(Coder coder) throws CoderException {
		super(coder);
		this.range = coder.decodeInt();
		this.xTether = coder.decodeInt();
		this.yTether = coder.decodeInt();
		this.attackStrength = coder.decodeInt();
		this.stunTransitionDuration = coder.decodeInt();
		this.stunStationaryDuration = coder.decodeInt();
		this.attackDelay = coder.decodeInt();
		this.refreshSpeed = coder.decodeInt();
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
		coder.encode(this.stunTransitionDuration);
		coder.encode(this.stunStationaryDuration);
		coder.encode(this.attackDelay);
		coder.encode(this.refreshSpeed);
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
		SFX.main.run(SFX.Sound.ENTITYKILLED);
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
		if (refreshSpeed != 0 && info.getFrameCount() % refreshSpeed != 0) {
			final long frameCount = info.getFrameCount();
			ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
			if (transitioning) {
				this.setImageAtIndex(2);
				int xOffset = getX() - xTether;
				int yOffset = getY() - yTether;
				if (xOffset == 0 && yOffset == 0) {
					stationary = true;
					stationaryFrame = frameCount;
					transitioning = false;
				}
				else {
					move(xOffset, yOffset);
				}
			}
			else if (stationary) {
				this.setImageAtIndex(2);
				if (frameCount - stationaryFrame >= stunStationaryDuration) {
					stationary = false;
					stunned = false;
				}
			}
			else {
				for (int i = 0; i < visibleEntities.size(); i++) {
					Entity entity = visibleEntities.get(i);
					if (collidesWith(entity)) {
						if (entity.isOfType(Tile.WALL_TYPES)) {
							this.setImageAtIndex(2);
							if (stunned) {
								if (frameCount - lastFrameStunned >= stunTransitionDuration) {
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
							this.setImageAtIndex(1);
							int xOffset = getX() - entity.getX();
							int yOffset = getY() - entity.getY();
							lastFrameInRadius = frameCount;
							move(xOffset, yOffset);
							
						}
						else if (entity.getType() == Player.TYPE &&  distance > range){
							if (frameCount - lastFrameInRadius >= 200) {
								this.setImageAtIndex(0);
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