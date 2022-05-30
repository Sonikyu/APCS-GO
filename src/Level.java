// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Level.java
//
// Add your name here if you work on this class:
/** @author Johnny */ 

import java.awt.Graphics2D;

import restore.Coder;
import restore.Encodable;

/**
 * Represents a single level in the game. Contains a 2D-array of rooms, some of which may be null.
 */
public class Level implements Encodable {

	private Room[][] map;
	
	private int currentRow;
	private int currentCol;
	private Player player;
	
	/**
     * 
     * @param type
     * @return
     */

	public Level(Room[][] rooms, int curRow, int curCol, Player player) {
		map = rooms;
		currentRow = curRow;
		currentCol = curCol;
		this.player = player;
		this.player.respawn();
	}
	
	public Level(Coder coder) {
		this.currentRow = coder.decodeInt();
		this.currentCol = coder.decodeInt();
		this.player = new Player(coder);
		int numRows = coder.decodeInt();
		int numCols = coder.decodeInt();
		this.map = new Room[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				map[i][j] = new Room(coder);
			}
		}
	}
	
	@Override
	public void encode(Coder coder) {
		coder.encode(currentRow);
		coder.encode(currentCol);
		coder.encode(player);
		coder.encode(map.length);
		coder.encode(map[0].length);
		for (Room[] row : map) {
			for (Room col : row) {
				coder.encode(col);
			}
		}
		System.out.println("Encoding Room");
	}
	
	public Room getCurrentRoom() {
		return map[currentRow][currentCol];
	}

	public void moveRoomUp() {
		if (currentRow > 0) {
			Room currentRoom = getCurrentRoom();
			currentRoom.unloadRoom();
			currentRow -= 1;
			currentRoom.loadRoom();
			Debugger.main.print(player.getID() + " moved to Room" + "[" + currentRow + "]" + "[" + currentCol + "]");
		}
	}

	public void moveRoomRight() {
		if (currentCol < map[0].length-1) {
			Room currentRoom = getCurrentRoom();
			currentRoom.unloadRoom();
			currentCol += 1;
			currentRoom.loadRoom();
			Debugger.main.print(player.getID() + " moved to Room" + "[" + currentRow + "]" + "[" + currentCol + "]");
		}
	}
	
	public void moveRoomDown() {
		if (currentRow < map.length-1) {
			Room currentRoom = getCurrentRoom();
			currentRoom.unloadRoom();
			currentRow += 1;
			currentRoom.loadRoom();
			Debugger.main.print(player.getID() + " moved to Room" + "[" + currentRow + "]" + "[" + currentCol + "]");
		}
	}
	
	public void moveRoomLeft() {
		if (currentCol > 0) {
			Room currentRoom = getCurrentRoom();
			currentRoom.unloadRoom();
			currentCol -= 1;
			currentRoom.loadRoom();
			Debugger.main.print(player.getID() + " moved to Room" + "[" + currentRow + "]" + "[" + currentCol + "]");
		}
	}
	
	public void cycle(Game.GameInfo info) {
		getCurrentRoom().cycle(this,info);
	}
	
	public void paint(Graphics2D g) {
		getCurrentRoom().paint(g);
	}
}