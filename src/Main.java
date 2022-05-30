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
			Player player = new Player();
			LevelCreator[] levels = {new LevelOneCreator(), new LevelTwoCreator()};
			game = new Game(size, player, levels);
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
		frame.setTitle("Jognny Droplet Touches Grass");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

<<<<<<< Updated upstream
		// MOOSIC
		//Audio.main.run();
=======
		// MOOSIC : Done through other classes LOl
		
>>>>>>> Stashed changes
	}
}