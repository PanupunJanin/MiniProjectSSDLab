public class Board {
    private final int size;
    private Tile [][] tiles;
    private Piece [][] pieces;
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
        return pieces[row][col];
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
        pieces = new Piece[size][size];
        for(int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Tile tile = getTile(row, col);
                if(tile.isInPlay() && row <= 2) {
                    pieces[row][col] = new Piece();
                    tile.addPiece();
                }
            }
        }
    }
    private void initPieceBlack() {
        for(int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Tile tile = getTile(row, col);
                if(tile.isInPlay() && row >= 5) {
                    pieces[row][col] = new Piece();
                    pieces[row][col].blacked();
                    tile.addPiece();
                }
            }
        }
    }
    public void movePiece(int row, int col) {
        Piece piece = getPiece(row, col);
        if(piece.isWhite()){
            if(!piece.isKing()) {
                Tile frontLeftTile = getTile(row+1, col-1);
                Tile frontRightTile = getTile(row+1, col+1);
            }
        } else {
            if(!piece.isKing()) {
                Tile frontLeftTile = getTile(row-1, col-1);
                Tile frontRightTile = getTile(row-1, col+1);
            }
        }
    }
}
