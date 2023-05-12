public class Tile {
    private boolean hasPiece;
    private boolean isInPlay;
    public Tile() {
        hasPiece = false;
        isInPlay = false; // In checker, the tiles that will be used is only black tiles.
    }
    public boolean hasPiece() {
        return hasPiece;
    }
    public boolean isInPlay() {
        return isInPlay;
    }
    public void addPiece() {
        this.hasPiece = true;
    }
    public void removePiece() {
        this.hasPiece = false;
    }
    public void inPlay() {
        this.isInPlay = true;
    }
}