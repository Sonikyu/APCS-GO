
public class EndScreen extends Entity {
	public static String TYPE = "EndScreen";
	private static String IMAGE_FILE =  "EndScreen.png";
	
	public EndScreen() {
		super(TYPE, 0, IMAGE_FILE);
	}
	
	

	@Override
	public void cycle(Level level, Game.GameInfo info) {
	
	}

}
