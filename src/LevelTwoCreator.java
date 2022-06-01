import javax.swing.text.html.parser.DTD;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: LevelTwoCreator.java
//
// Add your name here if you work on this class:
/** @author Johnny, Alex, Uday, Ethan */ 



class LevelTwoCreator implements LevelCreator {
	DoorSwitch doorSwitch1;
	DoorSwitch doorSwitch2;
	DoorSwitch doorSwitch3;

	private Player p;
	public Level createLevel(Player p) {
		this.p = p;
		this.doorSwitch1 = new DoorSwitch(3);
		this.doorSwitch2 = new DoorSwitch(3);
		this.doorSwitch3 = new DoorSwitch(3);
		
		Room strt = createStartingRoom();

		Room sr1 = createTLSwitchRoom();
		Room sr2 = createBSwitchRoom();
		Room sr3 = createTRSwitchRoom();
		Room drR = createDoorRoom();
		Room lvR = createLevelRoom();

		Room[][] rooms = {
			{L(),   UL(),	null,	null,	null,	null,	null},
			{sr1, 	UD(),	U(),	UR(),	LR(),	sr3,	U()},
			{UD(), 	UD(),   DR(),	RUD(),	UR(),	LR(),	RUD()},
			{DR(), 	DLR(),	ULR(),	strt,	RUD(),	L(),	RUD()},
			{null, 	UR(),	RUD(),	UD(),	DR(),	UL(),	UD()},
			{L(), 	DL(),	UD(),	drR,	null,	UD(),	UD()},
			{null, 	null,	D(),	lvR,	sr2,	DL(),	D()},
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
	
	private Room createTLSwitchRoom() {
		Room room = U();
		room.placeEntity(doorSwitch1, 9, 6);
		return room;
	}
	
	private Room createBSwitchRoom() {
		Room room = L();
		room.placeEntity(doorSwitch2, 6, 7);
		return room;
	}
	
	private Room createTRSwitchRoom() {
		Room room = R();
		room.placeEntity(doorSwitch3, 13, 7);
		return room;
	}
	
	private Room createDoorRoom() {
		String[] layout = {
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|----  ----|GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG"
		};
		Room room = new Room(layout, p);
		int[] combination = { 0, 2, 1 };
		DoorSwitch[] switches = { doorSwitch1, doorSwitch2, doorSwitch3 };
		SwitchDoor door1 = new SwitchDoor(combination, switches);
		SwitchDoor door2 = new SwitchDoor(combination, switches);
		room.placeEntity(door1, 9, 7);
		room.placeEntity(door2, 10, 7);
		return room;
	}
	
	private Room createLevelRoom() {
		String[] layout = {
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|    +     |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGGL##########JGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG"
		};
		Room room = new Room(layout, p);
		room.placeEntity(new NPCGuard("images/LevelTwoFinishedDialogue.png", 360, 240), 5, 8);
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
		Room room = new Room(layout, p);
		MoveOnlyEnemy enemy = new MoveOnlyEnemy(0, Tile.HEIGHT * 15, 5, 2, 2);
		room.placeEntity(enemy, 8, 3);
		return room;
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

	private Room createStartingRoom() {
		Room room = ULDR();
		room.placeEntity(new NPCGuard("images/LevelTwoStartedDialogue.png", 360, 240), 9, 6);
		return room;
	}
	private Room ULDR(){
		String [] layout = {
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"####J          L####",
				"                    ",
				"                    ",
				"         S          ",
				"                    ",
				"                    ",
				"####7          P####",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG"
		};
		
		Room room = new Room(layout, p);

		return room;
	}

	private Room DLR(){
		String [] layout = {
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"####J          P####",
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
	
	private Room LUD(){
		String [] layout = {
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          L####",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|          P####",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG"
		};
		return new Room(layout, p);
	}
	private Room ULR(){
		String [] layout =  {
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
				"####7          P####",
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

	private Room DR(){
		String [] layout = {
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          L####",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGGL###############",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG"
		};
		return new Room(layout, p);
	}

	private Room DL(){
		String [] layout = {
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
				"###############JGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG"
		};
		return new Room(layout, p);
	}
		
	private Room UR(){
		String [] layout = {
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGP###############",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|          P####",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG"
		};	
		return new Room(layout, p);
	}

	private Room UL(){
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
				"####7          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG",
				"GGGG|          |GGGG"
		};	
		return new Room(layout, p);
	}

	private Room R(){
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

		return new Room(layout, p);
	}

	private Room U(){
		String[] layout = {
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGP##########7GGGG",
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


	private Room D(){
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
				"GGGGL##########JGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG"
		};
		return new Room(layout, p);
	}

	private Room L(){
		String [] layout = {
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGP###############",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGGL###############",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG"
		};

		return new Room(layout, p);
	}

}