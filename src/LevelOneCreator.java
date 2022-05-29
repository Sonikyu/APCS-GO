public class LevelOneCreator implements LevelCreator {
	public Room createTestingRoom(Player p) {
		String[] layout = { //House png will be put left of the Spawn tile,
				"P##################7",
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
				"L##################J",
		};
		return new Room(layout, p);
	}
	
	public Room createStartingRoom(Player p) {
		String[] layout = { //House png will be put left of the Spawn tile,
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"#####J      L#######",
				"                   |",
				"#####7             |",
				"GGGGG|   S         D",
				"GGGGG|             |",
				"GGGGG|             |",
				"GGGGG|      P#######",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
		};
		return new Room(layout, p);
	}

	public Level createLevel(Player p) {
		MoveOnlyEnemy enemy1 = new MoveOnlyEnemy(0, 100, 10);
		TrackingEnemy enemy2 = new TrackingEnemy(200, 450, 450, 10, 300, 150, 3);

		DoorSwitch switch1 = new DoorSwitch();
		DoorSwitch switch2 = new DoorSwitch();
		StaticEntity sta = new StaticEntity(); // testing code for wall don't remove yet
		DoorSwitch switch3 = new DoorSwitch();
		DoorSwitch[] switches = {switch1, switch2, switch3};

		int[] combination = {1,1,1};
		SwitchDoor eDoor1= new SwitchDoor(combination, switches);



		String[] weaponRoomLayout = { //House png will be put left of the Spawn tile,
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGP##J      L##7GGGG",
				"GG|            |GGGG",
				"GG|            |GGGG",
				"GG|            |GGGG",
				"GG|            |GGGG",
				"GGL############JGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
		};

		String[] keyRoomLayout = { //House png will be put left of the Spawn tile,
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGP############7GGGG",
				"GG|            |GGGG",
				"GG|            |GGGG",
				"GG|            |GGGG",
				"GGL##7      P##JGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
				"GGGGG|      |GGGGGGG",
		};

		String[] potionRoomLayout = { 
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"####################",
				"                    ",
				"                    ",
				"                    ",
				"                    ",
				"                    ",
				"####################",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
		};

		String[] trackingEnemyRoomLayout = { 
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"####################",
				"                    ",
				"                    ",
				"     P########7     ",
				"     #        #     ",
				"     #        #     ",
				"#7   #        #   P#",
				"G#   L########J   #G",
				"G#                #G",
				"G#                #G",
				"G##################G",
		};





		//Init Rooms
		Room startingRoom = this.createStartingRoom(p);
		Room weaponRoom = new Room(weaponRoomLayout, p);
		Room potionRoom = new Room(potionRoomLayout, p);
		Room trackingEnemyRoom = new Room(trackingEnemyRoomLayout, p);
		Room keyRoom = new Room(keyRoomLayout, p);

		//Make and Place Entities

		//Potion Room, Wall of Enemies, Wall of Potions
		//Tracking Enemy Room, Tracking Enemy in box
		//potionRoom.placeEntity(potion1, row, col);

		//Make Level

		Room[][] rooms = {{ null, keyRoom, null, null, null},
				{ createTestingRoom(p), startingRoom, potionRoom, trackingEnemyRoom/*, goal*/} ,
				{ null, weaponRoom, null, null, null},
				{ }};




		Item healPot1 = new Item(Item.ItemType.HEALPOT);
		healPot1.setPosition(260, 200);
		potionRoom.placeEntity(new Item(Item.ItemType.HEALPOT), 6, 6);
		keyRoom.placeEntity(new Item(Item.ItemType.KEY), 7, 9);

		Level level = new Level(rooms, 1, 1, p);
		return level;
	}		
}