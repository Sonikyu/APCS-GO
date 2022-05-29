
public class StartScreen extends Entity {
	public static String TYPE = "StartScreen";
	private static String IMAGE_FILE =  "StartScreen.png";
	
	public StartScreen() {
		super(TYPE, 0, IMAGE_FILE);
	}
	
	

	@Override
	public void cycle(Level level, Game.GameInfo info) {
		if (info.getKeysDown().size() > 0) {
			info.startGame();
		}
	}
}
