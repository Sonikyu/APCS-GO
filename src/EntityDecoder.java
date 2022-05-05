import restore.Coder;

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
	public static Entity decode(Coder coder) {
		String type = coder.decodeString();
		coder.putBack(type);
		
		if (type.equals(Player.TYPE)) {
			return new Player(coder);
		} 
		else if (type.equals(MoveOnlyEnemy.TYPE)) {
			return new MoveOnlyEnemy(coder);
		}
		else {
			for (String tileType : StaticTile.TYPES) {
				if (type.equals(tileType)) {
					return new StaticTile(coder);
				}
			}
			coder.setErrorMsg("Unknown entity type");
			return null;
		}
	}
	
	private EntityDecoder() {}
}
