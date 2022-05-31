import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import restore.Coder;
import restore.Encodable;
import restore.CoderException;

// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: Game.java
//
// Add your name here if you work on this class:
/** @author Johnny, Ethan */ 
public class Game implements Encodable {
	
	public static final int VER_MAJ = 0;
	public static final int VER_BREAK = 1;
	
	private int verBreak = VER_BREAK;
	private GameInfo info;
	private GameState state;

	private Player player;
	private Level[] levels2;
	private boolean levels2AsSource;
	private LevelCreator[] levelCreators;
	private Level[] levels;
	private int currLevel;
	
	private StartScreen startScreen;
	private EndScreen endScreen;
	

	private enum GameState {
		START_GAME, IN_GAME, END_GAME;
	}
	
	/**
	 * Initializes the game.
	 * @param size The dimensions of the game.
	 * @param p The player.
	 * @param levels The level creators.
	 */
	public Game(Dimension size, Player p, LevelCreator[] levels) {
		this.player = p;
		this.levelCreators = levels;
		this.info = new GameInfo(size, this);
		this.levels = new Level[levels.length];
		this.levels2AsSource = false;
		currLevel = 0;
		state = GameState.START_GAME;
		startScreen = new StartScreen();
		endScreen = new EndScreen();
		
		this.levels[currLevel] = levelCreators[currLevel].createLevel(p);
		initialDebug();
	}
	
	/**
	 * Initializes the game.
	 * @param coder The object that creates the game from a game string.
	 */
	public Game(Coder coder) throws CoderException {	
		this.levels2AsSource = true;
		Integer verMaj = coder.decodeInt(); 
		Integer verBreak = coder.decodeInt();
		if (Game.VER_MAJ != verMaj || Game.VER_BREAK != verBreak) {
			throw new CoderException("Incompatible game versions");
		}
		
		Integer width = coder.decodeInt();
		Integer height = coder.decodeInt();
		
		
		this.info = new GameInfo(new Dimension(width, height), this);
		this.verBreak = verBreak;
		
		Long frameCount = coder.decodeLong();
		this.info.frameCount = frameCount;
//		TODO: Fix encoder
//		this.levels = new Level(coder); if (coder.hasError()) { coder.setErrorMsg("Failed to decode level"); return; }
		
		initialDebug();
	}
	
	/**
	 * Turns the game into a game string.
	 * @param coder The object that creates the game from a game string.
	 */
	public void encode(Coder coder) {
		coder.encode(Game.VER_MAJ);
		coder.encode(Game.VER_BREAK);
		
		coder.encode((int) info.size.getWidth());
		coder.encode((int) info.size.getHeight());
		
		coder.encode(info.frameCount);
//		TODO: complete coder
//		coder.encode(level);
		System.out.println("Encoded game");
	}
	
	/**
	 * Gets the game information.
	 * @return The game information.
	 */
	public GameInfo getGameInfo() {
		return info;
	}
	
	/**
	 * Gets the current level.
	 * @return THe current level.
	 */
	public Level getCurrentLevel() {
		return levels[currLevel];
	}
	
	/**
	 * Resets the specified level.
	 * @param The level that is being reset.
	 */
	public void resetLevel(int levIndex) {
		if (levels2AsSource) {
			Coder coder = new Coder();
			levels2[levIndex].encode(coder);
			try {
				levels[levIndex] = new Level(coder);
			} catch (CoderException e) {
				System.err.println(e);
				System.exit(1);
			}
		} else {
			levels[levIndex] = levelCreators[levIndex].createLevel(player);
		}
	}
	
	/**
	 * Restarts the current level.
	 */
	public void restartLevel() {
		resetLevel(currLevel);
	}
	
	/**
	 * Creates the next level.
	 */
	public void nextLevel() {
		currLevel++;
		resetLevel(currLevel);
		levels[currLevel].getCurrentRoom().setPlayerPosition(); // HACKY
	}
	
	/**
	 * Paints the game.
	 * @param g The graphics object the game is painted to.
	 */
	public void paint(Graphics2D g) {	
		if (state == GameState.IN_GAME) {
			levels[currLevel].paint(g);
		}
		else if (state ==  GameState.START_GAME) {
			startScreen.paint(g);
		}
		else {
			endScreen.paint(g);
			new TimerDisplay().paint(g);
		}
	}
	
	/**
	 * Cycles the game.
	 */
	public void cycle() {
		if (state == GameState.IN_GAME) {
			levels[currLevel].cycle(info);
			info.frameCount++;
			Timer.timer.updateTime();
		}
		else if (state ==  GameState.START_GAME) {
			startScreen.cycle(null,info);
		}
		else {
			endScreen.cycle(null,info);
		}
		//debugger.print("Game Loop");
	}
	
	// https://stackoverflow.com/questions/37490551/disabling-key-repeat-in-swing
	
	/**
	 * Adds the pressed key to a hashmap.
	 * @param key The key that is pressed.
	 */
	public void keyPressed(KeyEvent key) {
		int keycode = key.getKeyCode();
		if (0 <= keycode && keycode <= 0xFFFF) {
	        if (info.keysDown.contains(keycode)) return;
	        info.keysDown.add(keycode);
	    }

	}
	
	/**
	 * Removes the pressed key from the hashmap.
	 * @param key The key that is released.
	 */
	public void keyReleased(KeyEvent key) {
		int keycode = key.getKeyCode();
	    if (0 <= keycode && keycode <= 0xFFFF) {
	        if (!info.keysDown.contains(keycode)) return;
	        info.keysDown.remove(keycode);
	    }
	}
	
	/**
	 * Determines if the game version is up to date.
	 */
	private void initialDebug() {		
		System.out.println("Game v" + VER_MAJ + "." + verBreak + "\n==========");
		System.out.println("Window Size: " + info.size.getWidth() + "px by " + info.size.getHeight() + "px");
	}
	
	
	/**
	 * The game information.
	 */
	public class GameInfo {
		private Dimension size;
		private HashSet<Integer> keysDown;
		private Long frameCount;
		private Game game;
		
		/**
		 * 
		 * @param size The dimensions of the game.
		 * @param game The current game.
		 */
		public GameInfo(Dimension size, Game game) {
			this.size = size;
			this.keysDown = new HashSet<Integer>();
			this.frameCount = (long) 0;
			this.game = game;
		}
		
		/**
		 * Gets the dimensions of the game.
		 * @return The dimensions of the game.
		 */
		public Dimension getSize() {
			return size;
		}
		
		/**
		 * Restarts the current level.
		 */
		public void restartLevel() {
			game.restartLevel();
		}

		/**
		 * Sets the current level to the next level.
		 */
		public void nextLevel() {
			game.nextLevel();
		}
		
		/**
		 * Gets the hashmap of the keys pressed.
		 * @return The hashmap of the keys pressed.
		 */
		public HashSet<Integer> getKeysDown() {
			return keysDown;
		}
		
		/**
		 * Gets the current frame count.
		 * @return The frame count.
		 */
		public long getFrameCount() {
			return frameCount;
		}
		
		/**
		 * Starts the game after the start screen.
		 */
		public void startGame() {
			Timer.timer.start();
			state = GameState.IN_GAME;
			Audio.main.stopAudio();
			Audio.main.run(1);
			Audio.main.loopAudio();
		}
		
		/**
		 * Ends the game.
		 */
		public void endGame() {
			System.err.println("endGame");
			state = GameState.END_GAME;
		}
		
	}
}