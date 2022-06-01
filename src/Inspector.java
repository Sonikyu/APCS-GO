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

	/**
	 * Initializes the Inspector.
	 */
	public Inspector() {
		this.game = null;
		this.in = new Scanner(System.in);
	}

	/**
	 * Sets the game.
	 * @param game The current game.
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	private void printHelp() {
		System.out.println(" - Type 'list' to list the current entities.");
		System.out.println(" - Type 'i' or 'inspect' followed the id of an entity (as printed by 'list') to inspect it.");
		System.out.println(" - Type 'show' or 'hide' followed by an entity id (as printed by 'list') to change its visibility.");
		System.out.println(" - Type 'q', 'quit', 'e', or 'end' to exit.");
	}
	
	/**
	 * Runs the inspector.
	 */
	public void run() {
		System.out.println("[Inspector] Activated.");
		printHelp();
		String line = "";
		boolean HACK = true;
		final Room room = game.getCurrentLevel().getCurrentRoom();
		ArrayList<Entity> entities = room.HACK_getEntities();
		for (int i = entities.size() - 1; i >= 0; i--) {
			if (entities.get(i).isOfType(Tile.BORING_TYPES)) {
				entities.remove(i);
			}
		}
		while (HACK) {
			System.out.print("> ");
			line = in.nextLine();
			if (line.equals("q") || line.equals("quit") || line.equals("e") || line.equals("end")) {
				return;
			}

			if (line != null && game != null) {
				if (line.equals("list")) {
					for (Entity entity: entities) {
						System.out.println(entity.getID());
					}
//					int maxLength = 0;
//					for (Entity entity: visibleEntities) {
//						if (entity.getID().length() > maxLength) {
//							maxLength = entity.getID().length();
//						}
//					}
//					maxLength = (maxLength + 3) & ~3;
//					int col = 0;
//					for (Entity entity: visibleEntities) {
//						if (col >= 4) {
//							col = 0;
//							System.out.println();
//						}
//						System.out.print(entity.getID());
//						int dif = maxLength - entity.getID().length();
//						while (dif > 0) {
//							System.out.print(" ");
//							dif--;
//						}
//					}
//					System.out.println();
				} else if (line.equals("help")) {
					printHelp();
				} else if (line.startsWith("i ") || line.startsWith("inspect ")) {
					line = line.substring(line.indexOf(" ") + 1);
					boolean foundEntity = false;
					for (Entity entity: entities) {
						if (entity.getID().equals(line)) {
							entity.printDetailedDebug();
							foundEntity = true;
							break;
						}
					}
					if (!foundEntity) {
						System.err.println("Could not locate entity. Try 'list' to view avalible entities.");
					}
				} else if (line.startsWith("show ")) {
					line = line.substring(line.indexOf(" ") + 1);
					boolean foundEntity = false;
					for (Entity entity: entities) {
						if (entity.getID().equals(line)) {
							foundEntity = true;
							entity.show();
							System.out.println("The entity will become visible once the inspector terminates.");
							foundEntity = true;
							break;
						}
					}
					if (!foundEntity) {
						System.err.println("Could not locate entity. Try 'list' to view avalible entities.");
					}
				} else if (line.startsWith("hide ")) {
					line = line.substring(line.indexOf(" ") + 1);
					boolean foundEntity = false;
					for (Entity entity: entities) {
						if (entity.getID().equals(line)) {
							entity.hide();
							System.out.println("The entity will become invisible once the inspector terminates.");
							foundEntity = true;
							break;
						}
					}
					if (!foundEntity) {
						System.err.println("Could not locate entity. Try 'list' to view avalible entities.");
					}
				} else {
					System.err.println("Please enter a valid command. Confirm you have provided an entity id if required. Try 'help' for a list of the avaliable commands.");
				}
			} else {
				System.err.println("[Inspector] Your input was null. Please try again.");
			}
		}
		System.out.println("[Inspector] Terminated. Use the I key to reactivate.");
	}	
	
	/**
	 * Closes the inspector.
	 */
	public void close() {
		in.close();
	}
}