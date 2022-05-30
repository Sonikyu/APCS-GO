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
	public Level createLevel(Player p) {
		MoveOnlyEnemy enemy1 = new MoveOnlyEnemy(0, 100, 10);
		TrackingEnemy enemy2 = new TrackingEnemy(200, 450, 450, 10, 300, 150, 3, 0);

		DoorSwitch switch1 = new DoorSwitch();
		DoorSwitch switch2 = new DoorSwitch();
		DoorSwitch switch3 = new DoorSwitch();
		DoorSwitch[] switches = {switch1, switch2, switch3};

		int[] combination = {1,1,1};
		SwitchDoor eDoor1 = new SwitchDoor(combination, switches);



		String[] room1Layout = {
				"P###################",
				"|                   ",
				"|  S                ",
				"|                   ",
				"|                   ",
				"|                   ",
				"|              #####",
				"|                   ",
				"|  +                ",
				"|                   ",
				"|                   ",
				"|                   ",
				"|                   ",
				"|##                 ",
				"L###################"
		};
		String[] room2Layout = {
				"###################7",
				"                   |",
				"                   |",
				"                   |",
				"                   |",
				"                   |",
				"              #####|",
				"                   |",
				"                   |",
				"                   |",
				"                   |",
				"                   |",
				"                   |",
				"                 ##|",
				"###################J"
		};
		Room room1 = new Room(room1Layout, p);
		Room room2 = new Room(room2Layout, p);
		room1.placeEntity(enemy1, 16, 1);
		room1.placeEntity(enemy2, 10, 13);
		room2.placeEntity(switch1, 18, 1);
		room2.placeEntity(switch2, 18, 13);
		room1.placeEntity(switch3, 5, 5);
		room2.placeEntity(eDoor1, 2, 2);



		Room[][] rooms = {{ room1, room2}};



		Item key1 = new Item(Item.ItemType.KEY);
		Item healPot1 = new Item(Item.ItemType.HEALPOT);
		key1.setPosition(200, 200);
		healPot1.setPosition(260, 200);
		room1.addEntity(key1);
		room1.addEntity(healPot1);

		room1.setPlayerPosition();
		Level level = new Level(rooms, 0, 0, p);

		return level;
	}		
}