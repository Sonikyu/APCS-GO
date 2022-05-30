import restore.Coder;
import restore.Encodable;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: Debugger.java
//
//Add your name here if you work on this class:
/** @author Johnny */ 

public class DoorSwitch extends Entity implements Encodable {
	public static String TYPE = "DoorSwitch";
	private static String[] IMAGE_FILES = {"DoorSwitch.png", "DoorSwitch_Stage1.png"};
	
	private int combNumber;
	
	
	/**
	 * Initializes a DoorSwitch entity.
	 */
	
	public DoorSwitch() {
		super(TYPE, 0 , IMAGE_FILES);
		this.combNumber = 0;
	}
	
	/**
	 * 
	 * @param combNumber The starting combination number of the switch.
	 */
	public DoorSwitch(int combNumber) {
		super(TYPE, 0 , IMAGE_FILES);
		if (combNumber < IMAGE_FILES.length) {
			this.combNumber = combNumber;
		}
		else {
			this.combNumber = 0;
		}
	}
	
	public DoorSwitch(Coder coder) {
		super(coder);
		this.combNumber = coder.decodeInt();
	}
	
	public void encode(Coder coder) {
		super.encode(coder);
		coder.encode(combNumber);
	}

	public void setCombNumber(int combNumber) {
		this.combNumber = combNumber;
	}
	
	public void nextCombNumber() {
		this.combNumber++;
		combNumber = combNumber % IMAGE_FILES.length;
	}
	
	public int getCombNumber() {
		return combNumber;
	}
	
	@Override
	public void cycle(Level level, Game.GameInfo info) {
		// TODO Auto-generated method stub
		
	}
	

	
}
