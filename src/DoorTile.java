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
