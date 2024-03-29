public class Board {
    private final int size;
    private Tile [][] tiles;
    private Piece[] pieces;
    private static Piece lastMovedPiece;
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
        lastMovedPiece = pieces[maxPieceAmount-1];
    }
    public void movePiece(int row, int col, Tile tileToMove) {
        Tile currentTile = getTile(row, col);
        Piece piece = getPiece(row, col);
        if(piece.isWhite() != lastMovedPiece.isWhite()) {
            if(piece.isWhite()){
                Tile frontLeftTile = getTile(row+1, col-1);
                Tile frontRightTile = getTile(row+1, col+1);
                Tile nextFrontLeftTile = getTile(row+2, col-2);
                Tile nextFrontRightTile = getTile(row+2, col+2);
                if(!piece.isKing()) {
                    if(tileToMove == frontLeftTile && !tileToMove.hasPiece()) {
                        currentTile.removePiece();
                        tileToMove.addPiece();
                        piece.editRowCol(row+1, col-1);
                        lastMovedPiece = piece;
                        if(row+1 >= 7) {
                            piece.crowned();
                        }
                    } else if(tileToMove == frontRightTile && !tileToMove.hasPiece()) {
                        currentTile.removePiece();
                        tileToMove.addPiece();
                        piece.editRowCol(row+1, col+1);
                        lastMovedPiece = piece;
                        if(row+1 >= 7) {
                            piece.crowned();
                        }
                    } else if(tileToMove == nextFrontLeftTile && frontLeftTile.hasPiece() && !nextFrontLeftTile.hasPiece()) {
                        Piece enemy = getPiece(row+1, col-1);
                        if(piece.isWhite() != enemy.isWhite()) {
                            currentTile.removePiece();
                            frontLeftTile.removePiece();
                            enemy.killed();
                            nextFrontLeftTile.addPiece();
                            piece.editRowCol(row+2, col-2);
                            lastMovedPiece = piece;
                            if(row+2 >= 7) {
                                piece.crowned();
                            }
                        }
                    } else if(tileToMove == nextFrontRightTile && frontRightTile.hasPiece() && !nextFrontRightTile.hasPiece()) {
                        Piece enemy = getPiece(row+1, col+1);
                        if(piece.isWhite() != enemy.isWhite()) {
                            currentTile.removePiece();
                            frontRightTile.removePiece();
                            enemy.killed();
                            nextFrontRightTile.addPiece();
                            piece.editRowCol(row+2, col+2);
                            lastMovedPiece = piece;
                            if(row+2 >= 7) {
                                piece.crowned();
                            }
                        }
                    }
                } else {
                    Tile backLeftTile = getTile(row - 1, col - 1);
                    Tile backRightTile = getTile(row - 1, col + 1);
                    Tile nextBackLeftTile = getTile(row - 2, col - 2);
                    Tile nextBackRightTile = getTile(row - 2, col + 2);
                    if (tileToMove == frontLeftTile && !tileToMove.hasPiece()) {
                        currentTile.removePiece();
                        tileToMove.addPiece();
                        piece.editRowCol(row + 1, col - 1);
                        lastMovedPiece = piece;
                    } else if (tileToMove == frontRightTile && !tileToMove.hasPiece()) {
                        currentTile.removePiece();
                        tileToMove.addPiece();
                        piece.editRowCol(row + 1, col + 1);
                        lastMovedPiece = piece;
                    } else if (tileToMove == backLeftTile && !tileToMove.hasPiece()) {
                        currentTile.removePiece();
                        tileToMove.addPiece();
                        piece.editRowCol(row - 1, col - 1);
                        lastMovedPiece = piece;
                    } else if (tileToMove == backRightTile && !tileToMove.hasPiece()) {
                        currentTile.removePiece();
                        tileToMove.addPiece();
                        piece.editRowCol(row - 1, col + 1);
                        lastMovedPiece = piece;
                    } else if (tileToMove == nextFrontLeftTile && frontLeftTile.hasPiece() && !nextFrontLeftTile.hasPiece()) {
                        Piece enemy = getPiece(row+1, col-1);
                        if(piece.isWhite() != enemy.isWhite()) {
                            currentTile.removePiece();
                            frontLeftTile.removePiece();
                            enemy.killed();
                            nextFrontLeftTile.addPiece();
                            piece.editRowCol(row+2, col-2);
                            lastMovedPiece = piece;
                        }
                    } else if (tileToMove == nextFrontRightTile && frontRightTile.hasPiece() && !nextFrontRightTile.hasPiece()) {
                        Piece enemy = getPiece(row+1, col+1);
                        if(piece.isWhite() != enemy.isWhite()) {
                            currentTile.removePiece();
                            frontRightTile.removePiece();
                            enemy.killed();
                            nextFrontRightTile.addPiece();
                            piece.editRowCol(row+2, col+2);
                            lastMovedPiece = piece;
                        }
                    }else if (tileToMove == nextBackLeftTile && backLeftTile.hasPiece() && !nextBackLeftTile.hasPiece()) {
                        Piece enemy = getPiece(row-1, col-1);
                        if(piece.isWhite() != enemy.isWhite()) {
                            currentTile.removePiece();
                            backLeftTile.removePiece();
                            enemy.killed();
                            nextBackLeftTile.addPiece();
                            piece.editRowCol(row-2, col-2);
                            lastMovedPiece = piece;
                        }
                    } else if (tileToMove == nextBackRightTile && backRightTile.hasPiece() && !nextBackRightTile.hasPiece()) {
                        Piece enemy = getPiece(row-1, col+1);
                        if(piece.isWhite() != enemy.isWhite()) {
                            currentTile.removePiece();
                            backRightTile.removePiece();
                            enemy.killed();
                            nextBackRightTile.addPiece();
                            piece.editRowCol(row-2, col+2);
                            lastMovedPiece = piece;
                        }
                    }
                }
            } else {
                Tile frontLeftTile = getTile(row - 1, col - 1);
                Tile frontRightTile = getTile(row - 1, col + 1);
                Tile nextFrontLeftTile = getTile(row - 2, col - 2);
                Tile nextFrontRightTile = getTile(row - 2, col + 2);
                if (!piece.isKing()) {
                    if (tileToMove == frontLeftTile && !tileToMove.hasPiece()) {
                        currentTile.removePiece();
                        tileToMove.addPiece();
                        piece.editRowCol(row - 1, col - 1);
                        lastMovedPiece = piece;
                        if (row - 1 <= 0) {
                            piece.crowned();
                        }
                    } else if (tileToMove == frontRightTile && !tileToMove.hasPiece()) {
                        currentTile.removePiece();
                        tileToMove.addPiece();
                        piece.editRowCol(row - 1, col + 1);
                        lastMovedPiece = piece;
                        if (row - 1 <= 0) {
                            piece.crowned();
                        }
                    } else if (tileToMove == nextFrontLeftTile && frontLeftTile.hasPiece() && !nextFrontLeftTile.hasPiece()) {
                        Piece enemy = getPiece(row-1, col-1);
                        if(piece.isWhite() != enemy.isWhite()) {
                            currentTile.removePiece();
                            frontLeftTile.removePiece();
                            enemy.killed();
                            nextFrontLeftTile.addPiece();
                            piece.editRowCol(row - 2, col - 2);
                            lastMovedPiece = piece;
                            if (row - 2 <= 0) {
                                piece.crowned();
                            }
                        }
                    } else if (tileToMove == nextFrontRightTile && frontRightTile.hasPiece() && !nextFrontRightTile.hasPiece()) {
                        Piece enemy = getPiece(row-1, col+1);
                        if(piece.isWhite() != enemy.isWhite()) {
                            currentTile.removePiece();
                            frontRightTile.removePiece();
                            enemy.killed();
                            nextFrontRightTile.addPiece();
                            piece.editRowCol(row - 2, col + 2);
                            lastMovedPiece = piece;
                            if (row - 2 <= 0) {
                                piece.crowned();
                            }
                        }
                    }
                } else {
                    Tile backLeftTile = getTile(row + 1, col - 1);
                    Tile backRightTile = getTile(row + 1, col + 1);
                    Tile nextBackLeftTile = getTile(row + 2, col - 2);
                    Tile nextBackRightTile = getTile(row + 2, col + 2);
                    if (tileToMove == frontLeftTile && !tileToMove.hasPiece()) {
                        currentTile.removePiece();
                        tileToMove.addPiece();
                        piece.editRowCol(row - 1, col - 1);
                        lastMovedPiece = piece;
                    } else if (tileToMove == frontRightTile && !tileToMove.hasPiece()) {
                        currentTile.removePiece();
                        tileToMove.addPiece();
                        piece.editRowCol(row - 1, col + 1);
                        lastMovedPiece = piece;
                    } else if (tileToMove == backLeftTile && !tileToMove.hasPiece()) {
                        currentTile.removePiece();
                        tileToMove.addPiece();
                        piece.editRowCol(row + 1, col - 1);
                        lastMovedPiece = piece;
                    } else if (tileToMove == backRightTile && !tileToMove.hasPiece()) {
                        currentTile.removePiece();
                        tileToMove.addPiece();
                        piece.editRowCol(row + 1, col + 1);
                        lastMovedPiece = piece;
                    } else if (tileToMove == nextFrontLeftTile && frontLeftTile.hasPiece() && !nextFrontLeftTile.hasPiece()) {
                        Piece enemy = getPiece(row-1, col-1);
                        if(piece.isWhite() != enemy.isWhite()) {
                            currentTile.removePiece();
                            frontLeftTile.removePiece();
                            enemy.killed();
                            nextFrontLeftTile.addPiece();
                            piece.editRowCol(row - 2, col - 2);
                            lastMovedPiece = piece;
                        }
                    } else if (tileToMove == nextFrontRightTile && frontRightTile.hasPiece() && !nextFrontRightTile.hasPiece()) {
                        Piece enemy = getPiece(row-1, col+1);
                        if(piece.isWhite() != enemy.isWhite()) {
                            currentTile.removePiece();
                            frontRightTile.removePiece();
                            enemy.killed();
                            nextFrontRightTile.addPiece();
                            piece.editRowCol(row - 2, col + 2);
                            lastMovedPiece = piece;
                        }
                    } else if (tileToMove == nextBackLeftTile && backLeftTile.hasPiece() && !nextBackLeftTile.hasPiece()) {
                        Piece enemy = getPiece(row+1, col-1);
                        if(piece.isWhite() != enemy.isWhite()) {
                            currentTile.removePiece();
                            backLeftTile.removePiece();
                            enemy.killed();
                            nextBackLeftTile.addPiece();
                            piece.editRowCol(row + 2, col - 2);
                            lastMovedPiece = piece;
                        }
                    } else if (tileToMove == nextBackRightTile && backRightTile.hasPiece() && !nextBackRightTile.hasPiece()) {
                        Piece enemy = getPiece(row+1, col+1);
                        if(piece.isWhite() != enemy.isWhite()) {
                            currentTile.removePiece();
                            backRightTile.removePiece();
                            enemy.killed();
                            nextBackRightTile.addPiece();
                            piece.editRowCol(row + 2, col + 2);
                            lastMovedPiece = piece;
                        }
                    }
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
    public boolean getCurrentTurn() { return !lastMovedPiece.isWhite(); }
}
