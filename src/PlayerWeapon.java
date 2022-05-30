import java.util.ArrayList;
import restore.Coder;
import restore.Encodable;
import restore.CoderException;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: PlayerWeapon.java
//
//Add your name here if you work on this class:
/** @author Johnny, Ethan */ 

public class PlayerWeapon extends Entity implements Encodable {
	public static String TYPE = "Attack";
	private static String[] IMAGE_FILES = {"PlayerAttack_North.png", "PlayerAttack_East.png",  "PlayerAttack_South.png",  "PlayerAttack_West.png"};
	
	private int attackDamage;
	private long frameAttacking = -Player.ATTACK_DURATION;
	private Player.Direction pD;
	
	public PlayerWeapon(Player.Direction pD, int attackDamage) {
		super(TYPE, 0, IMAGE_FILES);
		this.pD = pD;
		this.attackDamage = attackDamage;
	}

	public PlayerWeapon(Coder coder) throws CoderException {
		super(coder);
		//this.pD = getint
		this.attackDamage = coder.decodeInt();
		this.frameAttacking = coder.decodeLong();
	}
	
	public void encode(Coder coder) {
		super.encode(coder);
		coder.encode(pD.ordinal());
		coder.encode(this.attackDamage);
		coder.encode(frameAttacking);
	}
	
	public void setDirection(Player.Direction pD) {
		this.pD = pD;
		this.setImageAtIndex(this.pD.ordinal());
	}
	
	public void setPosition(Player player) {
		switch (pD) {
			case NORTH:
				super.setPosition(player.getX() + (player.getWidth() - getWidth())/2, player.getY() - this.getHeight());
				break;
			case EAST:
				super.setPosition(player.getX() + player.getWidth(), player.getY() + (player.getWidth() - getWidth())/2);
				break;
			case SOUTH:
				super.setPosition(player.getX() + (player.getWidth() - getWidth())/2, player.getY() + player.getHeight());
				break;
			case WEST:
				super.setPosition(player.getX() - this.getWidth(), player.getY() + (player.getWidth() - getWidth())/2);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		if (isVisible() && info.getFrameCount() - frameAttacking > Player.ATTACK_DURATION) {
			ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
			for (int i = 0; i < visibleEntities.size(); i++) {
				Entity entity = visibleEntities.get(i);
				if (collidesWith(entity)) {
					if (entity.isOfType(MoveOnlyEnemy.TYPE) || entity.isOfType(TrackingEnemy.TYPE) || entity.isOfType("BossBattleMinion") || entity.isOfType(BreakableTile.TYPE)) {
						frameAttacking = info.getFrameCount();
						entity.takeDamage(attackDamage);
						Debugger.main.print(entity + " took " + attackDamage + " damage.");
					}
					else if (entity.isOfType(DoorSwitch.TYPE)) {
						DoorSwitch s = (DoorSwitch) entity;
						frameAttacking = info.getFrameCount();
						s.nextCombNumber();
						s.setImageAtIndex(s.getCombNumber());
					}
				}
			}
		}
	}

}
	
	
