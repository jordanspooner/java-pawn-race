class AI{

    private Game game;
    private Color player;

    public AI(Game game, Color player) {
        this.game = game;
        this.player = player;
    }

    Move alphaBeta(int depth) {
        int bestScore = - 1000000000;
        Move bestMove = null;
        for (Move validMove : game.getAllValidMoves()) {
            game.applyMove(validMove);
            int score = alphaBetaHelper(depth, - 1000000000, 1000000000);
            game.unapplyMove();
            if (score > bestScore) {
                bestMove = validMove;
                bestScore = score;
            }
            System.out.println("Score for the move " + validMove.getSAN() + " was " + score);
        }
        System.out.println("Choosing the move " + bestMove.getSAN());
        return bestMove;
    }

    private int alphaBetaHelper(int depth, int alpha, int beta) {
        if (depth == 0 || game.isFinished()) {
            return value();
        }
        if (player == game.getCurrentPlayer()) {
            for (Move validMove : game.getAllValidMoves()) {
                game.applyMove(validMove);
                alpha = Math.max(alpha, alphaBetaHelper(depth - 1, alpha, beta));
                game.unapplyMove();
                if (beta <= alpha) {
                    break;
                }
            }
            return alpha;
        }
        else {
            for (Move validMove : game.getAllValidMoves()) {
                game.applyMove(validMove);
                beta = Math.min(beta, alphaBetaHelper(depth - 1, alpha, beta));
                game.unapplyMove();
                if (beta <= alpha) {
                    break;
                }
            }
            return beta;
        }
    }

    private int value() {
        if (game.isFinished()) {
            Color winner = game.getCurrentPlayer();
            if (winner == player) {
                return 1000000;
            } else if (winner == Color.opposite(player)) {
                return -1000000;
            } else {
                return 0;
            }
        } else {
            int tally = 0;
            for (Square pawn : game.getBoard().getWPawns()) {
                int y = pawn.getY();
                tally += y;
            }
            for (Square pawn : game.getBoard().getBPawns()) {
                int y = 7 - pawn.getY();
                tally -= y;
            }
            if (player == Color.WHITE) {
                return tally;
            } else {
                return (- tally);
            }
        }
    }

}