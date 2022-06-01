import restore.Coder;
import restore.Encodable;
import restore.CoderException;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: SwitchDoor.java
//
//Add your name here if you work on this class:
/** @author Johnny */ 

public class SwitchDoor extends Entity implements Encodable {
	public static String TYPE = "SwitchDoor";
	private static String[][] IMAGE_FILES = { {"SwitchDoor.png", "FloorTile.png"}, {"SwitchDoorV.png", "FloorTile.png"} };
	
	private int[] combination;
	private DoorSwitch[] switches;
	
	public enum Orientation {
		HORIZONTAL, VERTICAL;
	}
	
	/**
	 * Initalizes a SwitchDoor entity.
	 * @param combination The combination of the SwitchDoor entity.
	 * @param switches An array of doorswtich entities.
	 */
	public SwitchDoor(int[] combination, DoorSwitch[] switches, Orientation orientation) {
		super(TYPE, 0, IMAGE_FILES[orientation.ordinal()]);
		this.combination = combination;
		this.switches = switches;
	}
	
	public SwitchDoor(Coder coder) throws CoderException {
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
	
	/**
	 * Determines whether the SwitchDoor is open or not.
	 * @return Whether the SwitchDoor is open or not.
	 */
	public boolean isOpen() {
		boolean combo = true;
		for (int i = 0; i < combination.length; i++) {
			if (combination[i] != switches[i].getCombNumber()) {
				combo = false;
			}
		}
		return combo;
	}
	
	
	
	/**
	 * Cycles the current entity.
	 * @param level The current level.
	 * @param info The game information.
	 */
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
