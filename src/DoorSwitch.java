import restore.Coder;
import restore.Encodable;

public class DoorSwitch extends Entity implements Encodable {
	private static String TYPE = "DoorSwitch";
	private static String[] IMAGE_FILES = {"DoorSwitch.png", "DoorSwitch_Stage1.png"};
	
	private int combNumber;
	
	public DoorSwitch() {
		super(TYPE, 0 , IMAGE_FILES);
		this.combNumber = 0;
	}
	
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
