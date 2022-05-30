import restore.Coder;
import restore.Encodable;

public class BreakableTile extends Entity implements Encodable{
	public static String TYPE = "BreakableTile";
	private static String[] IMAGE_FILES = {"SwitchDoor.png", "BreakingTile.png"};
	
	
	public BreakableTile(int health) {
		super(TYPE, health, IMAGE_FILES);
	}
	
	public BreakableTile(Coder coder) {
		super(coder);
	}

	public void encode(Coder coder) {
		super.encode(coder);
	}
	
	@Override
	public boolean shouldShow() {
		return !isDead();
	}
	
	@Override
	public void whenDead() {
		hide();
	}
	
	@Override
	public void takeDamage(int change) {
		super.takeDamage(change);
		if (getHealth() < getMaxHealth() && getHealth() > 0) {
			setImageAtIndex(1);
		}
	}
	
	
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		// TODO Auto-generated method stub
		
	}
	
}
