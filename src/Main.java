import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
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
/** @authors Ethan */ 
public class Main {
	public static void main(String[] args) {
		// Window size
		Dimension size = new Dimension(600, 600);

		// Initialize game
		//Entity starEntity = new Entity("STAR1", 0, "star.png");
		Game game = new Game(size);
		//game.addEntity(starEntity);

		// Sets up frame with GameView
		JFrame frame = new JFrame();
		GameView gameView = new GameView(game);
		frame.add(gameView);
		
		// Setup input controller
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
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
