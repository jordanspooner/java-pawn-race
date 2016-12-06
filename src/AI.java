import java.util.ArrayList;

class AI{

    private Game game;
    private Color player;

    public AI(Game game, Color player) {
        this.game = game;
        this.player = player;
    }

    Move alphaBeta(double depthFactor) {
        int bestScore = - 1000000000;
        Move bestMove = null;
        ArrayList<Move> validMoves = game.getAllValidMoves();
        double complexity = validMoves.size();
        int depth = (int)(depthFactor / Math.log(complexity));
        System.out.println("Deciding to do " + depth + " iterations");
        for (Move validMove : validMoves) {
            game.applyMove(validMove);
            int score = alphaBetaHelper(depth, - 1000000000, 1000000000);
            game.unapplyMove();
            if (score > bestScore) {
                bestMove = validMove;
                bestScore = score;
            }
            System.out.print(validMove.getSAN() + " " + score + " | ");
            if (bestScore == 1000000) {
                break;
            }
        }
        System.out.println("Choose " + bestMove.getSAN());
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
        Color winner = game.getGameResult();
        if (winner == player) {
            return 1000000;
        } else if (winner == Color.opposite(player)) {
            return -1000000;
        } else {
            ArrayList<Square> wPawns = game.getBoard().getWPawns();
            ArrayList<Square> bPawns = game.getBoard().getBPawns();
            int tally = 0;
            for (Square pawn : wPawns) {
                int x = pawn.getX();
                int y = pawn.getY();
                boolean passed = true;
                for (Square oppPawn : bPawns) {
                    int oppX = oppPawn.getX();
                    int oppY = oppPawn.getY();
                    if (Math.abs(x - oppX) <= 1) {
                        if (y < oppY) {
                            passed = false;
                        }
                    }
                }
                tally += y;
                if (passed) {
                    tally += 100;
                }
            }
            for (Square pawn : bPawns) {
                int x = pawn.getX();
                int y = 7 - pawn.getY();
                boolean passed = true;
                for (Square oppPawn : wPawns) {
                    int oppX = oppPawn.getX();
                    int oppY = 7 - oppPawn.getY();
                    if (Math.abs(x - oppX) <= 1) {
                        if (y < oppY) {
                            passed = false;
                        }
                    }
                }
                tally -= y;
                if (passed) {
                    tally -= 100;
                }
            }
            if (player == Color.WHITE) {
                return tally;
            } else {
                return (- tally);
            }
        }
    }

}