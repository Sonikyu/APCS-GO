
import java.util.ArrayList;
import restore.Encodable;
import restore.Coder;

import restore.CoderException;

public class Boss extends Entity {


public class Boss extends Entity implements Encodable {

	public static String TYPE = "BossEnemy";
	private static int MAX_HEALTH = 100;
	private static String IMAGE_FILE = "ParthInThePark.png";
	
	private int randX;
	private int randY;
	private int xTether;
	private int yTether;
	private long retreatFrame;
	private int radius = 10;
	
	private boolean minionAttack = false;
	private boolean retreat = false;
	private boolean aggravated = false;
	
	public Boss(int colTether, int rowTether) {
		super(Boss.TYPE, Boss.MAX_HEALTH, Boss.IMAGE_FILE);
		xTether = colTether * Tile.WIDTH;
		yTether = rowTether * Tile.HEIGHT;
	}
	
	public Boss(Coder coder) throws CoderException {
		super(coder);
		xTether = coder.decodeInt();
		yTether = coder.decodeInt();
	}
	
	public void encode(Coder coder) {
		super.encode(coder);
		coder.encode(xTether);
		coder.encode(yTether);
	}
	
	public void setMinionAttack(boolean attack) {
		minionAttack = attack;
	}
	
	public void move(int xOffset, int yOffset, int speed) {
		if (xOffset < 0) {
			if (Math.abs(xOffset) < speed) {
				updateXBy(-xOffset);
			}
			else {
				updateXBy(speed);
			}
		}
		
		if (xOffset > 0) {
			if (Math.abs(xOffset) < speed) {
				updateXBy(-xOffset);
			}
			else {
				updateXBy(-speed);
			}
		}
		
		if (yOffset < 0) {
			if (Math.abs(yOffset) < speed) {
				updateXBy(-yOffset);
			}
			else {
				updateYBy(speed);
			}
		}
		if (yOffset > 0) {
			if (Math.abs(yOffset) < speed) {
				updateXBy(-yOffset);
			}
			else {
				updateYBy(-speed);
			}
		}
	}
	
	public void cycle(Level level, Game.GameInfo info) {
		if (aggravated) {
			int xOffset = getX() - randX;
			int yOffset = getY() - randY;
			move(xOffset, yOffset, 2);
			if (xOffset == 0 && yOffset == 0) {
//				Debugger.main.print("Reached location");
				aggravated = false;
				retreat = true;
				retreatFrame = info.getFrameCount();
			}
		}
		else if (retreat) {
//			Debugger.main.print("" + retreat);
			if (info.getFrameCount() - retreatFrame >= 100) {
//				Debugger.main.print("Duration over");
				int xOffset = getX() - xTether;
				int yOffset = getY() - yTether;
				move(xOffset, yOffset, 1);
				if (xOffset == 0 && yOffset == 0) {
					retreat = false;
					minionAttack = false;
				}
			}
		}
		else if (minionAttack) {
			ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
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
			
			randX = Math.max(30, randX);
			randX = Math.min(770, randX);
			
			randY = Math.max(30, randY);
			randY = Math.min(570, randY);
			
			Debugger.main.print("X of player: " + px);
			Debugger.main.print("Y of player: " + py);
			
			Debugger.main.print("X of enemy: " + ex);
			Debugger.main.print("Y of enemy: " + ey);
			
			Debugger.main.print("Random center X: " + randX);
			Debugger.main.print("Random center Y: " + randY);
			minionAttack = false;
			aggravated = true;
		}
	}
}