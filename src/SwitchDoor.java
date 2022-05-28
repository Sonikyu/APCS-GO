import restore.Coder;
import restore.Encodable;

public class SwitchDoor extends Entity implements Encodable {
	public static String TYPE = "SwitchDoor";
	private static String[] IMAGE_FILES = { "SwitchDoor.png", "FloorTile.png" };
	
	private int[] combination;
	private DoorSwitch[] switches;
	
	public SwitchDoor(int[] combination, DoorSwitch[] switches) {
		super(TYPE, 0, IMAGE_FILES);
		this.combination = combination;
		this.switches = switches;
	}
	
	public SwitchDoor(Coder coder) {
		super(coder);
		int combinationSize = coder.decodeInt();
		this.combination = new int[combinationSize];
		for (int i = 0; i < combinationSize; i++) {
			combination[i] = coder.decodeInt();
		}
		int numSwitches = coder.decodeInt();
		switches = new DoorSwitch[numSwitches];
		for (int i = 0; i < numSwitches; i ++) {
			switches[i] = new DoorSwitch(coder);
		}
	}
	
	public void encode(Coder coder) {
		super.encode(coder);
		coder.encode(combination.length);
		for (int i = 0; i < combination.length; i++) {
			coder.encode(combination[i]);
		}
		coder.encode(switches.length);
		for (int i = 0; i < switches.length; i++) {
			switches[i].encode(coder);
		}
	}
	
	public boolean isOpen() {
		boolean combo = true;
		for (int i = 0; i < combination.length; i++) {
			if (combination[i] != switches[i].getCombNumber()) {
				combo = false;
			}
		}
		return combo;
	}
	
	
//	@Override
//	public boolean shouldShow() {
//		return !hasCorrectCombination;
//	}
	
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		if (isOpen()) {
			setImageAtIndex(1);
		}
		else {
			setImageAtIndex(0);
		}
	}

}
