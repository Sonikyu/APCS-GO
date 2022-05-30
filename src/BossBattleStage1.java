import java.util.ArrayList;

public class BossBattleStage1 extends Entity{
	public static String TYPE = "TrackingEnemy";
	private static int MAX_HEALTH = 100;
	private static String IMAGE_FILE = "Enemy.png";
	
	private int radius = 10;
	private int randX;
	private int randY;
	
	private boolean aggravated = false;
	public BossBattleStage1() {
		super(BossBattleStage1.TYPE, BossBattleStage1.MAX_HEALTH, BossBattleStage1.IMAGE_FILE);
	}
	
	public void cycle(Level level, Game.GameInfo info) {
		
		if (aggravated) {
			
		}
		else {
		ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
			for (int i = 0; i < visibleEntities.size(); i ++) {
				Entity entity = visibleEntities.get(i);
				if (entity.getType().equals(Player.TYPE)) {
					Player p = (Player) entity;
					if (p.isAttacking()) {
						int px = p.getX();
						int py = p.getY();
						
						int ex = getX();
						int ey = getY();
						
						int deltay = (ey-py);
						int deltax = (ex-px);
						
						double hypotenuse = Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2));
						
		
						double centerX = -(deltax * radius/hypotenuse - px);
						double centerY = -(deltay * radius/hypotenuse - py);
						
						double r = radius * Math.sqrt(Math.random());
						double theta = Math.random() * 2 * Math.PI;
						
						randX = (int) (centerX + r * Math.cos(theta));
						randY = (int) (centerY + r * Math.sin(theta));
						
						randX = Math.max(30, randX);
						randX = Math.min(770 - 30, randX);
						
						randY = Math.max(30, randY);
						randY = Math.min(570 - 30, randY);
					
					}
					
				}
			}
		}
	}
}
