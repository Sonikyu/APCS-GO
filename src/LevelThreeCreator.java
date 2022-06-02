public class LevelThreeCreator implements LevelCreator {

	private Player p;

	DoorSwitch doorSwitch1;
	DoorSwitch doorSwitch2;
	DoorSwitch doorSwitch3;
	
	@Override
	public Level createLevel(Player p) {
		this.p = p;
		this.doorSwitch1 = new DoorSwitch(2);
		this.doorSwitch2 = new DoorSwitch(2);
		this.doorSwitch3 = new DoorSwitch(3);
		
		Room strt = this.createStartingRoom();

		Room key = createKeyRoom();
		Room b1 = createBossStage1();
		Room s1 = createSwitchRoomStage1();
		Room b2 = createBossStage2();
		Room s2 = createSwitchRoomStage2();
		Room grs = createGrassRoom();

		Room[][] rooms = {
			{null,  null,	s2,		null},
			{null, 	null,	b2,		grs},
			{key, 	LR(),   strt,	null},
			{null, 	null,	UD(),	null},
			{null, 	null,	b1,		s1},
		};

		// Auto find starting room
		int row = 0;
		int col = 0;
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[0].length; j++) {
				// Double equal works because we aren't checking for equality just the same class
				if (rooms[i][j] == strt) {
					row = i;
					col = j;
					break;
				}
			}
		}

		return new Level(rooms, row, col, p);
	}
	
	private Room createStartingRoom() {
//		Room room = RUD();
		String [] layout  = {
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"####J          |GGGG",
				"               |GGGG",
				"               |GGGG",
				"         S     |GGGG",
				"               |GGGG",
				"               |GGGG",
				"####7          |GGGG",
				"GGGG|----dd----|GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG"
		};
		int[] combination = {1};
		DoorSwitch[] switches = {doorSwitch1};
		SwitchDoor door1 = new SwitchDoor(combination, switches, SwitchDoor.Orientation.HORIZONTAL);
		SwitchDoor door2 = new SwitchDoor(combination, switches, SwitchDoor.Orientation.HORIZONTAL);
		SwitchDoor door3 = new SwitchDoor(combination, switches, SwitchDoor.Orientation.HORIZONTAL);
		SwitchDoor door4 = new SwitchDoor(combination, switches, SwitchDoor.Orientation.HORIZONTAL);
		SwitchDoor door5 = new SwitchDoor(combination, switches, SwitchDoor.Orientation.HORIZONTAL);
		SwitchDoor door6 = new SwitchDoor(combination, switches, SwitchDoor.Orientation.HORIZONTAL);
		SwitchDoor door7 = new SwitchDoor(combination, switches, SwitchDoor.Orientation.HORIZONTAL);
		SwitchDoor door8 = new SwitchDoor(combination, switches, SwitchDoor.Orientation.HORIZONTAL);
		SwitchDoor door9 = new SwitchDoor(combination, switches, SwitchDoor.Orientation.HORIZONTAL);
		SwitchDoor door10 = new SwitchDoor(combination, switches, SwitchDoor.Orientation.HORIZONTAL);

		
		Room room = new Room(layout, p);
		room.placeEntity(door1, 5, 0);
		room.placeEntity(door2, 6, 0);
		room.placeEntity(door3, 7, 0);
		room.placeEntity(door4, 8, 0);
		room.placeEntity(door5, 9, 0);
		room.placeEntity(door6, 10, 0);
		room.placeEntity(door7, 11, 0);
		room.placeEntity(door8, 12, 0);
		room.placeEntity(door9, 13, 0);
		room.placeEntity(door10, 14, 0);
		room.placeEntity(new NPCGuard("images/LevelThreeGetKey.png", 360, 240), 3, 8);
		room.placeEntity(new NPCGuard("images/SwitchActivationNecessary.png", 360, 240), 9, 2);
		
		return room;
	}
	
	private Room createKeyRoom() {
		String [] layout = {
				"P------------------7",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  L",
				"|                   ", // edit this room to make sense
				"|                   ",
				"|--------           ",
				"|                   ",
				"|                   ",
				"|                  P",
				"|                  |",
				"|                  |",
				"|                  |",
				"L__________________J",
		};
		// add tracking enemy protecting key
		Room room = new Room(layout, p);
		TrackingEnemy enemy = new TrackingEnemy(7, 5, 3, 25, 300, 0);
		room.placeEntity(enemy, 5, 3);
		room.placeEntity(new Item(Item.ItemType.KEY), 3, 3);
		room.placeEntity(new Item(Item.ItemType.HEALPOT), 3, 9);
//		room.placeEntity(new Item(Item.ItemType.HEALPOT), 5, 10);
//		room.placeEntity(new Item(Item.ItemType.HEALPOT), 3, 11);
		return room;
		
	}
	
	private Room createBossStage1() {
		String [] layout = {
				"P---J          L---7",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |", // edit this room to make sense
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  L",
				"|                 BB",
				"|                 BB",
				"L___________________",
		};
		// add stage 1 boss
		Room room = new Room(layout, p);
		Boss boss = new Boss();
		room.placeEntity(new BossBattleTracker(boss, "BossBattleMinion", 3), 10, 10);
		room.placeEntity(boss, 2, 10);
		return room;
	}
	
	private Room createSwitchRoomStage1() {
		String [] layout = {
				"P------------------7",
				"|          |       |",
				"|          |       |",
				"|          |       |",
				"|          |       |",
				"|          |       |", // edit this room to make sense
				"|          |       |",
				"|          |       |",
				"|                  |",
				"|                  |",
				"|                  |",
				"J                  |",
				"                   |",
				"                   |",
				"___________________J",
		};
		// add tracking enemy protecting switch for stage 2
		Room room = new Room(layout, p);
		TrackingEnemy enemy = new TrackingEnemy(5, 15, 4, 50, 300, 0);
		room.placeEntity(enemy, 15, 4);
		room.placeEntity(doorSwitch1, 17, 2);
		room.placeEntity(new Item(Item.ItemType.HEALPOT), 3, 3);
		room.placeEntity(new Item(Item.ItemType.HEALPOT), 5, 3);
		room.placeEntity(new Item(Item.ItemType.HEALPOT), 7, 3);
		return room;
	}
	
	private Room createBossStage2() {
		String [] layout = {
				"PJBBL--------------7",
				"| BB               |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  L",
				"|                   ", // edit this room to make sense
				"|                   ",
				"|                  P",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"L---J          L---J",
		};
		// add stage 1 boss
		Room room = new Room(layout, p);
		int[] combination = {1};
		DoorSwitch[] switches = {doorSwitch2};
		SwitchDoor door1 = new SwitchDoor(combination, switches, SwitchDoor.Orientation.VERTICAL);
		SwitchDoor door2 = new SwitchDoor(combination, switches, SwitchDoor.Orientation.VERTICAL);
		Boss boss = new Boss();
		room.placeEntity(new BossBattleTracker(boss, "BossBattleTracker", 10), 6, 2);
		room.placeEntity(boss, 3, 9);
		room.placeEntity(door1, 19, 6);
		room.placeEntity(door2, 19, 7);
		
		return room;
	}
	
	private Room createSwitchRoomStage2() {
		String [] layout = {
				"P------------------7",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |", // edit this room to make sense
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"|                  |",
				"L7  P______________J",
		};
		Room room = new Room(layout, p);
		room.placeEntity(doorSwitch2, 8, 8);
		
		
		return room;
	}
	
	private Room createGrassRoom() {
		String[] layout = {
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"###############-GGGG",
				"                GGGG",
				"                GGGG",
				"                GGGG",
				"                GGGG",
				"                GGGG",
				"###############-GGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
		};
		Room room = new Room(layout, p);
		// make gras room
		return room;
	}
	
	private Room LR(){	
		String [] layout = {		
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
				"GGGGGGGGGGGGGGGGGGGG"
		};
		return new Room(layout, p);
	}

	private Room UD(){
		String [] layout = {
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG"
		};
		Room room = new Room(layout, p);
		room.placeEntity(new NPCGuard("images/BossStage1.png", 360, 240), 10, 10);
//		room.placeEntity(doorSwitch1, 0, 0);
		return room;
	}
		
	private Room RUD(){
		String [] layout  = {
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"####J          |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"####7          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG"
		};
		return new Room(layout, p);
	}
}

