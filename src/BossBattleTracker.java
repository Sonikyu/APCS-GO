import java.util.ArrayList;

import restore.Coder;
import restore.CoderException;
import restore.Encodable;

public class BossBattleTracker extends Entity implements Encodable {
	public static String TYPE;
	private static int MAX_HEALTH = 140;
	private static String IMAGE_FILE = "Enemy.png";
	
	private long frameCount;
	private long lastFrameAttacked;
	private boolean attacked = false;
	private int attackStrength = 10;
	
	private int refreshSpeed;
	private Boss boss;
	
	
	/**
	 * Initalizes a boss minion.
	 * @param boss The boss entity.
	 * @param type The entity's type.
	 * @param refreshSpeed The number of frames.
	 */
	public BossBattleTracker(Boss boss, String type, int refreshSpeed) {
		super(type, BossBattleTracker.MAX_HEALTH, BossBattleTracker.IMAGE_FILE);
		this.TYPE = type;
		this.boss = boss;
		this.refreshSpeed = refreshSpeed;
	}
	
	/**
	 * See coder class.
	 */
	public BossBattleTracker(Coder coder) throws CoderException {
		super(coder);
		this.boss = new Boss(coder);
	}
	
	/**
	 * See coder class.
	 */
	public void encode(Coder coder) {
		super.encode(coder);
		boss.encode(coder);
	}
	
	/**
	 * Determines if the entity should be showing.
	 * @return Whether the entity should show or not.
	 */
	@Override
	public boolean shouldShow() {
		return !isDead();
	}
	
	/**
	 * Hides the entity when it dies.
	 */
	@Override
	public void whenDead() {
		boss.minionDead();
		hide();
	}
	
	/**
	 * Makes the entity take damage.
	 * @param change The amount of damage the entity takes.
	 */
	@Override
	public void takeDamage(int change) {
		super.takeDamage(change);
		boss.setMinionAttack(true);
	}
	
	/**
	 * Moves the entity.
	 * @param xOffset The change in the x-coordinate.
	 * @param yOffSet The change in the y-coordinate.
	 */
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
	
	/**
	 * Cycles the entity every frame.
	 * @param level The current level.
	 * @param info The game information.
	 */
	public void cycle(Level level, Game.GameInfo info) {
		frameCount = info.getFrameCount();
		if (frameCount % refreshSpeed != 0) {
			ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
			for (int i = 0; i < visibleEntities.size(); i++) {
				Entity entity = visibleEntities.get(i);
				if (entity.getType().equals(Player.TYPE)) {
					Player p = (Player) entity;
					if (collidesWith(p)) {
						if (frameCount - lastFrameAttacked >= 100) {
							p.takeDamage(info, attackStrength);
							lastFrameAttacked = frameCount;
						}
					}
					int xOffset = getX() - p.getX();
					int yOffset = getY() - p.getY();
					move(xOffset, yOffset);
				}
			}
		}
	}
}

