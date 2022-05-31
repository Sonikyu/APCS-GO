import restore.Coder;
import restore.Encodable;
import restore.CoderException;

public class BreakableTile extends Entity implements Encodable{
	public static String TYPE = "BreakableTile";
	private static String[] IMAGE_FILES = {"BreakingTile.png", "BreakingTile2.png", "BreakingTile3.png"};
	private int counter = 0 ;
	
	
	public BreakableTile(int health) {
		super(TYPE, 70, IMAGE_FILES);
		counter = 0;
	}
	
	public BreakableTile(Coder coder) throws CoderException {
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
			counter ++;
			setImageAtIndex(counter);
		}
	}
	
	
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		// TODO Auto-generated method stub
	}
	
}
