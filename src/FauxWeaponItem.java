import java.util.ArrayList;

public class FauxWeaponItem extends Entity {
	public static final String TYPE = "FauxWeaponItem";
	private static final String[] IMAGE_NAMES = {"PlayerAttack_North.png", "PlayerAttack_East.png",  "PlayerAttack_South.png",  "PlayerAttack_West.png"};
	public FauxWeaponItem() {
		super(TYPE, 0, IMAGE_NAMES);
	}
	
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		if (info.getFrameCount() % 50 == 0) {
			this.setNextImage();
		}
	}
}
