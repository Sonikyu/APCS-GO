
// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: FloorTile.java
//
// Add your name here if you work on this class:
/** @authors Alex */ 
public class WallTile extends Entity{
    
    public static final String TYPE = "WallTile";
    public WallTile(String id){
        super(id, WallTile.TYPE, 0, "WallTIle.png");

    }

    public void cycle(Game game){

    }

}

// Tile images are 30 x 30, total frame is 600 x 600
// Types of tiles: Floor, Wall, Door, 