import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Main.java
//
// Add your name here if you work on this class:
/** @author Ethan */ 
public class Main {
	public static void main(String[] args) {		
		// Window size
		Dimension size = new Dimension(600, 600);
		
		// Make JFRame
		JFrame frame = new JFrame();

		// Initialize game
		Player p = new Player();
		WallTile wall = new WallTile("MyWallTile");
		wall.setPosition(100, 100);
		
		// Setup game
		Game game = new Game(size);
		game.addEntity(p);
		game.addEntity(wall);

		// Sets up frame with GameView
		GameView gameView = new GameView(game);
		frame.add(gameView);
		
		// Setup input controller
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
				}
				
				game.keyPressed(e);
			}
			
			public void keyReleased(KeyEvent e) {
				game.keyReleased(e);
			}
		});
		
		// Set frame properties and show it
		frame.setVisible(true);
		frame.setTitle("Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}
}
