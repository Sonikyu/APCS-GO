import java.util.ArrayList;
import restore.Coder;
import restore.CoderException;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: Boss.java
//
//Add your name here if you work on this class:
/** 
 * Class for creating a boss to be used in the boss battle
 * @author Uday 
 * */ 
public class Boss extends Entity{
	public static String TYPE = "BossEnemy";
	private static int MAX_HEALTH = 100;
	private static String IMAGE_FILE = "ParthInThePark.png";
	
	private int startX;
	private int startY;
	private int retreatX;
	private int retreatY;
	private int randX;
	private int randY;
	private int radius = 100;
	private int dx;
	private int dy;
	private int d;
	private int xi;
	private int yi;
	
	private long retreatFrame;
	private long lastFrameAttacked;
	
	private boolean minionAttack = false;
	private boolean retreat = false;
	private boolean aggravated = false;
	
	
	/**
	 * Constructs a boss object
	 */
	public Boss() {
		super(Boss.TYPE, Boss.MAX_HEALTH, Boss.IMAGE_FILE);;
	}
	
	/**
	 * See coder class.
	 */
	public Boss(Coder coder) throws CoderException {
		super(coder);
	}
	

	/**
	 * See coder class.
	
	/**
	 * See coder
	 * @param coder See Coder

	 */
	public void encode(Coder coder) {
		super.encode(coder);
	}
	

	/**
	 * Tells the boss whether the minion has been attacked
	 * @param attack Boolean value to represent whether the minion has been attacked

	 */
	public void setMinionAttack(boolean attack) {
		minionAttack = attack;
	}
	
	/**
	 * 
	 * @param
	 * @return
	 */

	private boolean plotLineLow(int x0, int y0, int x1, int y1) {
		if (dx < 0) {
			xi = -xi;
			dx = -dx;
		}
		
		if (dy < 0) {
			yi = -yi;
			dy = -dy;
		}
		
		if (Math.abs(getX() - x1) > 1) {
			updateXBy(xi);
			if (d > 0) {
				updateYBy(yi);
				d = d + (2 * (dy-dx));
			}
			else
				d = d + 2 * dy;
			return false;
		}
		else {
			retreatX = getX();
			retreatY = getY();
			dx = startX - retreatX;
			dy = startY - retreatY;
			d = (2*dy) - dx;
			xi = 2;
			yi = 2;
			return true;
		}
	}
	
	private boolean plotLineHigh(int x0, int y0, int x1, int y1) {
			if (dx < 0) {
				xi = -xi;
				dx = -dx;
			}
			
			if (dy < 0) {
				yi = -yi;
				dy = -dy;
			}
			
			if (Math.abs(getY() - y1) > 1) {
				updateYBy(yi);
				if (d > 0) {
					updateXBy(xi);
					d = d + (2 * (dx-dy));
				}
				else {
					d = d + 2 * dy;
				}
				return false;
			}
			else {
				retreatX = getX();
				retreatY = getY();
				dx = startX - retreatX;
				dy = startY - retreatY;
				d = (2*dy) - dx;
				xi = 2;
				yi = 2;
				return true;
			}
		}
	
	
	/**
	 * Cycles the enemy
	 * @param level The current level in the game.
	 * @param info The game information.
	 */
	public void cycle(Level level, Game.GameInfo info) {
		ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
		for (int i = 0; i < visibleEntities.size(); i ++) {
			Entity entity = visibleEntities.get(i);
			if (entity.isOfType(Player.TYPE)) {
				Player p = (Player) entity;
				if (collidesWith(p)) {
					if (info.getFrameCount() - lastFrameAttacked >= 100) {
//						Boss makes contact with player, damage sound
						p.takeDamage(info, 10);
						SFX.main.run(SFX.Sound.BICEPSDIFF);
						lastFrameAttacked = info.getFrameCount();
					}
				}
			}
			else if (entity.isOfType("BossBattleTracker")) {
				takeDamage(10000);
			}
		}
		if (aggravated) {
			SFX.main.run(SFX.Sound.MICHAELJACKSON);
			if (Math.abs(randY-startY) < Math.abs(randX-startX)) {
				if(plotLineLow(startX, startY, randX, randY)) {
					aggravated = false;
					retreat = true;
					retreatFrame = info.getFrameCount();
				}
			}
			else {
				if(plotLineHigh(startX, startY, randX, randY)) {
					aggravated = false;
					retreat = true;
					retreatFrame = info.getFrameCount();
				};
			}
		}
		else if (retreat) {
//			Turn off aggravated
			if (info.getFrameCount() - retreatFrame >= 100) {
				if (Math.abs(startY-retreatY) < Math.abs(startX-retreatX)) {
					if(plotLineLow(retreatX, retreatY, startX, startY)) {
						retreat = false;
						minionAttack = false;
					}
				}
				else {
					if(plotLineHigh(retreatX, retreatY, startX, startY)) {
						retreat = false;
						minionAttack = false;
					}
				}
			}
		}
		
		else if (minionAttack) {
			int px = 0;
			int py = 0;
			int ex = 0;
			int ey = 0;
			for (int i = 0; i < visibleEntities.size(); i ++) {
				Entity entity = visibleEntities.get(i);
				if (entity.getType().equals(Player.TYPE)) {
					Player p = (Player) entity;
					px = p.getX();
					py = p.getY();
				}
				else if (entity.getType().equals("BossBattleMinion")) {
					ex = entity.getX();
					ey = entity.getY();
				}
					
			}		
			int deltay = (ey-py);
			int deltax = (ex-px);
			
			double hypotenuse = Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2));
			

			double centerX = -(deltax * radius/hypotenuse - px);
			double centerY = -(deltay * radius/hypotenuse - py);
			
			double r = radius * Math.sqrt(Math.random());
			double theta = Math.random() * 2 * Math.PI;
			
			randX = (int) (centerX + r * Math.cos(theta));
			randY = (int) (centerY + r * Math.sin(theta));
			
			randX = Math.max(Tile.WIDTH, randX);
			randX = Math.min((int)info.getSize().getWidth() - getWidth(), randX);
			
			randY = Math.max(Tile.HEIGHT, randY);
			randY = Math.min((int)info.getSize().getHeight() - getHeight(), randY);
			minionAttack = false;
			aggravated = true;
			
			startX = getX();
			startY = getY();
			dx = randX - startX;
			
			dy = randY - startY;
			d = (2*dy) - dx;
			xi = 2;
			yi = 2;
		}
	}
}

