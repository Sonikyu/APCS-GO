import java.util.ArrayList;

import restore.Coder;
import restore.Encodable;

//made by johnny

public class PlayerWeapon extends Entity implements Encodable {
	private static String TYPE = "Attack";
	private static String[] IMAGE_FILES = {"PlayerAttack_North.png", "PlayerAttack_North.png", "PlayerAttack_East.png", "PlayerAttack_North.png", "PlayerAttack_South.png", "PlayerAttack_North.png", "PlayerAttack_West.png" ,"PlayerAttack_North.png"};
	
	private int attackDamage;
	private int frameAttacking = -Player.ATTACK_DURATION;
	private Player.PlayerDirection pD;
	
	public PlayerWeapon(Player.PlayerDirection pD, int attackDamage) {
		super(TYPE, 0, IMAGE_FILES);
		this.pD = pD;
		this.attackDamage = attackDamage;
	}

	public PlayerWeapon(Coder coder) {
		super(coder);
		this.attackDamage = coder.decodeInt();
	}
	
	public void encode(Coder coder) {
		super.encode(coder);
		coder.encode(this.attackDamage);
	}
	
	public void setDirection(Player.PlayerDirection pD) {
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
		if (isVisible() && (int) info.getFrameCount() - frameAttacking > Player.ATTACK_DURATION) {
			ArrayList<Entity> visibleEntities = level.getCurrentRoom().getVisibleEntities();
			for (int i = 0; i < visibleEntities.size(); i++) {
				Entity entity = visibleEntities.get(i);
				if (collidesWith(entity)) {
					if (entity.isOfType(MoveOnlyEnemy.TYPE) || entity.isOfType(TrackingEnemy.TYPE)) {
						frameAttacking = (int) info.getFrameCount();
						entity.takeDamage(attackDamage);
						Debugger.main.print(entity + " took " + attackDamage + " damage.");
					}
					else if (entity.isOfType(DoorSwitch.TYPE)) {
						DoorSwitch s = (DoorSwitch) entity;
						frameAttacking = (int) info.getFrameCount();
						s.setNextImage();
						s.nextCombNumber();
					}
				}
			}
		}
	}

}
	
	
