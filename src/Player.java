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
            AI ai = new AI(game, game.getCurrentPlayer());
            Move move = ai.alphaBeta(10);
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