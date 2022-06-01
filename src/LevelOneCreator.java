import javax.swing.ImageIcon;

//AP CS Project
//Alex, Johnny, Ethan, and Uday
//
//This is a template comment that you should paste verbatim above every class.
//Fill out any necessary information:
//
//File: LevelOneCreator.java
//
//Add your name here if you work on this class:
/** @author Johnny, Alex, Uday, Ethan */ 

public class LevelOneCreator implements LevelCreator {	
	
	/**
	 * Creates level one.
	 * @param p The player entity.
	 * @return The created level.
	 */
	public Level createLevel(Player p) {
		Room testingRoom = this.createTestingRoom(p);
		Room startingRoom = this.createStartingRoom(p);
		Room keyRoom = this.createKeyRoom(p);
		Room weaponRoom = this.createWeaponRoom(p);
		Room enemyRoom = this.createEnemyRoom(p);
		Room potionRoom = this.createPotionRoom(p);
		Room trackingEnemyRoom = this.createTrackingEnemyRoom(p);
		Room goalRoom = this.createGoalRoom(p);

		Room[][] rooms = {
				{ null, 		keyRoom, 		null, 		null, 				null 		},
				{ testingRoom, 	startingRoom, 	potionRoom,	trackingEnemyRoom,	goalRoom	},
				{ null, 		weaponRoom, 	enemyRoom,	null, 				null 		},
		};
		
		// Auto find starting room
		int row = 0;
		int col = 0;
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[0].length; j++) {
				// Double equal works because we aren't checking for equality just the same class
				if (rooms[i][j] == startingRoom) {
					row = i;
					col = j;
					break;
				}
			}
		}

		Level level = new Level(rooms, row, col, p);



		return level;
	}		
	
	/**
	 * Creates a specific room.
	 * @param p The player entity.
	 * @return The created room.
	 */
	private Room createTestingRoom(Player p) {
		String[] layout = { // for uday
				"P------------------7",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  L",
				"|                   ",
				"|                  P",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"L__________________J",
		};
		Room room = new Room(layout, p);
		Boss boss = new Boss();
		room.placeEntity(boss, 3, 10);
		room.placeEntity(new BossBattleTracker(boss, "BossBattleMinion", 3), 10, 10);
//		room.placeEntity(new BossBattleTracker(boss, "Infinitetracker", 10), 0, 0);
		return room;
	}
	
	private Room createStartingRoom(Player p) {
		String[] layout = { //House png will be put left of the Spawn tile,
				"P####7      |GGGGGGG",
				"|F   |      |GGGGGGG",
				"|    |      |GGGGGGG",
				"|    |      |GGGGGGG",
				"--J LJ      L------7",
				"B                  |",
				"-----7             |",
				"GGGGG|   S         D",
				"GGGGG|             |",
				"GGGGG|             |",
				"GGGGG|      P------J",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
		};
		Room room = new Room(layout, p);
//		room.placeEntity(new BreakableTile(100), 7, 7);
//		return new Room(layout, p);
		room.placeEntity(new NPCGuard("images/KeyRequiredDialogue.png", 360, 240), 16, 8);
		return room;
	}
	
	private Room createKeyRoom(Player p) {
		String[] layout = { 
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGP------------7GGGG",
				"GG|            |GGGG",
				"GG|            |GGGG",
				"GG|            |GGGG",
				"GGL--7      P--JGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
		};
		Room room = new Room(layout, p);
		room.placeEntity(new Item(Item.ItemType.KEY), 9, 7);
		return room;
	}
	

	private Room createWeaponRoom(Player p) {
		String[] layout = {
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      L-------",
				"GGGGG|              ",
				"GGGGG|              ",
				"GGGGG|              ",
				"GGGGG|              ",
				"GGGGG|              ",
				"GGGGGL______________",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
		};
		Room room = new Room(layout, p);
		if (!p.hasWeapon()) {
			room.placeEntity(new FauxWeaponItem(), 8, 8);
		}
		return room;
	}
	
	private Room createEnemyRoom(Player p) {
		String[] layout = {
				"GGGGGP------7GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"-----J      L------7",
				"                   |",
				"                   |",
				"                   |",
				"                   |",
				"                   |",
				"___________________J",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
		};
		Room room = new Room(layout, p);
		room.placeEntity(new Item(Item.ItemType.HEALPOT), 17, 9);
		MoveOnlyEnemy enemy = new MoveOnlyEnemy(0, Tile.HEIGHT * 15, 5, 2, 2);
		room.placeEntity(enemy, 8, 3);
		room.placeEntity(new NPCGuard("images/FirstEnemyDialogue.png", 360, 240), 3, 10);
		return room;
	}
	
	private Room createPotionRoom(Player p) {
		String[] layout = { 
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"P-------------------",
				"|                   ",
				"J                   ",
				"                    ",
				"7                   ",
				"|                   ",
				"L-------------------",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
		};
		Room room = new Room(layout, p);
		room.placeEntity(new Item(Item.ItemType.SPEEDPOT), 8, 6);
		return room;
	}
	
	private Room createTrackingEnemyRoom(Player p) {
		String[] layout = { 
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"--------------------",
				"                    ",
				"                    ",
				"     P--------7     ",
				"     |        |     ",
				"     |        |     ",
				"#7   |        |   P#",
				"G|   L--------J   |G",
				"G|                |G",
				"G|                |G",
				"G|________________JG",
		};
		Room room = new Room(layout, p);

		TrackingEnemy enemy = new TrackingEnemy(14, 9, 9, 0, 20, 100);
		room.placeEntity(enemy, 9, 9);

//		room.placeEntity(new Item(Item.ItemType.HEALPOT), 6, 6);
		
		room.placeEntity(new NPCGuard("images/TrackingEnemyDialogue.png", 360, 240), 3, 13);

		return room;
	}
	
	private Room createGoalRoom(Player p) {
		String[] layout = { 
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"----------7GGGGGGGGG",
				"  |       |GGGGGGGGG",
				"  |       |GGGGGGGGG",
				"  |   |   |GGGGGGGGG",
				"      | + |GGGGGGGGG",
				"      |   |GGGGGGGGG",
				"----------JGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
		};
		Room room = new Room(layout, p);
		room.placeEntity(new NPCGuard("images/NextLevelDialogue.png", 360, 240), 4, 9);
		return room;
	}
}