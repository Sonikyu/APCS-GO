// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: StaticTile.java
//src/StaticTile.java
// Add your name here if you work on this class:
/** @author Alex, Johnny */

import restore.Coder;

public class Tile extends Entity {
    public enum Material {
        WALL, FLOOR, DOOR, START, GOAL, LEVEL_UP, GRASS
    }

    public static final String[] TYPES = {"WallTile", "FloorTile", "DoorTile", "StartTile", "GoalTile", "LevelUpTile", "GrassTile" };
    public static final String[] WALL_TYPES = { "WallTile", "DoorTile" };
    private static final String[][] PNGS = {{"WallTileH.png","WallTileV.png","WallTileDL.png","WallTIleDR.png", "WallTileUL.png","WallTileUR.png"}, {"FloorTile.png"}, {"DoorTile.png", "FloorTile.png"}, {"FloorTile.png"}, {"GoalTile.png"}, {"LevelUpTile.png"}, {"GrassTile.png"}};


    public static final int HEIGHT = 40;
    public static final int WIDTH = 40;
    
    private Material material;
    
    public Material getMaterial() {
    	return material;
    }
    
    public Tile(Material material){
        super(TYPES[material.ordinal()], 0, PNGS[material.ordinal()]);
        this.material = material;
    }
    
    public Tile(Coder coder) {
    	super(coder);
    }
    
    public void encode(Coder coder) {
    	super.encode(coder);
    }

    @Override
    public void cycle(Level level, Game.GameInfo info){
    	
    }
}

// Tile images are 40 x 40, total frame is 800 x 600
// Types of tiles: Floor, Wall, Door, 