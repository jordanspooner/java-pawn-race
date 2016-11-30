import java.util.ArrayList;

class Game {

    private Board board;
    private Color currentPlayer;
    private ArrayList<Move> moves = new ArrayList<>();

    Game(Board board) {
        this.board = board;
        currentPlayer = Color.WHITE;
    }

    Board getBoard() {
        return board;
    }

    Color getCurrentPlayer() {
        return currentPlayer;
    }

    Move getLastMove() {
        return moves.get(moves.size() - 1);
    }

    void applyMove (Move move) {
        moves.add(move);
        board.applyMove(move);
        currentPlayer = Color.opposite(currentPlayer);
    }

    void unapplyMove() {
        currentPlayer = Color.opposite(currentPlayer);
        Move move = getLastMove();
        board.unapplyMove(move);
        moves.remove(moves.size() - 1);
    }

    boolean isFinished() {
        // Winner exists
        if (getGameResult() != Color.NONE){
            return true;
        }

        // No possible moves
        return getAllValidMoves().size() == 0;
    }

    Color getGameResult() {
        // White pawn reaches final rank
        for (Square wPawn : board.getWPawns()) {
            if (wPawn.getY() == 7) {
                return Color.WHITE;
            }
        }

        // Black pawn reaches final rank
        for (Square bPawn : board.getBPawns()) {
            if (bPawn.getY() == 0) {
                return Color.BLACK;
            }
        }

        // All white pawns captured
        if (board.getWPawns().size() == 0) {
            return Color.WHITE;
        }

        // All black pawns captured
        if (board.getBPawns().size() == 0) {
            return Color.BLACK;
        }

        // No possible moves
        return Color.NONE;
    }

    ArrayList<Move> getAllValidMoves() {
        ArrayList<Move> validMoves = new ArrayList<>();
        int u = 1;
        if (currentPlayer == Color.BLACK) {
            u = -1;
        }

        for (Square pawn : getAllPawns()) {
            int x = pawn.getX();
            int y = pawn.getY();

            // Standard forwards move
            if (board.getSquare(x, y + u).occupiedBy() == Color.NONE) {
                validMoves.add(new Move(pawn, board.getSquare(x, y + u), false, false));
                if ((y == 0 || y == 7) && board.getSquare(x, y + 2 * u).occupiedBy() == Color.NONE) {
                    validMoves.add(new Move(pawn, board.getSquare(x, y + 2 * u), false, false));
                }
            }

            // Standard capture
            if (x != 7) {
                if (board.getSquare(x + 1, y + u).occupiedBy() == Color.opposite(currentPlayer)) {
                    validMoves.add(new Move(pawn, board.getSquare(x + 1, y + u), true, false));
                }
            }
            if (x != 0) {
                if (board.getSquare(x - 1, y + u).occupiedBy() == Color.opposite(currentPlayer)) {
                    validMoves.add(new Move(pawn, board.getSquare(x - 1, y + u), true, false));
                }
            }

            // EnPassant capture
            if (currentPlayer == Color.WHITE && y == 5 || currentPlayer == Color.BLACK && y == 2) {
                int lastX = getLastMove().getFrom().getX();
                int lastY = getLastMove().getFrom().getY();
                int thisY = getLastMove().getTo().getY();
                // Last play was double forwards by black - white might be able to make enpassant capture
                if (lastY == 7 && thisY == 5) {
                    if (x != 7) {
                        if (lastX == x + 1) {
                            validMoves.add(new Move(pawn, board.getSquare(x + 1, 6), true, true));
                        }
                    }
                    if (x != 0) {
                        if (lastX == x - 1) {
                            validMoves.add(new Move(pawn, board.getSquare(x - 1, 6), true, true));
                        }
                    }
                }
                // Last play was double forwards by white - black might be able to make enpassant capture
                if (lastY == 0 && thisY == 2) {
                    if (x != 7) {
                        if (lastX == x + 1) {
                            validMoves.add(new Move(pawn, board.getSquare(x + 1, 1), true, true));
                        }
                    }
                    if (x != 0) {
                        if (lastX == x - 1) {
                            validMoves.add(new Move(pawn, board.getSquare(x - 1, 1), true, true));
                        }
                    }
                }
            }
        }
        return validMoves;
    }

    private ArrayList<Square> getAllPawns() {
        if (currentPlayer == Color.WHITE) {
            return board.getWPawns();
        } else {
            return board.getBPawns();
        }
    }

}
