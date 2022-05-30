import restore.Coder;
import restore.Encodable;
import restore.CoderException;

public class BreakableTile extends Entity implements Encodable{
	public static String TYPE = "BreakableTile";
	private static String[] IMAGE_FILES = {"SwitchDoor.png", "BreakingTile.png"};
	
	
	public BreakableTile(int health) {
		super(TYPE, health, IMAGE_FILES);
		Debugger.main.print("BreakableTile Created");
	}
	
	public BreakableTile(Coder coder) throws CoderException {
		super(coder);
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
