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
        private Image imagePieceWhite;
        private Image imageKingPieceWhite;
        private Image imagePieceBlack;
        private Image imageKingPieceBlack;
        private static int currentRow;
        private static int currentCol;

        public GameUI() {
            setPreferredSize(new Dimension(boardSize * TileSize, boardSize * TileSize));
            setTitle("Checker");
            imageTileWhite = new ImageIcon("imgs/TileWhite.png").getImage();
            imageTileBlack = new ImageIcon("imgs/TileBlack.png").getImage();
            imagePieceWhite = new ImageIcon("imgs/WhitePiece.png").getImage();
            imageKingPieceWhite = new ImageIcon("imgs/KingWhitePiece.png").getImage();
            imagePieceBlack = new ImageIcon("imgs/BlackPiece.png").getImage();
            imageKingPieceBlack = new ImageIcon("imgs/KingBlackPiece.png").getImage();

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
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
                        currentRow = e.getY() / TileSize;
                        currentCol = e.getX() / TileSize;
                    } else {
                        int tileRow = e.getY() / TileSize;
                        int tileCol = e.getX() / TileSize;
                        Tile gotoTile = board.getTile(tileRow, tileCol);
                        board.movePiece(currentRow, currentCol, gotoTile);
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
            } else {
                g.drawImage(imageTileWhite, x, y, TileSize, TileSize, null, null);
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
                } else if (!piece.isWhite() && piece.isAlive()){
                    g.drawImage(imagePieceBlack, x, y, TileSize, TileSize, null, null);
                }
            }
        }
    }
    public static void main(String[] args) {
        new Game();
    } //
}

