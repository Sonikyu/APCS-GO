
import restore.Coder;
import restore.Encodable;

import java.awt.Graphics2D;
import java.util.ArrayList;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: Room.java
//
//Add your name here if you work on this class:
/** @author Johnny, Ethan */ 

/**
 * Represents a single room in a level. Contains a 2D-array of tiles.
 */
public class Room implements Encodable {
	
	public static int HEIGHT = 15;
	public static int WIDTH = 20;
	
	private ArrayList<Entity> entities;
	private Tile[][] tiles;
	private Player player;


	
	public Room(String[] roomString, Player player) {
		this.tiles = new Tile[HEIGHT][WIDTH];
		this.entities = new ArrayList<Entity>();
		// TODO: need rooms to take an arraylist of entities
		this.player = player;
		
		tileInit(roomString);
		setPlayerPosition();
	}
	
	public Room(ArrayList<Entity> entities, Tile[][] tiles, Player player) {
		this.tiles = new Tile[HEIGHT][WIDTH];
		
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				this.tiles[i][j] = new Tile(Tile.Material.FLOOR);
				this.tiles[i][j].setPosition(j * Tile.WIDTH, i * Tile.HEIGHT);
			}
		}

		this.entities = entities;
		this.player = player;

		setPlayerPosition();
	}
	
	public Room(Coder coder) {
		tiles = new Tile[HEIGHT][WIDTH];
		this.entities = new ArrayList<Entity>(); 
		this.player = new Player(coder);
		int entitySize = coder.decodeInt();
		for (int i = 0; i < entitySize; i++) {
			entities.add(EntityDecoder.decode(coder));
		}
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				tiles[i][j] = new Tile(coder);
			}
		}

		setPlayerPosition();
	}
	
	private void tileInit(String[] roomString) {
		for (int i = 0; i < roomString.length; i++) {
			String row = roomString[i];
			for (int j = 0; j < row.length(); j++) {
				switch (row.charAt(j)) {
		
				case ' ':
					this.tiles[i][j] = new Tile(Tile.Material.FLOOR);
					break;
				case 'D':
					this.tiles[i][j] = new DoorTile();
					break;
				case 'S':
					this.tiles[i][j] = new Tile(Tile.Material.START);
					break;
				case '#':
				case '-':
				case '_':
					this.tiles[i][j] = new Tile(Tile.Material.WALL);
					tiles[i][j].setImageAtIndex(0);
					break;
				case '|':
					this.tiles[i][j] = new Tile(Tile.Material.WALL);
					tiles[i][j].setImageAtIndex(1);
					break;
				case 'P':
					this.tiles[i][j] = new Tile(Tile.Material.WALL);
					tiles[i][j].setImageAtIndex(3);
					break;
				case 'L':
					this.tiles[i][j] = new Tile(Tile.Material.WALL);
					tiles[i][j].setImageAtIndex(5);
					break;
				case '7':
					this.tiles[i][j] = new Tile(Tile.Material.WALL);
					tiles[i][j].setImageAtIndex(2);
					break;
				case 'J':
					this.tiles[i][j] = new Tile(Tile.Material.WALL);
					tiles[i][j].setImageAtIndex(4);
					break;
				case '+':
					this.tiles[i][j] = new Tile(Tile.Material.LEVEL_UP);
					break;
				case 'G':
					this.tiles[i][j] = new Tile(Tile.Material.GRASS);
					break;
				case 'B':
					this.tiles[i][j] = new Tile(Tile.Material.FLOOR);
					placeEntity(new BreakableTile(100), j, i);
					break;
				default:
					this.tiles[i][j] = new Tile(Tile.Material.FLOOR);
				}
				this.tiles[i][j].setPosition(j * tiles[i][j].getHeight(), i * tiles[i][j].getWidth());
			}
		}
	}
	
	public void setPlayerPosition() {
		System.out.println("PlayerPostion called");
		for (Tile[] row: tiles) {
			for (Tile tile: row) {
				if (tile != null && tile.getMaterial() == Tile.Material.START) {
					Debugger.main.print("Start tile found");
					player.setPosition(tile.getX(), tile.getY());
					System.out.println("Tile Found: X: " + tile.getX() + "| Y: " + tile.getY());
				}
			}
		}
	}
	

	
	@Override
	public void encode(Coder coder) {
		coder.encode(player);
		coder.encode(entities.size());
		for (Entity entity : entities) {
			coder.encode(entity);
		}
		for (Tile[] excellentRowOfTiles : tiles) {
			for (Tile theTileInTheColumnOfTheExcellentRowOfTiles : excellentRowOfTiles) {
				coder.encode(theTileInTheColumnOfTheExcellentRowOfTiles);
			}
		}
	}
	

	
	public void paint(Graphics2D g) {	
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				Tile tile = tiles[i][j];
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
		
	}
	
	public void loadRoom() {
		for (Tile[] tileRow : tiles) {
			for (Tile tile : tileRow) {
				tile.show();
			}
		}
		for (Entity entity : entities) {
			entity.show();
		}
	}
	
	public void unloadRoom() {
		for (Tile[] tileRow : tiles) {
			for (Tile tile : tileRow) {
				tile.hide();
			}
		}
		for (Entity entity : entities) {
			entity.hide();
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
	
	
	
	/**
	 * For example, to get the player, use: game.getEntitiesByType(Player.TYPE).get(0)
	 * @param type
	 * @return A list of visible entities of the given type.
	 */
	public ArrayList<Entity> getEntitiesByType(String type) {
		ArrayList<Entity> entitiesByType = new ArrayList<Entity>();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.isVisible() && entity.isOfType(type)) {
				entitiesByType.add(entity);
			}
		}
		return entitiesByType;
	}
	
	public void addEntity(Entity entity) {
		System.out.println("entities[" + entities.size() + "] = " + entity);
		entities.add(entity);
	}
	
	public void placeEntity(Entity entity, int col, int row) {
		entity.setPosition(col * Tile.WIDTH, row * Tile.HEIGHT);
		addEntity(entity);
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
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				Tile tile = tiles[i][j];
				if (tile != null && tile.isVisible()) {
					visibleEntities.add(tile);
				}
			}
		}
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
		
		//debugger.print("Game Loop");
	}
}

	
	
