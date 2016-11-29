import java.util.ArrayList;
import java.util.Arrays;

class Board {

    private Square[][] board = new Square[8][8];
    private ArrayList<Square> wPawns = new ArrayList<>();
    private ArrayList<Square> bPawns = new ArrayList<>();

    Board(Character whiteGap, Character blackGap) {
        int whiteGapIndex = getIndex(whiteGap);
        int blackGapIndex = getIndex(blackGap);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board[x][y] = new Square(x, y);
                if (y == 0 && x != whiteGapIndex) {
                    board[x][y].setOccupier(Color.WHITE);
                    wPawns.add(board[x][y]);
                }
                if (y == 7 && x != blackGapIndex) {
                    board[x][y].setOccupier(Color.BLACK);
                    bPawns.add(board[x][y]);
                }
            }
        }
    }

    private int getIndex (char gap) {
        Character[] files = {'a','b','c','d','e','f','g','h'};
        assert Arrays.asList(files).contains(Character.toLowerCase(gap));
        for (int i = 0; i < 8; i++) {
            if (files[i] == Character.toLowerCase(gap)) {
                return i;
            }
        }
        return 8;
    }

    ArrayList<Square> getWPawns() {
        return wPawns;
    }

    ArrayList<Square> getBPawns() {
        return bPawns;
    }

    Square getSquare(int x, int y) {
        return board[x][y];
    }

    void applyMove(Move move) {
        int fromX = move.getFrom().getX();
        int fromY = move.getFrom().getY();
        int toX = move.getTo().getX();
        int toY = move.getTo().getY();
        Color movingColor = move.getFrom().occupiedBy();

        if (movingColor == Color.WHITE) {
            // EnPassant capture
            if (move.isEnPassantCapture()) {
                board[toX][fromY].setOccupier(Color.NONE);
                bPawns.remove(board[toX][fromY]);
            }

            // Standard capture
            if (move.isCapture() && !move.isEnPassantCapture()) {
                bPawns.remove(board[toX][toY]);
            }

            // All moves
            board[fromX][fromY].setOccupier(Color.NONE);
            board[toX][toY].setOccupier(Color.WHITE);
            wPawns.remove(board[fromX][fromY]);
            wPawns.add(board[toX][toY]);
        } else {
            // EnPassant capture
            if (move.isEnPassantCapture()) {
                board[toX][fromY].setOccupier(Color.NONE);
                wPawns.remove(board[toX][fromY]);
            }

            // Standard capture
            if (move.isCapture() && !move.isEnPassantCapture()) {
                wPawns.remove(board[toX][toY]);
            }

            // All moves
            board[fromX][fromY].setOccupier(Color.NONE);
            board[toX][toY].setOccupier(Color.BLACK);
            bPawns.remove(board[fromX][fromY]);
            bPawns.add(board[toX][toY]);
        }
    }

    void display() {
        System.out.println("       A  B  C  D  E  F  G  H      \n");
        for (int y = 7; y >= 0; y--) {
            System.out.print(y + 1);
            System.out.print("      ");
            for (int x = 0; x < 8; x++) {
                if (board[x][y].occupiedBy() == Color.BLACK) {
                    System.out.print("B  ");
                }
                if (board[x][y].occupiedBy() == Color.WHITE) {
                    System.out.print("W  ");
                }
                if (board[x][y].occupiedBy() == Color.NONE) {
                    System.out.print(".  ");
                }
            }
            System.out.print("    ");
            System.out.println(y + 1);
        }
        System.out.println("\n       A  B  C  D  E  F  G  H       ");
    }

}
