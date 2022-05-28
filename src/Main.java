import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.Scanner;
import restore.Coder;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Main.java
//
// Add your name here if you work on this class:
/** @author Ethan Alex Uday Johnny */ 
public class Main {
	public static void main(String[] args) {
		Game game = null;

		// Make JFrame
		JFrame frame = new JFrame();
		
		// See if we can load a game
		Scanner in = new Scanner(System.in);
		System.out.print("Enter game load string. If none, press enter with empty string: ");
		String line = in.nextLine();	
		in.close();
		
		if (!line.isEmpty()) {
			Coder coder = new Coder(line);
			game = new Game(coder);
			if (coder.hasError()) {
				System.err.println("Invalid game string: " + coder.getError());
				return;
			}
		} else {
			
			// Window size
			Dimension size = new Dimension(800, 600);

			// Initialize game
			Player p = new Player();
			
			class LevelOneCreator implements LevelCreator {
				public Level createLevel() {
					
					MoveOnlyEnemy enemy1 = new MoveOnlyEnemy(0, 100, 10);
					TrackingEnemy enemy2 = new TrackingEnemy(200, 450, 450, 10, 300, 150, 3);
					
					DoorSwitch switch1 = new DoorSwitch();
					DoorSwitch switch2 = new DoorSwitch();
					DoorSwitch switch3 = new DoorSwitch();
					DoorSwitch[] switches = {switch1, switch2, switch3};
					
					int[] combination = {1,1,1};
					SwitchDoor eDoor1= new SwitchDoor(combination, switches);
					
					
					String[] room1Layout = {
						"P###################",
						"|                   ",
						"| S                 ",
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
						"|                   ",
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
							"                   |",
							"###################J"
						};
					Room room1 = new Room(room1Layout, p);
					Room room2 = new Room(room2Layout, p);
					room1.placeEntity(enemy1, 16, 1);
					room1.placeEntity(enemy2, 10, 13);
					room2.placeEntity(switch1, 18, 1);
					room2.placeEntity(switch2, 18, 13);
					room2.placeEntity(switch3, 5, 5);
					room2.placeEntity(eDoor1, 2, 2);
					
					
					Room[][] rooms = { { room1, room2} };
					
					
						
					Item key1 = new Item(Item.ItemType.KEY);
					Item healPot1 = new Item(Item.ItemType.HEALPOT);
					key1.setPosition(200, 200);
					healPot1.setPosition(260, 200);
					room1.addEntity(key1);
					room1.addEntity(healPot1);
					
					Level level = new Level(rooms, 0, 0, p);
					return level;
				}		
			}
			class LevelTwoCreator implements LevelCreator {
				public Level createLevel() {
					Debugger.main.print("Apple");
					
					MoveOnlyEnemy enemy1 = new MoveOnlyEnemy(0, 100, 10);
					TrackingEnemy enemy2 = new TrackingEnemy(200, 450, 450, 10, 300, 150, 3);
					
					DoorSwitch switch1 = new DoorSwitch();
					DoorSwitch switch2 = new DoorSwitch();
					DoorSwitch switch3 = new DoorSwitch();
					DoorSwitch[] switches = {switch1, switch2, switch3};
					
					int[] combination = {1,1,1};
					SwitchDoor eDoor1= new SwitchDoor(combination, switches);
					
					
					String[] room1Layout = {
						"P###################",
						"|                   ",
						"| S                 ",
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
					room2.placeEntity(switch3, 5, 5);
					room2.placeEntity(eDoor1, 2, 2);
					
					
					Room[][] rooms = { { room1, room2} };
					
					
						
					Item key1 = new Item(Item.ItemType.KEY);
					Item healPot1 = new Item(Item.ItemType.HEALPOT);
					key1.setPosition(200, 200);
					healPot1.setPosition(260, 200);
					room1.addEntity(key1);
					room1.addEntity(healPot1);
					
					Level level = new Level(rooms, 0, 0, p);
					return level;
				}		
			}
			/*
			StaticTile wall = new StaticTile(StaticTile.Material.WALL);
			StaticTile wall1 = new StaticTile(StaticTile.Material.WALL);
			StaticTile wall2 = new StaticTile(StaticTile.Material.WALL);
			StaticTile wall3 = new StaticTile(StaticTile.Material.WALL);
			StaticTile wall4 = new StaticTile(StaticTile.Material.WALL);
            StaticTile wall5 = new StaticTile(StaticTile.Material.WALL);
            StaticTile wall6 = new StaticTile(StaticTile.Material.WALL);
            StaticTile wall7 = new StaticTile(StaticTile.Material.WALL);
            StaticTile wall8 = new StaticTile(StaticTile.Material.WALL);
            StaticTile wall9 = new StaticTile(StaticTile.Material.WALL);
            StaticTile wall10 = new StaticTile(StaticTile.Material.WALL);
            StaticTile wall11 = new StaticTile(StaticTile.Material.WALL);
			StaticTile wall12 = new StaticTile(StaticTile.Material.WALL);

			Item key1 = new Item(Item.Object.KEY);
			Item key2 = new Item(Item.Object.KEY);
//			Item key = new Item();

            wall.setPosition(100, 100);
            wall1.setPosition(70, 100);
            wall2.setPosition(40,100);
            wall3.setPosition(10,100);


            wall4.setPosition(450, 350);
            wall5.setPosition(420, 350);
            wall6.setPosition(390, 350);
            wall7.setPosition(480, 350);
            wall8.setPosition(510, 350);
            wall9.setPosition(540, 350);
            wall10.setPosition(570, 350);
            wall11.setPosition(360, 350);
			wall12.setPosition(0, 100);

			key1.setPosition(200, 200);
			key1.setPosition(260, 200);


			

			Room room1 = new Room(new ArrayList<Entity>(), new StaticTile[20][20], p);
			Room room2 = new Room(new ArrayList<Entity>(), new StaticTile[20][20], p);
			room1.addEntity(wall);
			room1.addEntity(wall1);
			room1.addEntity(wall2);
			room1.addEntity(wall3);
			room1.addEntity(wall4);
			room1.addEntity(wall5);
			room1.addEntity(wall6);
			room1.addEntity(wall7);
			room1.addEntity(wall8);
			room1.addEntity(wall9);
			room1.addEntity(wall10);
			room1.addEntity(wall11);
			room1.addEntity(wall12);
			
			room1.addEntity(key1);
			room1.addEntity(key2);

			room1.addEntity(enemy1);
			room1.addEntity(enemy2);
			Room[][] rooms = new Room[1][2];
			for (int r = 0; r < rooms.length; r++) {
				for (int c = 0; c < rooms[0].length; c++) {
					rooms[r][c] = new Room(room1);
				}
			}
			rooms[0][0] = room2;
			Level level = new Level(rooms, 0, 0, p);
			*/
		
		LevelCreator[] levels = {new LevelOneCreator(), new LevelTwoCreator()};
		game = new Game(size, levels);

		// Sets up frame with GameView
		GameView gameView = new GameView(game);
		frame.add(gameView);
		
		// Setup input controller
		GameBox gameBox = new GameBox(game);
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_D) {
					String msg = "Debug " + (!Debugger.main.isShowing() ? "En" : "Dis") + "abled";
					for (int i = 0; i < msg.length(); i++) System.out.print("=");
					System.out.println("");
					System.out.println(msg);
					for (int i = 0; i < msg.length(); i++) System.out.print("=");
					System.out.println("");
					Debugger.main.showDebug(!Debugger.main.isShowing());
				} else if (e.getKeyCode() == KeyEvent.VK_G) {
					Coder coder = new Coder();
					coder.encode(gameBox.game);
					System.out.println("GAME STRING: " + coder.result());
				}
				
				gameBox.game.keyPressed(e);
			}
			
			public void keyReleased(KeyEvent e) {
				gameBox.game.keyReleased(e);
			}
		});
		
		// Set frame properties and show it
		frame.setVisible(true);
		frame.setTitle("Jognny Droplet Touches Grass");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

		// MOOSIC
		Audio.main.run();
		
	}
}
}