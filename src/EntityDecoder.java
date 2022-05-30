import restore.Coder;
import restore.CoderException;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: EntityDecoder.java
//
// Add your name here if you work on this class:
/** @author Ethan */ 

public class EntityDecoder {
	/**
	 * Decodes the game entity from the game string.
	 * @param coder The coder object that uses the game string to create the game.
	 * @return The decoded entity.
	 */
	public static Entity decode(Coder coder) throws CoderException {
		String type = coder.decodeString();
		coder.putBack(type);
		
		if (type.equals(Player.TYPE)) {
			return new Player(coder);
		} 
		if (type.equals(Boss.TYPE)) {
			return new Boss(coder);
		}
		if (type.equals(BossBattleTracker.TYPE)) {
			return new BossBattleTracker(coder);
		}
		if (type.equals(DoorSwitch.TYPE)) {
			return new DoorSwitch(coder);
		}
		if (type.equals("DoorTile")) {
			return new DoorTile(coder);
		}
		if (type.equals(EndScreen.TYPE)) {
			return new EndScreen(coder);
		}
		if (type.equals(Heart.TYPE)) {
			return new Heart(coder);
		}
		if (type.equals(InventorySlot.TYPE)) {
			return new InventorySlot(coder);
		}
		if (type.equals(Item.TYPE)) {
			return new Item(coder);
		}
		if (type.equals(MoveOnlyEnemy.TYPE)) {
			return new MoveOnlyEnemy(coder);
		}
		if (type.equals(NPCGuard.TYPE)) {
			return new NPCGuard(coder);
		}
		if (type.equals(PlayerWeapon.TYPE)) {
			return new PlayerWeapon(coder);
		}
		if (type.equals(StartScreen.TYPE)) {
			return new StartScreen(coder);
		}
		for (String tileType : Tile.TYPES) {
			if (type.equals(tileType)) {
				return new Tile(coder);
			}
		}
		throw new CoderException("Unknown entity type");
	}
	
	private EntityDecoder() {}
}
