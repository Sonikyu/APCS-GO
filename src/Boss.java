import java.util.ArrayList;

import restore.Coder;
import restore.CoderException;

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
	private long retreatFrame;
	private long lastFrameAttacked;
	private int radius = 100;
	private int dx;
	private int dy;
	private int d;
	private int xi;
	private int yi;
	private boolean minionAttack = false;
	private boolean retreat = false;
	private boolean aggravated = false;
	
	public Boss() {
		super(Boss.TYPE, Boss.MAX_HEALTH, Boss.IMAGE_FILE);;
	}
	
	public Boss(Coder coder) throws CoderException {
		super(coder);
	}
	
	public void encode(Coder coder) {
		super.encode(coder);
	}
	public void setMinionAttack(boolean attack) {
		minionAttack = attack;
	}
	

	public boolean plotLineLow(int x0, int y0, int x1, int y1) {
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
	
	public boolean plotLineHigh(int x0, int y0, int x1, int y1) {
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
	
	public void cycle(Level level, Game.GameInfo info) {
		ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
		for (int i = 0; i < visibleEntities.size(); i ++) {
			Entity entity = visibleEntities.get(i);
			if (entity.isOfType(Player.TYPE)) {
				Player p = (Player) entity;
				if (collidesWith(p)) {
					if (info.getFrameCount() - lastFrameAttacked >= 100) {
						p.takeDamage(info, 10);
						lastFrameAttacked = info.getFrameCount();
					}
				}
			}
		}
		if (aggravated) {			
			if (Math.abs(randY-startY) < Math.abs(randX-startX)) {
				if(plotLineLow(startX, startY, randX, randY)) {
					aggravated = false;
					retreat = true;
					retreatFrame = info.getFrameCount();
				}
			}
			else {
				if(plotLineHigh(startX, startY, randX, randY)) {
					Debugger.main.print("Done");
					aggravated = false;
					retreat = true;
					retreatFrame = info.getFrameCount();
				};
			}
		}
		else if (retreat) {
			if (info.getFrameCount() - retreatFrame >= 100) {
//				Debugger.main.print("Duration over");
				if (Math.abs(startY-retreatY) < Math.abs(startX-retreatX)) {
					Debugger.main.print("Slope is less than 1");
					if(plotLineLow(retreatX, retreatY, startX, startY)) {
						Debugger.main.print("Done");
						retreat = false;
						minionAttack = false;
					}
				}
				else {
					Debugger.main.print("Slope is more than 1");
					if(plotLineHigh(retreatX, retreatY, startX, startY)) {
						Debugger.main.print("Done");
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
			
			randX = Math.max(30, randX);
			randX = Math.min(800 - getWidth(), randX);
			
			randY = Math.max(30, randY);
			randY = Math.min(600 - getHeight(), randY);
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

