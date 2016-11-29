import java.util.Random;

public class Player {

    private Game game;
    private boolean isComputerPlayer;

    public Player(Game game, boolean isComputerPlayer) {
        this.game = game;
        this.isComputerPlayer = isComputerPlayer;
    }

    boolean isComputerPlayer() {
        return isComputerPlayer;
    }

    void makeMove() {
        if (isComputerPlayer) {
            Random rand = new Random();
            int randomChoice = rand.nextInt(game.getAllValidMoves().size() - 1);
            Move move = game.getAllValidMoves().get(randomChoice);
            game.applyMove(move);
        }
    }

}
