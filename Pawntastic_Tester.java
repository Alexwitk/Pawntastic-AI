import java.util.List;
import java.util.Scanner;

public class Pawntastic_Tester {
    public static void main(String[] args) {
        // Get user input for number of rows
        Scanner input = new Scanner(System.in);
        System.out.print("""
                Choose your game:
                4. Tiny 4x4 Pawntastic
                5. Very small 5x5 Pawntastic
                6. Small 6x6 Pawntastic
                8. Standard 8x8 Pawntastic
                10. Jumbo 10x10 Pawntastic
                Or enter any size >= 4 to play that game
                Your choice:\s""");
        int n = input.nextInt();
        input.nextLine();
        System.out.print("Do you want to start with black (b) or white (w): ");
        char c = input.next().charAt(0);
        input.nextLine();

        Pawntastic_Game g = new Pawntastic_Game(n, c == 'b'); // Pass true for white, false for black
        Pawntastic_Player comp;
        Pawntastic_Player human;
        Pawntastic_Player cur_Player;

        if (c == 'w') {
            human = new Pawntastic_Player( "Human",'X', true);
            comp = new Pawntastic_Player("Computer",'O', false);
            cur_Player = human;
        } else {
            human = new Pawntastic_Player("Human",'O', true);
            comp = new Pawntastic_Player( "Computer",'X', false);
            cur_Player = comp;
        }

        System.out.print("""
                Choose your opponent:\s
                1. Opponent with minimax
                2. Opponent with hminimax
                Your choice?\s""");
        int val = input.nextInt();
        input.nextLine();

        MiniMax mini = null;
        HMinimax hmini = null;
        int depth = 0;
        if (val == 1) {
            mini = new MiniMax();
        } else if (val == 2) {
            hmini = new HMinimax();
            System.out.println("Input a depth value suggested value is " + n);
            depth = input.nextInt();
            input.nextLine();
        } else {
            System.out.println("Invalid choice. Exiting.");
            return;
        }

        g.getBoard().print_Board();
        while (!g.isGameOver()) {
            List<Pawntastic_Move> moves = g.generateAllMovesForCurrentPlayer();
            Pawntastic_Move selectedMove = null;
            if (cur_Player == human) {
                System.out.println("Enter what move you would like to make ex: (a1 a3): ");
                // Splits input string into two parts
                String proposed_Move = input.nextLine();
                String[] player_Move = proposed_Move.split(" ");
                // Checks if valid move
                if (player_Move.length == 2 && proposed_Move.length() == 5) {
                    String from_Square = player_Move[0];
                    String to_Square = player_Move[1];
                    selectedMove = new Pawntastic_Move(
                            new Pawntastic_Location(n - Integer.parseInt(from_Square.substring(1, 2)), from_Square.charAt(0) - 'a', n),
                            new Pawntastic_Location(n - Integer.parseInt(to_Square.substring(1, 2)), to_Square.charAt(0) - 'a', n));
                } else {
                    System.out.println("Invalid input please try again: ");
                    continue;
                }
            } else {
                System.out.println("Calculating move ...");
                // Performs minimax or h-minimax based on previous input
                if (val == 1){
                    selectedMove = mini.getBestMove(g, (cur_Player.getPieceColor() == 'X') ? "white" : "black");
                }
                else{
                    selectedMove = hmini.getBestMove(g, (cur_Player.getPieceColor() == 'X') ? "white" : "black", 6);
                }
            }
            // Make the selected move
            if (g.isValid(selectedMove)) {
                g.makeMove(selectedMove);
            } else {
                System.out.println("Please enter a valid move: ");
                continue;
            }
            System.out.println(cur_Player.getName() + " move is " + selectedMove);
            g.getBoard().print_Board();
            // If player has no moves then its a draw
            if (g.generateAllMovesForCurrentPlayer().isEmpty()){
                System.out.println("Game is a draw!");
                break;
            }
            // Otherwise if it's a game over the current player won
            if (g.isGameOver()){
                System.out.println(cur_Player.getName() + " wins ");
            }
            cur_Player = (cur_Player.equals(human)) ? comp : human;
        }

    }
}