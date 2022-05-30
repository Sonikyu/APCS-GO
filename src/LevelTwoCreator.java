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
	private Player p;
	public Level createLevel(Player p) {
		this.p = p;
		/*
		MoveOnlyEnemy enemy1 = new MoveOnlyEnemy(0, 100, 10);
		TrackingEnemy enemy2 = new TrackingEnemy(200, 450, 450, 10, 300, 150, 3, 0);

		DoorSwitch switch1 = new DoorSwitch(2);
		DoorSwitch switch2 = new DoorSwitch(3);
		DoorSwitch switch3 = new DoorSwitch(2);
		DoorSwitch[] switches = {switch1, switch2, switch3};

		int[] combination = {1,2,1};
		SwitchDoor eDoor1 = new SwitchDoor(combination, switches);
		*/
		Room[][] rooms = {
			{L(),   UL(),	null,	null,	null,	null,	null},
			{U(), 	UD(),	U(),	UR(),	LR(),	R(),	U()},
			{UD(), 	UD(),   DR(),	RUD(),	UR(),	LR(),	RUD()},
			{DR(), 	DLR(),	ULR(),	ULDR(),	RUD(),	L(),	RUD()},
			{null, 	UR(),	RUD(),	UD(),	DR(),	UL(),	UD()},
			{L(), 	DL(),	UD(),	UD(),	null,	UD(),	UD()},
			{null, 	null,	D(),	D(),	L(),	DL(),	D()},
		};

		// Auto find starting room
		Room startingRoom = rooms[3][3];
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
				"####|          |####",
				"                    ",
				"                    ",
				"         S          ",
				"                    ",
				"                    ",
				"####|          |####",
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
				"####|          |####",
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
				"GGGG|          |####",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|          |####",
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
				"####|          |####",
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
				"####|          |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"####|          |GGGG",
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
				"GGGG|          |####",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|###############",
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
				"####|          |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"###############|GGGG",
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
				"GGGG|###############",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|          #####",
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
				"###############|GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"####|          |GGGG",
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
				"###############|GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"               |GGGG",
				"###############|GGGG",
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
				"GGGG############GGGG",
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
				"GGGG|##########|GGGG",
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
				"GGGG################",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|               ",
				"GGGG|###############",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG",
				"GGGGGGGGGGGGGGGGGGGG"
		};

		return new Room(layout, p);
	}

}