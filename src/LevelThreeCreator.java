public class LevelThreeCreator implements LevelCreator {

	private Player p;

	DoorSwitch doorSwitch1;
	DoorSwitch doorSwitch2;
	DoorSwitch doorSwitch3;
	
	@Override
	public Level createLevel(Player p) {
		this.p = p;
		this.doorSwitch1 = new DoorSwitch(3);
		this.doorSwitch2 = new DoorSwitch(3);
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
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG"
		};
		Room room = new Room(layout, p);
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
				"|-------            ",
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
		TrackingEnemy enemy = new TrackingEnemy(7, 5, 3, 50, 300, 0);
		room.placeEntity(enemy, 5, 3);
		room.placeEntity(new Item(Item.ItemType.KEY), 3, 3);
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
		return new Room(layout, p);
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
		room.placeEntity(new Item(Item.ItemType.KEY), 17, 2);
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
				"|                   ",
				"|                   ",
				"|                   ",
				"|                  P",
				"|                  |",
				"|                  |",
				"L---J          L---J",
		};
		// add stage 1 boss
		return new Room(layout, p);
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
		// add 2 switch for grass
		return new Room(layout, p);
	}
	
	private Room createGrassRoom() {
		String[] layout = {
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"###############7GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"###############JGGGG",
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
		return new Room(layout, p);
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
