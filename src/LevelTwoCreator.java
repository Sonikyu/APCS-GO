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
		
		Room strt = ULDR();

		Room sr1 = createTLSwitchRoom();
		Room sr2 = createBSwitchRoom();
		Room sr3 = createTRSwitchRoom();

		Room[][] rooms = {
			{L(),   UL(),	null,	null,	null,	null,	null},
			{sr1, 	UD(),	U(),	UR(),	LR(),	sr3,	U()},
			{UD(), 	UD(),   DR(),	RUD(),	UR(),	LR(),	RUD()},
			{DR(), 	DLR(),	ULR(),	strt,	RUD(),	L(),	RUD()},
			{null, 	UR(),	RUD(),	UD(),	DR(),	UL(),	UD()},
			{L(), 	DL(),	UD(),	UD(),	null,	UD(),	UD()},
			{null, 	null,	D(),	D(),	sr2,	DL(),	D()},
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

		Level level = new Level(rooms, row, col, p);
		

		return level;
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
		return new Room(layout, p);
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