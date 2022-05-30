import javax.swing.ImageIcon;

public class LevelOneCreator implements LevelCreator {	
	public Level createLevel(Player p) {
		Room testingRoom = this.createTestingRoom(p);
		Room startingRoom = this.createStartingRoom(p);
		Room keyRoom = this.createKeyRoom(p);
		Room weaponRoom = this.createWeaponRoom(p);
		Room enemyRoom = this.creatEnemyRoom(p);
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
		return new Room(layout, p);
	}
	
	private Room createStartingRoom(Player p) {
		String[] layout = { //House png will be put left of the Spawn tile,
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"-----J      L------7",
				"                   |",
				"-----7             L",
				"GGGGG|   S         D",
				"GGGGG|             P",
				"GGGGG|             |",
				"GGGGG|      P------J",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
		};
		return new Room(layout, p);
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
		// add faux weapon "item"
		return room;
	}
	
	private Room creatEnemyRoom(Player p) {
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
		MoveOnlyEnemy enemy = new MoveOnlyEnemy(0, Tile.HEIGHT * -10, 5);
		room.placeEntity(enemy, 8, 9);
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

		TrackingEnemy enemy = new TrackingEnemy(7, 9, 9, 0, 20, 100);
		room.placeEntity(enemy, 9, 9);

//		room.placeEntity(new Item(Item.ItemType.HEALPOT), 6, 6);
		ImageIcon icon = new ImageIcon("images/NPCDialogue1.png");
		room.placeEntity(new NPCGuard(icon), 6, 6);

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
				"  |   |   |GGGGGGGGG",
				"  |   |   |GGGGGGGGG",
				"  |   | + |GGGGGGGGG",
				"      |   |GGGGGGGGG",
				"----------JGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
		};
		return new Room(layout, p);
	}
}