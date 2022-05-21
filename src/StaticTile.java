// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: StaticTile.java
//
// Add your name here if you work on this class:
/** @author Alex, Johnny */

import restore.Coder;

public class StaticTile extends Entity {
    public enum Material {
        WALL, FLOOR, DOOR, START, GOAL
    }
    
    private static final String[] PNGS = {"WallTile.png", "FloorTile.png", "DoorTile.png", "FloorTile.png", "GoalTile.png"};
    public static final String[] TYPES = {"WallTile", "FloorTile", "DoorTile", "StartTile", "GoalTile"};
    public static final int HEIGHT = 30;
    public static final int WIDTH = 30;
    
    public StaticTile(Material material){
        super(TYPES[material.ordinal()], 0, PNGS[material.ordinal()]);
    }
    
    public StaticTile(Coder coder) {
    	super(coder);
    }
    
    public void encode(Coder coder) {
    	super.encode(coder);
    }

    @Override
    public void cycle(Level level, Game.GameInfo info){
    	
    }
}

// Tile images are 30 x 30, total frame is 600 x 600
// Types of tiles: Floor, Wall, Door, 