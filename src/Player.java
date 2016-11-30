// import java.util.Random;

public class Player {

    private Game game;
    private boolean isComputerPlayer;

    Player(Game game, boolean isComputerPlayer) {
        this.game = game;
        this.isComputerPlayer = isComputerPlayer;
    }

    boolean isComputerPlayer() {
        return isComputerPlayer;
    }

    // AI Selection
    void makeMove() {
        if (isComputerPlayer) {
            MoveValue ai = new MoveValue(game, game.getCurrentPlayer(), 0);
            Move move = ai.alphaBeta(-10000, 10000, 6).getMvMove();
            game.applyMove(move);
        }
    }

//    // Random Selection
//    void makeMove() {
//        if (isComputerPlayer) {
//            Random rand = new Random();
//            int randomChoice = rand.nextInt(game.getAllValidMoves().size() - 1);
//            Move move = game.getAllValidMoves().get(randomChoice);
//            game.applyMove(move);
//        }
//    }

}