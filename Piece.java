import java.sql.Struct;

public class Piece {
    private boolean isAlive;
    private boolean isKing;
    private boolean isWhite;
    private boolean canMove;
    public int currentRow;
    public int currentCol;
    private final int pieceNumber;
    public Piece(int row, int col, int number) {
        currentRow = row;
        currentCol = col;
        pieceNumber = number;
        isAlive = true;
        isKing = false;
        isWhite = true;
        canMove = false;
    }
    public int row() { return currentRow; }
    public int col() { return currentCol; }
    public int num() { return pieceNumber; }
    public boolean isAlive() { return isAlive; }
    public boolean isKing() { return isKing; }
    public boolean isWhite() {
        return isWhite;
    }
    public boolean canMove() {
        return canMove;
    }
    public void killed() {
        this.isAlive = false;
    }
    public void crowned() {
        this.isKing = true;
    }
    public void blacked() {
        this.isWhite = false;
    }
    public void enableMove() { this.canMove = true; }
}
