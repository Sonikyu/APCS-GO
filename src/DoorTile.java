
// AP CS Project
// Alex, Johnny, Ethan, and Uday
//
// This is a template comment that you should paste verbatim above every class.
// Fill out any necessary information:
//
// File: DoorTile.java
//
// Add your name here if you work on this class:
/** @author Alex */ 

public class DoorTile extends Tile{
    private boolean open;
    public DoorTile(){
        super(Tile.Material.DOOR);
        open = false;
        
    }
    public boolean isOpen(){
        return open;
    }

    public void setOpen(boolean bool){
        open = bool;
    }
}
