import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class PawnRace {

    public static void main (String[] args) {
        // Check arguments are valid
        String[] files = {"a","b","c","d","e","f","g","h"};
        String[] players = {"c","h"};
        assert Arrays.asList(players).contains(args[0].toLowerCase());
        assert Arrays.asList(players).contains(args[1].toLowerCase());
        assert Arrays.asList(files).contains(args[2].toLowerCase());
        assert Arrays.asList(files).contains(args[3].toLowerCase());

        // Initialise variables
        Board board = new Board(args[2].charAt(0), args[3].charAt(0));
        Game game = new Game(board);
        Player white;
        Player black;

        // Create new players
        if (Objects.equals(args[0], "H") || Objects.equals(args[0], "h")) {
            white = new Player(game, false);
        } else {
            white = new Player(game, true);
        }
        if (Objects.equals(args[1], "H") || Objects.equals(args[1], "h")) {
            black = new Player(game, false);
        } else {
            black = new Player(game, true);
        }

        // Play game
        while (!game.isFinished()) {
            board.display();
            if (game.getCurrentPlayer() == Color.WHITE) {
                System.out.println("White to play!");
                makeTurn(white, game);
                System.out.println("[White chose the move " + game.getLastMove().getSAN() + "]");
            } else {
                System.out.println("Black to play!");
                makeTurn(black, game);
                System.out.println("[Black chose the move " + game.getLastMove().getSAN() + "]");
            }
        }

        // Return results
        board.display();
        if (game.getGameResult() == Color.WHITE) {
            System.out.println("Congratulations, white!");
        }
        if (game.getGameResult() == Color.BLACK) {
            System.out.println("Congratulations, black!");
        }
        if (game.getGameResult() == Color.NONE) {
            System.out.println("Nice try! There was no winner this round...");
        }
    }

    private static void makeTurn(Player player, Game game) {
        if (player.isComputerPlayer()) {
            System.out.println("Let me think...");
            player.makeMove();
        } else {
            System.out.println("Your turn! Choose a move...");
            Move move = getMove(game);
            game.applyMove(move);
        }
    }

    private static Move getMove(Game game) {
        while (true) {
            // Get input
            Scanner scanner = new Scanner(System.in);
            String moveInput = scanner.next();

            // Check validity
            ArrayList<Move> moves = new ArrayList<>();
            for (Move validMove : game.getAllValidMoves()) {
                String san = validMove.getSAN();
                if (san.contains(moveInput)) {
                    moves.add(validMove);
                }
            }

            // 'Debugging' code
            if (moveInput.equals("debug")) {
                System.out.println("Some 'debugging' information: Here are the moves that I'll let you play this turn:");
                for (Move validMove : game.getAllValidMoves()) {
                    System.out.println(validMove.getSAN());
                }
            }
            if (moveInput.equals("undo")) {
                System.out.println("Some 'debugging' information: One move has been undone:");
                game.unapplyMove();
                game.getBoard().display();
            }
            if (moveInput.equals("undo2")) {
                System.out.println("Some 'debugging' information: Two moves have been undone:");
                game.unapplyMove();
                game.unapplyMove();
                game.getBoard().display();
            }
            if (moveInput.equals("ai")) {
                System.out.println("Some 'debugging' information: Why not choose this move:");
                AI ai = new AI(game, game.getCurrentPlayer());
                Move move = ai.alphaBeta(20);
            }

            // Return valid input or ask for a new input
            if (moves.size() == 1) {
                return moves.get(0);
            }
            System.out.println("I don't know of any moves like that! Choose a move...");
        }
    }

}