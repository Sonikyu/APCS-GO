import java.util.ArrayList;
import java.util.Scanner;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Inspector.java
//
// Add your name here if you work on this class:
/** @author Ethan */ 
public class Inspector {
	public static Inspector main = new Inspector();

	private Game game;
	private Scanner in;

	public Inspector() {
		this.game = null;
		this.in = new Scanner(System.in);
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void run() {
		System.out.println("Inspection begun:");
		System.out.println(" - Type 'list' to list the current entities.");
		System.out.println(" - Type the id of an entity (as printed by 'list') to inspect it.");
		System.out.println(" - Type 'q', 'quit', 'e', or 'end' to exit.");

		String line = "";
		while (true) {
			System.out.print("> ");
			line = in.nextLine();
			if (line.equals("q") || line.equals("quit") || line.equals("e") || line.equals("end")) {
				return;
			}

			if (line != null && game != null) {
				final Room room = game.getCurrentLevel().getCurrentRoom();
				final ArrayList<Entity> visibleEntities = room.getVisibleEntities();
				if (line.equals("list")) {
					int maxLength = 0;
					for (Entity entity: visibleEntities) {
						if (entity.getID().length() > maxLength) {
							maxLength = entity.getID().length();
						}
					}
					maxLength = (maxLength + 3) & ~3;
					int col = 0;
					for (Entity entity: visibleEntities) {
						if (col >= 4) {
							col = 0;
							System.out.println();
						}
						System.out.print(entity.getID());
						int dif = maxLength - entity.getID().length();
						while (dif > 0) {
							System.out.print(" ");
							dif--;
						}
					}
					System.out.println();
				} else {

				}
			} else {
				System.err.println("[Inspector] Your input was null. Please try again.");
			}
		}
	}	
	
	public void close() {
		in.close();
	}
}