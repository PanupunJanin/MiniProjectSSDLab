public class Tile {
    private boolean hasPiece;
    private boolean isInPlay;
    private boolean canMove;
    public Tile() {
        hasPiece = false;
        isInPlay = false; // In checker, the piece would only be on black tiles.
        canMove = false;
    }
    public boolean hasPiece() {
        return hasPiece;
    }
    public boolean isInPlay() {
        return isInPlay;
    }
    public boolean canMove() {
        return canMove;
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
    public void setEnableMove(boolean set) {
        this.canMove = set;
    }
}