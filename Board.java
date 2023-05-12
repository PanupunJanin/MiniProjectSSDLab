public class Board {
    private final int size;
    private Tile [][] tiles;
    private Piece[] pieces;
    public final int maxPieceAmount = 24;
    private int currentPieceNumber = 0;
    public Board(int size) {
        this.size = size;
        initTiles();
        initPieceWhite();
        initPieceBlack();
    }
    public Tile getTile(int row, int col) {
        if(row < 0 || row >= size || col < 0 || col >= size) {
            return null;
        }
        return tiles[row][col];
    }
    public Piece getPiece(int row, int col) {
        Tile tile = getTile(row, col);
        if(!tile.hasPiece()) {
            return null;
        }
        for(int i=0; i < maxPieceAmount; i++) {
            if(pieces[i].currentRow() == row && pieces[i].currentCol() == col) {
                return pieces[i];
            }
        }
        return null;
    }
    private void initTiles() {
        tiles = new Tile[size][size];
        for(int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++) {
                tiles[row][col] = new Tile();
                if(row%2 == 0 && col%2 != 0) {
                    tiles[row][col].inPlay();
                }
                else if(row%2 != 0 && col%2 == 0){
                    tiles[row][col].inPlay();
                }
            }
        }
    }
    private void initPieceWhite() {
        pieces = new Piece[maxPieceAmount];
        for(int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Tile tile = getTile(row, col);
                if(tile.isInPlay() && row <= 2) {
                    pieces[currentPieceNumber] = new Piece(row, col, currentPieceNumber);
                    tile.addPiece();
                    currentPieceNumber++;
                }
            }
        }
    }
    private void initPieceBlack() {
        for(int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Tile tile = getTile(row, col);
                if(tile.isInPlay() && row >= 5) {
                    pieces[currentPieceNumber] = new Piece(row, col, currentPieceNumber);
                    pieces[currentPieceNumber].blacked();
                    tile.addPiece();
                    currentPieceNumber++;
                }
            }
        }
    }
    public void movePiece(int row, int col, Tile tileToMove) {
        Piece piece = getPiece(row, col);
        if(piece.isWhite()){
            Tile frontLeftTile = getTile(row+1, col-1);
            Tile frontRightTile = getTile(row+1, col+1);
            if(!piece.isKing()) {
                if(tileToMove == frontLeftTile) {
                    frontLeftTile.removePiece();
                    tileToMove.addPiece();
                    piece.editRowCol(row+1, col-1);
                } else if(tileToMove == frontRightTile) {
                    frontRightTile.removePiece();
                    tileToMove.addPiece();
                    piece.editRowCol(row+1, col+1);
                }
            } else {
                Tile backLeftTile = getTile(row - 1, col - 1);
                Tile backRightTile = getTile(row - 1, col + 1);
                if (tileToMove == frontLeftTile) {
                    frontLeftTile.removePiece();
                    tileToMove.addPiece();
                    piece.editRowCol(row + 1, col - 1);
                } else if (tileToMove == frontRightTile) {
                    frontRightTile.removePiece();
                    tileToMove.addPiece();
                    piece.editRowCol(row + 1, col + 1);
                } else if (tileToMove == backLeftTile) {
                    frontRightTile.removePiece();
                    tileToMove.addPiece();
                    piece.editRowCol(row - 1, col - 1);
                } else if (tileToMove == backRightTile) {
                    frontRightTile.removePiece();
                    tileToMove.addPiece();
                    piece.editRowCol(row - 1, col + 1);
                }
            }
        } else {
            Tile frontLeftTile = getTile(row-1, col-1);
            Tile frontRightTile = getTile(row-1, col+1);
            if(!piece.isKing()) {
                if(tileToMove == frontLeftTile) {
                    frontLeftTile.removePiece();
                    tileToMove.addPiece();
                    piece.editRowCol(row-1, col-1);
                } else if(tileToMove == frontRightTile) {
                    frontRightTile.removePiece();
                    tileToMove.addPiece();
                    piece.editRowCol(row-1, col+1);
                }
            } else {
                Tile backLeftTile = getTile(row + 1, col - 1);
                Tile backRightTile = getTile(row + 1, col + 1);
                if (tileToMove == frontLeftTile) {
                    frontLeftTile.removePiece();
                    tileToMove.addPiece();
                    piece.editRowCol(row - 1, col - 1);
                } else if (tileToMove == frontRightTile) {
                    frontRightTile.removePiece();
                    tileToMove.addPiece();
                    piece.editRowCol(row - 1, col + 1);
                } else if (tileToMove == backLeftTile) {
                    frontRightTile.removePiece();
                    tileToMove.addPiece();
                    piece.editRowCol(row + 1, col - 1);
                } else if (tileToMove == backRightTile) {
                    frontRightTile.removePiece();
                    tileToMove.addPiece();
                    piece.editRowCol(row + 1, col + 1);
                }
            }
        }
    }
    public int checkGameEnd() {
        int whiteCount = 0;
        int blackCount = 0;
        for(int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++) {
                Tile tile = getTile(row, col);
                if(tile.hasPiece()) {
                    Piece piece = getPiece(row, col);
                    if(piece.isWhite()) {
                        whiteCount++;
                    } else {
                        blackCount++;
                    }
                }
            }
        }
        if(whiteCount <= 0) {
            return 1; // Return 1 = White win
        } else if(blackCount <= 0) {
            return 2; // Return 2 = Black win
        }
        return 0; // Return 0 = Game not over yet
    }
}
