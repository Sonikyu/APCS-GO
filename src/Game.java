import java.awt.Graphics2D;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import restore.Coder;
import restore.Encodable;

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
	private LevelCreator[] levelCreators;
	private Level[] levels;
	private int currLevel;
	
	private StartScreen startScreen;
	private EndScreen endScreen;
	

	private enum GameState {
		START_GAME, IN_GAME, END_GAME;
	}
	
	public Game(Dimension size, Player p, LevelCreator[] levels) {
		this.player = p;
		this.levelCreators = levels;
		this.info = new GameInfo(size, this);
		this.levels = new Level[levels.length];
		currLevel = 0;
		state = GameState.START_GAME;
		startScreen = new StartScreen();
		endScreen = new EndScreen();
		
		this.levels[currLevel] = levelCreators[currLevel].createLevel(p);
		initialDebug();
	}
	
	
	public Game(Coder coder) {	
		Integer verMaj = coder.decodeInt(); if (coder.hasError()) { coder.setErrorMsg("Unknown major version"); return; }
		Integer verBreak = coder.decodeInt(); if (coder.hasError()) { coder.setErrorMsg("Unknown minor version"); return; }
		if (Game.VER_MAJ != verMaj || Game.VER_BREAK != verBreak) {
			coder.setErrorMsg("Incompatible game versions");
			return;
		}
		
		Integer width = coder.decodeInt(); if (coder.hasError()) { coder.setErrorMsg("Failed to decode width"); return; }
		Integer height = coder.decodeInt(); if (coder.hasError()) { coder.setErrorMsg("Failed to decode height"); return; }
		
		
		this.info = new GameInfo(new Dimension(width, height), this);
		this.verBreak = verBreak;
		
		Long frameCount = coder.decodeLong(); if (coder.hasError()) { coder.setErrorMsg("Failed to decode frame count"); return; }
		this.info.frameCount = frameCount;
//		TODO: Fix encoder
//		this.levels = new Level(coder); if (coder.hasError()) { coder.setErrorMsg("Failed to decode level"); return; }
		
		initialDebug();
	}
	
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
	
	public GameInfo getGameInfo() {
		return info;
	}
	
	public void resetLevel(int levIndex) {
		levels[levIndex] = levelCreators[levIndex].createLevel(player);
	}
	
	public void restartLevel() {
		resetLevel(currLevel);
	}
	
	public void nextLevel() {
		currLevel++;

		levels[currLevel] = levelCreators[currLevel].createLevel(player);
		levels[currLevel].getCurrentRoom().setPlayerPosition(); // HACKY
	}
	
	public void paint(Graphics2D g) {	
		if (state == GameState.IN_GAME) {
			levels[currLevel].paint(g);
		}
		else if (state ==  GameState.START_GAME) {
			startScreen.paint(g);
		}
		else {
			endScreen.paint(g);
		}
	}
	
	public void cycle() {
		if (state == GameState.IN_GAME) {
			levels[currLevel].cycle(info);
			info.frameCount++;
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
	
	public void keyPressed(KeyEvent key) {
		int keycode = key.getKeyCode();
		if (0 <= keycode && keycode <= 0xFFFF) {
	        if (info.keysDown.contains(keycode)) return;
	        info.keysDown.add(keycode);
	    }

	}
	
	public void keyReleased(KeyEvent key) {
		int keycode = key.getKeyCode();
	    if (0 <= keycode && keycode <= 0xFFFF) {
	        if (!info.keysDown.contains(keycode)) return;
	        info.keysDown.remove(keycode);
	    }
	}
	
	private void initialDebug() {
		Debugger.main.start();
		
		System.out.println("Game v" + VER_MAJ + "." + verBreak + "\n==========");
		System.out.println("Window Size: " + info.size.getWidth() + "px by " + info.size.getHeight() + "px");
	}
	
	
// johnny wrote this
	
	public class GameInfo {
		
		private Dimension size;
		private HashSet<Integer> keysDown;
		private Long frameCount;
		private Game game;
		
		public GameInfo(Dimension size, Game game) {
			this.size = size;
			this.keysDown = new HashSet<Integer>();
			this.frameCount = (long) 0;
			this.game = game;
		}
		
		public Dimension getSize() {
			return size;
		}
		
		public void restartLevel() {
			game.restartLevel();
		}

		public void nextLevel() {
			game.nextLevel();
		}
		
		public HashSet<Integer> getKeysDown() {
			return keysDown;
		}
		
		public long getFrameCount() {
			return frameCount;
		}
		
		public void startGame() {
			state = GameState.IN_GAME;
		}
		
		public void endGame() {
			System.err.println("endGame");
			state = GameState.END_GAME;
		}
		
	}
}