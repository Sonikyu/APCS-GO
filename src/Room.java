//Johnny made this

import restore.Coder;
import restore.Encodable;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Room implements Encodable {
	
	public static int HEIGHT = 20;
	public static int WIDTH = 20;
	public static int TILE_SIZE = 30;
	
	private ArrayList<Entity> entities;
	private StaticTile[][] tiles;
	private Player player;
	private Heart[] healthBar;
	
	public Room(ArrayList<Entity> entities, StaticTile[][] tiles, Player player) {
		this.tiles = new StaticTile[HEIGHT][WIDTH];
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				this.tiles[i][j] = new StaticTile(StaticTile.Material.FLOOR);
				this.tiles[i][j].setPosition(j * TILE_SIZE, i * TILE_SIZE);
			}
		}
		this.entities = entities;
		this.player = player;
		healthBar = new Heart[10];
		for (int i = 0; i < healthBar.length; i++) {
			Heart h = new Heart();
			h.setPosition(10 + i * h.getWidth(), 10);
			healthBar[i] = h;
		}

	}
	
	public Room(Coder coder) {
		tiles = new StaticTile[HEIGHT][WIDTH];
		this.entities = new ArrayList<Entity>(); 
		this.player = new Player(coder);
		int entitySize = coder.decodeInt();
		for (int i = 0; i < entitySize; i++) {
			entities.add(EntityDecoder.decode(coder));
		}
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				tiles[i][j] = new StaticTile(coder);
			}
		}
		healthBar = new Heart[10];
		for (int i = 0; i < healthBar.length; i++) {
			Heart h = new Heart();
			h.setPosition(0 + i * h.getWidth(), 10);
			healthBar[i] = h;
		}
	}
	
	// TESTING CODE DELETE LATER
	
	public Room(Room otherRoom) {
		this.tiles = otherRoom.tiles;
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				this.tiles[i][j] = new StaticTile(StaticTile.Material.FLOOR);
				this.tiles[i][j].setPosition(j * TILE_SIZE, i * TILE_SIZE);

			}
		}
		this.entities = otherRoom.entities;
		this.player = otherRoom.player;
		healthBar = new Heart[10];
		for (int i = 0; i < healthBar.length; i++) {
			Heart h = new Heart();
			h.setPosition(10 + i * h.getWidth(), 10);
			healthBar[i] = h;
		}
	}
	
	// END OF TESTING CODE

	@Override
	public void encode(Coder coder) {
		coder.encode(player);
		coder.encode(entities.size());
		for (Entity entity : entities) {
			coder.encode(entity);
		}
		for (StaticTile[] excellentRowOfTiles : tiles) {
			for (StaticTile theTileInTheColumnOfTheExcellentRowOfTiles : excellentRowOfTiles) {
				coder.encode(theTileInTheColumnOfTheExcellentRowOfTiles);
			}
		}
	}
	

	
	public void paint(Graphics2D g) {	
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				StaticTile tile = tiles[i][j];
				if (tile.isVisible()) {
					tile.paint(g);
				}
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.isVisible()) {
				entity.paint(g);
			}	
		}
		player.paint(g);
		for (int i = 0; i < healthBar.length; i++) {
			Heart heart = healthBar[i];
			if (heart.isVisible()) {
				healthBar[i].paint(g);
			}
		}
	}
	
	public void loadRoom() {
		for (Entity entity : entities) {
			entity.show();
		}
		for (Heart heart : healthBar) {
			heart.show();
		}
	}
	
	public void unloadRoom() {
		for (Entity entity : entities) {
			entity.hide();
		}
		for (Heart heart : healthBar) {
			heart.hide();
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return The entity with the given id or null if none exists.
	 */
	public Entity getEntityByID(String id) {
		for (Entity e: entities) {
			if (e.getID().equals(id)) {
				return e;
			}
		}
		return null;
	}
	
	public void updateHearts() {
		int health = player.getHeartCount();
		for (int i = 0; i < healthBar.length; i++) {
			if (health >= 2) {
				healthBar[i].setImageAtIndex(2);
				health -= 2;
			}
			else if (health == 1) {
				healthBar[i].setImageAtIndex(1);
				health--;
			}
			else {
				healthBar[i].setImageAtIndex(0);
			}
		}
		
	}
	
	/**
	 * For example, to get the player, use: game.getEntitiesByType(Player.TYPE).get(0)
	 * @param type
	 * @return A list of visible entities of the given type.
	 */
	public ArrayList<Entity> getEntitiesByType(String type) {
		ArrayList<Entity> entitiesByType = new ArrayList<Entity>();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.isVisible() && entity.getType().equals(type)) {
				entitiesByType.add(entity);
			}
		}
		return entitiesByType;
	}
	
	public void addEntity(Entity entity) {
		System.out.println("entities[" + entities.size() + "] = " + entity);
		entities.add(entity);
	}
	
	public void removeEntityByID(String id) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getID().equals(id)) {
				entities.remove(i);
				break;
			}
		}
	}
	
	public ArrayList<Entity> getVisibleEntities() {
		ArrayList<Entity> visibleEntities = new ArrayList<Entity>();
		visibleEntities.add(player);
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.isVisible()) {
				visibleEntities.add(entity);
			}
		}
		return visibleEntities;
	}

	public void cycle(Level level, Game.GameInfo info) {
		// Testing code
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.isVisible()) {
				entity.cycle(level, info);
			}
		}
		player.cycle(level, info);
		
		// Cycle for Hearts 
		updateHearts();
		for (int i = 0; i < healthBar.length; i++) {
			healthBar[i].cycle(level, info);
		}
		//debugger.print("Game Loop");
	}
}

	
	

