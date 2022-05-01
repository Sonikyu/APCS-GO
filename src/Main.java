import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
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
			if (game == null) {
				System.err.println("Invalid game string: " + coder.getError());
				return;
			}
		} else {
			
			// Window size
			Dimension size = new Dimension(600, 600);

			// Initialize game
			Player p = new Player();
			WallTile wall = new WallTile();
			WallTile wall1 = new WallTile();
			WallTile wall2 = new WallTile();
			WallTile wall3 = new WallTile();
			wall.setPosition(100, 100);
			wall1.setPosition(70, 100);
			wall2.setPosition(40,100);
			wall3.setPosition(10,100);
			MoveOnlyEnemy enemy1 = new MoveOnlyEnemy(0, 100);
			enemy1.setPosition(100, 0);
			
			// Setup game
			game = new Game(size);
			game.addEntity(p);
			game.addEntity(wall);
			game.addEntity(wall1);
			game.addEntity(wall2);
			game.addEntity(wall3);
			game.addEntity(enemy1);
		}

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
		frame.setTitle("Escape from the Galactic Cruiser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}
}
