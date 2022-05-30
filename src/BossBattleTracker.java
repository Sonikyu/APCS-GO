import java.util.ArrayList;

public class BossBattleTracker extends Entity{
	public static String TYPE = "TrackingEnemy";
	private static int MAX_HEALTH = 100;
	private static String IMAGE_FILE = "Enemy.png";
	
	private long frameCount;
	private long lastFrameAttacked;
	
	private int attackStrength = 10;
	
	public BossBattleTracker() {
		super(BossBattleTracker.TYPE, BossBattleTracker.MAX_HEALTH, BossBattleTracker.IMAGE_FILE);
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
		frameCount = info.getFrameCount();
		if (frameCount % 3 != 0) {
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
