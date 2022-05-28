import restore.Coder;
import restore.Encodable;

public class StaticEntity extends Entity implements Encodable {
	public StaticEntity() {
		super("WallTile", 0, "Wall2.png");
	}
	
	public StaticEntity(Coder coder) {
		super(coder);
	}

	public void encode(Coder coder) {
		super.encode(coder);
	}
	
	@Override
	public void cycle(Level level, Game.GameInfo info) {}

}
