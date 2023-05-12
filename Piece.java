import java.sql.Struct;

public class Piece {
    private boolean isAlive;
    private boolean isKing;
    private boolean isWhite;
    private boolean canMove;
    private int currentRow;
    private int currentCol;
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
    public int currentRow() { return currentRow; }
    public int currentCol() { return currentCol; }
    public int pieceNumber() { return pieceNumber; }
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
    public void editRowCol(int newRow, int newCol) {
        this.currentRow = newRow;
        this.currentCol = newCol;
    }
}
