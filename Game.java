import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JFrame{
    private Board board;
    private final int boardSize = 8;
    private GameUI gameUI;
    public Game() {
        board = new Board(boardSize);
        gameUI = new GameUI();
        add(gameUI);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    class GameUI extends JPanel {
        public static final int TileSize = 100;
        private Image imageTileWhite;
        private Image imageTileBlack;
        private Image imageMoveableTile;
        private Image imagePieceWhite;
        private Image imageKingPieceWhite;
        private Image imagePieceBlack;
        private Image imageKingPieceBlack;
        private int currentRow = 0;
        private int currentCol = 1;

        public GameUI() {
            setPreferredSize(new Dimension(boardSize * TileSize, boardSize * TileSize));
            setTitle("Checker");
            imageTileWhite = new ImageIcon("imgs/TileWhite.png").getImage();
            imageTileBlack = new ImageIcon("imgs/TileBlack.png").getImage();
            imageMoveableTile = new ImageIcon("imgs/MoveableTile.png").getImage();
            imagePieceWhite = new ImageIcon("imgs/WhitePiece.png").getImage();
            imageKingPieceWhite = new ImageIcon("imgs/KingWhitePiece.png").getImage();
            imagePieceBlack = new ImageIcon("imgs/BlackPiece.png").getImage();
            imageKingPieceBlack = new ImageIcon("imgs/KingBlackPiece.png").getImage();

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    ResetTiles();
                    super.mousePressed(e);
                    int row = e.getY() / TileSize;
                    int col = e.getX() / TileSize;
                    Tile tile = board.getTile(row, col);
                    Piece piece = board.getPiece(row, col);
                    System.out.println("Clicked on row: " + row + ", col: " + col);
                    System.out.println("Has piece: " + tile.hasPiece());
                    if(tile.hasPiece()){
                        System.out.println("Piece is white: " + piece.isWhite());
                        System.out.println("Piece is king: " + piece.isKing());
                        System.out.println("Current piece row, col: " + piece.currentRow() +", "+ piece.currentCol());
                        System.out.println("Current piece num: " + piece.pieceNumber());
                        System.out.println("------------------------------------------------");
                        currentRow = e.getY() / TileSize;
                        currentCol = e.getX() / TileSize;
                        markMovableTile(currentRow, currentCol);
                        repaint();
                    } else {
                        int tileRow = e.getY() / TileSize;
                        int tileCol = e.getX() / TileSize;
                        Tile gotoTile = board.getTile(tileRow, tileCol);
                        board.movePiece(currentRow, currentCol, gotoTile);
                        repaint();
                    }
                }
            });
            repaint();
        }
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    paintTile(g, row, col);
                    paintPiece(g, row, col);
                }
            }
        }

        private void paintTile(Graphics g, int row, int col) {
            int x = col * TileSize;
            int y = row * TileSize;
            Tile tile = board.getTile(row, col);
            g.setColor(Color.black);
            if(tile.isInPlay()) {
                g.drawImage(imageTileBlack, x, y, TileSize, TileSize, null, null);
                if (tile.canMove()) {
                    g.drawImage(imageMoveableTile, x, y, TileSize, TileSize, null, null); 
                }
            } else {
                g.drawImage(imageTileWhite, x, y, TileSize, TileSize, null, null);
            }
        }

        private void markMovableTile(int row, int col) {
            Piece piece = board.getPiece(row, col);
            if(piece.isWhite()){
                if (col == 0) {
                    Tile frontRightTile = board.getTile(row+1, col+1);
                    if (!frontRightTile.hasPiece()) {
                        frontRightTile.setEnableMove(true);
                        if (piece.isKing()) {
                            Tile backRightTile = board.getTile(row - 1, col + 1);
                            if (!backRightTile.hasPiece()) {
                                backRightTile.setEnableMove(true);
                            }
                        }
                    } else {
                        Piece checkPiece = board.getPiece(row+1, col+1);
                        if (!checkPiece.isWhite()) {
                            Tile nextFrontRightTile = board.getTile(row+2, col+2);
                            if (!nextFrontRightTile.hasPiece()) {
                                nextFrontRightTile.setEnableMove(true);
                            }

                            if (piece.isKing()) {
                                Tile backRightTile = board.getTile(row - 1, col + 1);
                                if (backRightTile.hasPiece()) {
                                    Tile nextBackRightTile = board.getTile(row - 2, col + 2);
                                    if (!nextBackRightTile.hasPiece()) {
                                        nextBackRightTile.setEnableMove(true);
                                    }
                                }
                            }
                        }
                    }
                } else if (col == 7) {
                    Tile frontLeftTile = board.getTile(row+1, col-1);
                    if (!frontLeftTile.hasPiece()) {
                        frontLeftTile.setEnableMove(true);
                        if (piece.isKing()) {
                            Tile backLeftTile = board.getTile(row - 1, col - 1);
                            if (!backLeftTile.hasPiece()) {
                                backLeftTile.setEnableMove(true);
                            }
                        }
                    } else {
                        Piece checkPiece = board.getPiece(row+1, col-1);
                        if (!checkPiece.isWhite()) {
                            Tile nextFrontLeftTile = board.getTile(row+2, col-2);
                            if (!nextFrontLeftTile.hasPiece()) {
                                nextFrontLeftTile.setEnableMove(true);
                                if (piece.isKing()) {
                                    Tile backLeftTile = board.getTile(row - 1, col - 1);
                                    if (backLeftTile.hasPiece()) {
                                        Tile nextBackLeftTile = board.getTile(row - 2, col - 2);
                                        if (!nextBackLeftTile.hasPiece()) {
                                            nextBackLeftTile.setEnableMove(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Tile frontLeftTile = board.getTile(row+1, col-1);
                    Tile frontRightTile = board.getTile(row+1, col+1);
                    if (!frontRightTile.hasPiece() && !frontLeftTile.hasPiece()){
                        frontLeftTile.setEnableMove(true);
                        frontRightTile.setEnableMove(true);
                        if (piece.isKing()) {
                            Tile backLeftTile = board.getTile(row - 1, col - 1);
                            Tile backRightTile = board.getTile(row - 1, col + 1);
                            if (!backLeftTile.hasPiece() && !backRightTile.hasPiece()) {
                                backLeftTile.setEnableMove(true);
                                backRightTile.setEnableMove(true);
                            }
                        }
                    } else if  (!frontRightTile.hasPiece() && frontLeftTile.hasPiece()){
                        Piece checkPiece = board.getPiece(row+1, col-1);
                        if (!checkPiece.isWhite()) {
                            Tile nextFrontLeftTile = board.getTile(row+2, col-2);
                            if (!nextFrontLeftTile.hasPiece()) {
                                nextFrontLeftTile.setEnableMove(true);
                                frontRightTile.setEnableMove(true);
                                if (piece.isKing()) {
                                    Tile backLeftTile = board.getTile(row - 1, col - 1);
                                    Tile backRightTile = board.getTile(row - 1, col + 1);
                                    if (!backLeftTile.hasPiece() && !backRightTile.hasPiece()) {
                                        backLeftTile.setEnableMove(true);
                                        backRightTile.setEnableMove(true);
                                    }
                                }
                            } 
                        }
                        frontRightTile.setEnableMove(true);
                    } else if (frontRightTile.hasPiece() && !frontLeftTile.hasPiece()) {
                        Piece checkPiece = board.getPiece(row+1, col+1);
                        if (!checkPiece.isWhite()) {
                            Tile nextFrontRightTile = board.getTile(row+2, col+2);
                            if (!nextFrontRightTile.hasPiece()) {
                                frontLeftTile.setEnableMove(true);
                                nextFrontRightTile.setEnableMove(true);
                            }
                        }
                        frontLeftTile.setEnableMove(true);
                    } else if (frontRightTile.hasPiece() && frontLeftTile.hasPiece()) {
                        Tile nextFrontLeftTile = board.getTile(row+2, col-2);
                        Tile nextFrontRightTile = board.getTile(row+2, col+2);
                        Piece checkPiece1 = board.getPiece(row+2, col-2);
                        Piece checkPiece2 = board.getPiece(row+2, col+2);
                        if (!checkPiece1.isWhite() && !checkPiece2.isWhite()) {
                            nextFrontLeftTile.setEnableMove(true);
                            nextFrontRightTile.setEnableMove(true);
                        }
                    }
                }
            } else {
                if (col == 0) {
                    Tile backRightTile = board.getTile(row - 1, col + 1);
                    if (!backRightTile.hasPiece()) {
                        backRightTile.setEnableMove(true);
                    } else {
                        Piece checkPiece = board.getPiece(row - 1, col + 1);
                        if (checkPiece.isWhite()) {
                            Tile nextBackRightTile = board.getTile(row - 2, col + 2);
                            if (!nextBackRightTile.hasPiece()) {
                                nextBackRightTile.setEnableMove(true);
                            }
                        }
                    }
                } else if (col == 7) {
                    Tile backLeftTile = board.getTile(row - 1, col - 1);
                    if (!backLeftTile.hasPiece()) {
                        backLeftTile.setEnableMove(true);
                    } else {
                        Piece checkPiece = board.getPiece(row - 1, col - 1);
                        if (checkPiece.isWhite()) {
                            Tile nextBackLeftTile = board.getTile(row - 2, col - 2);
                            if (!nextBackLeftTile.hasPiece()) {
                                nextBackLeftTile.setEnableMove(true);
                            }
                        }
                    }   
                } else {
                    Tile backLeftTile = board.getTile(row - 1, col - 1);
                    Tile backRightTile = board.getTile(row - 1, col + 1);
                    if (!backLeftTile.hasPiece() && !backRightTile.hasPiece()){
                        backLeftTile.setEnableMove(true);
                        backRightTile.setEnableMove(true);
                    } else if  (!backLeftTile.hasPiece() && backRightTile.hasPiece()){
                        Piece checkPiece = board.getPiece(row - 1, col + 1);
                        if (checkPiece.isWhite()) {
                            Tile nextBackRightTile = board.getTile(row - 2, col + 2);
                            if (!nextBackRightTile.hasPiece()) {
                                backLeftTile.setEnableMove(true);
                                nextBackRightTile.setEnableMove(true);
                            } 
                        }
                        backLeftTile.setEnableMove(true);
                    } else if (backLeftTile.hasPiece() && !backRightTile.hasPiece()) {
                        Piece checkPiece = board.getPiece(row - 1, col - 1);
                        if (checkPiece.isWhite()) {
                            Tile nextBackLeftTile = board.getTile(row - 2, col - 2);
                            if (!nextBackLeftTile.hasPiece()) {
                                nextBackLeftTile.setEnableMove(true);
                                backRightTile.setEnableMove(true);
                            }
                        }
                        backRightTile.setEnableMove(true);
                    } 
                    else if (backLeftTile.hasPiece() && backRightTile.hasPiece()) {
                        Tile nextFrontLeftTile = board.getTile(row+2, col-2);
                        Tile nextFrontRightTile = board.getTile(row+2, col+2);
                        Piece checkPiece1 = board.getPiece(row+2, col-2);
                        Piece checkPiece2 = board.getPiece(row+2, col+2);
                        if (!checkPiece1.isWhite() && !checkPiece2.isWhite()) {
                            nextFrontLeftTile.setEnableMove(true);
                            nextFrontRightTile.setEnableMove(true);
                        }
                    }
                }
            }
        }

        private void ResetTiles() {
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    Tile tile = board.getTile(row, col);
                    tile.setEnableMove(false);
                }
            }
        }

        private void paintPiece(Graphics g, int row, int col) {
            int x = col * TileSize;
            int y = row * TileSize;
            g.setColor(Color.black);
            Tile tile = board.getTile(row, col);
            Piece piece = board.getPiece(row, col);
            if (tile.hasPiece()) {
                if (piece.isWhite() && piece.isAlive()) {
                    g.drawImage(imagePieceWhite, x, y, TileSize, TileSize, null, null);
                    if (piece.isKing()) {
                        g.drawImage(imageKingPieceWhite, x, y, TileSize, TileSize, null, null);
                    }
                } else if (!piece.isWhite() && piece.isAlive()){
                    g.drawImage(imagePieceBlack, x, y, TileSize, TileSize, null, null);
                    if (piece.isKing()) {
                        g.drawImage(imageKingPieceBlack, x, y, TileSize, TileSize, null, null);
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        new Game();
    } //
}

