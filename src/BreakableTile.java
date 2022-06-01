import restore.Coder;
import restore.Encodable;
import restore.CoderException;

public class BreakableTile extends Entity implements Encodable{
	public static String TYPE = "BreakableTile";
	private static String[] IMAGE_FILES = {"BreakingTile.png", "BreakingTile2.png", "BreakingTile3.png"};
	private int counter = 0 ;
	
	
	/**
	 * Initalizes a BreakableTile entity
	 * @param health The health of the entity.
	 */
	public BreakableTile(int health) {
		super(TYPE, Player.ATTACK_DAMAGE * 3, IMAGE_FILES);
		counter = 0;
	}
	
	/**
	 * See coder class.
	 */
	public BreakableTile(Coder coder) throws CoderException {
		super(coder);
	}

	public void encode(Coder coder) {
		super.encode(coder);
	}
	
	/**
	 * Determines if the entity should show.
	 * @return Whether the entity should show or not.
	 */
	@Override
	public boolean shouldShow() {
		return !isDead();
	}
	
	/**
	 * Hides the entity when it dies.
	 */
	@Override
	public void whenDead() {
		hide();
	}
	
	/**
	 * Causes the enemy to take damage.
	 * @param change How much damage the entity takes.
	 */
	@Override
	public void takeDamage(int change) {
		super.takeDamage(change);
		if (getHealth() < getMaxHealth() && getHealth() > 0) {
			counter ++;
			setImageAtIndex(counter);
		}
	}
	
	/**
	 * Cycles the entity every frame.
	 * @param level The current level.
	 * @param info The game information.
	 */
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		// TODO Auto-generated method stub
	}
	
}
