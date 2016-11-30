// TODO Cleanup this code for a neater implementation of the alpha-beta pruning algorithm

class MoveValue{

    private Game game;
    private Color color;
    private double mvScore;
    private Move mvMove;

    public MoveValue(Game game, Color color, double score) {
        this.color = color;
        this.game = game;
        this.mvScore = score;
    }

    Move getMvMove() {
        return mvMove;
    }

    MoveValue alphaBeta(double alpha, double beta, int depth) {
        double value = 0;
        if (depth == 0 || game.isFinished()) {
            value = score();
            return new MoveValue(game, color, value);
        }
        MoveValue returnMove;
        MoveValue bestMove = null;
        if (color == game.getCurrentPlayer()) {
            for (Move currentMove : game.getAllValidMoves()) {
                game.applyMove(currentMove);
                returnMove = alphaBeta(alpha, beta, depth - 1);
                game.unapplyMove();
                if ((bestMove == null) || (bestMove.mvScore < returnMove.mvScore)) {
                    bestMove = returnMove;
                    bestMove.mvMove = currentMove;
                }
                if (returnMove.mvScore > alpha) {
                    alpha = returnMove.mvScore;
                    bestMove = returnMove;
                }
                if (beta <= alpha) {
                    bestMove.mvScore = beta;
                    bestMove.mvMove = null;
                    return bestMove;
                }
            }
            return bestMove;
        } else {
            for (Move currentMove : game.getAllValidMoves()) {
                game.applyMove(currentMove);
                returnMove = alphaBeta(alpha, beta, depth - 1);
                game.unapplyMove();
                if ((bestMove == null) || (bestMove.mvScore > returnMove.mvScore)) {
                    bestMove = returnMove;
                    bestMove.mvMove = currentMove;
                }
                if (returnMove.mvScore < beta) {
                    beta = returnMove.mvScore;
                    bestMove = returnMove;
                }
                if (beta <= alpha) {
                    bestMove.mvScore = alpha;
                    bestMove.mvMove = null;
                    return bestMove;
                }
            }
            return bestMove;
        }
    }

    private int score() {
        if (game.isFinished()) {
            if (game.getGameResult() == color) {
                return 9999;
            }
            if (game.getGameResult() == Color.opposite(color)) {
                return -9999;
            }
            return 0;
        }
        Board board = game.getBoard();
        int tally = 0;
        for (Square pawn : board.getWPawns()) {
            tally += pawn.getY() * pawn.getY();
        }
        for (Square pawn : board.getBPawns()) {
            tally -= (7 - pawn.getY()) * (7 - pawn.getY());
        }
        if (color == Color.WHITE) {
            return tally;
        }
        return (- tally);
    }

}