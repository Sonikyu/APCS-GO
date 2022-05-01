import java.awt.Dimension;
import java.util.ArrayList;
import restore.Coder;
import restore.Restorer;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: GameRestorer.java
//
//Add your name here if you work on this class:
/** @author Ethan */ 
public class GameRestorer extends Restorer {
	@Override
	public Object decode(Coder coder) {
		int verBreak = coder.decodeInt();
		int verBack = coder.decodeInt();
		if (Game.VER_BREAK != verBreak) {
			coder.setError("Incompatible game versions");
			return null;
		}
		
		int width = coder.decodeInt();
		int height = coder.decodeInt();
		
		Game game = new Game(new Dimension(width, height));
		game.__setVerBack(verBack);
		
		long frameCount = coder.decodeLong();
		game.__setFrameCount(frameCount);
		
		int entityCount = coder.decodeInt();
		EntityRestorer entityRestorer = new EntityRestorer();
		for (int i = 0; i < entityCount; i++) {
			Entity entity = (Entity)entityRestorer.decode(coder);
			if (entity != null) {
				game.addEntity(entity);
			}
		}
		
		return game;
	}

	@Override
	public void encode(Object object, Coder coder) {
		Game game = (Game)object;
		
		coder.encode(Game.VER_BREAK);
		coder.encode(Game.VER_BACK);
		
		coder.encode((int)game.getSize().getWidth());
		coder.encode((int)game.getSize().getHeight());
		
		coder.encode((long)game.getFrameCount());
		
		ArrayList<Entity> entities = game.__getEntities();
		coder.encode(entities.size());
		EntityRestorer entityRestorer = new EntityRestorer();
		for (int i = 0; i < entities.size(); i++) {
			entityRestorer.encode(entities.get(i), coder);
		}
	}
}