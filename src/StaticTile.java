// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: StaticTile.java
//
// Add your name here if you work on this class:
/** @authors Alex */ 
public class StaticTile extends Entity{
    
    public enum Material {
        WALL, FLOOR, DOOR, START, GOAL
    }
    private static final String[] pngs = {"WallTile.png", "FloorTile.png", "DoorTile.png", "StartTile.png", "GoalTile.png"};
    private static final String[] names = {"WallTile", "FloorTile", "DoorTile", "StartTile", "GoalTile"};

    
    public StaticTile(String id, Material material){
        super(id, names[material.ordinal()], 0, pngs[material.ordinal()]);
    }



    public void cycle(Game game){
        if(getType().equals("FloorTile")){
            System.out.println("Test");
        }
    }

}

// Tile images are 30 x 30, total frame is 600 x 600
// Types of tiles: Floor, Wall, Door, 