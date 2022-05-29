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
	
	private LevelCreator[] levelCreators;
	private int verBreak = VER_BREAK;
	private GameInfo info;
	private Level[] levels;
	private int currLevel;
	private StartScreen screen;
	private boolean hasStarted;
	
	public Game(Dimension size, LevelCreator[] levels) {
		this.levelCreators = levels;
		this.info = new GameInfo(size, this);
		this.levels = new Level[levels.length];
		for (int i = 0; i < levels.length; i++) {
			this.levels[i] = levels[i].createLevel();
		}
		currLevel = 0;
		hasStarted = false;
		screen = new StartScreen();
		screen.setPosition(0, 0);
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
		levels[levIndex] = levelCreators[levIndex].createLevel();
	}
	
	public void restartLevel() {
		resetLevel(currLevel);
	}
	
	public void nextLevel() {
		currLevel++;
		levels[currLevel].getCurrentRoom().setPlayerPosition(); // HACKY
	}
	
	public void paint(Graphics2D g) {	
		if (hasStarted) {
			levels[currLevel].paint(g);
		}
		else {
			screen.paint(g);
		}
		
	}
	
	public void cycle() {
		if (hasStarted) {
			levels[currLevel].cycle(info);
			info.frameCount++;
		}
		else {
			screen.cycle(null, info);
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
			hasStarted = true;
		}
		
	}
}