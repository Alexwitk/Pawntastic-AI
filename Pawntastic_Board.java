public class Pawntastic_Board {
    private char[][] board;

    // Initializes the board with dimensions of nxn
    public Pawntastic_Board(int n) {
        this.board = create_Board(n);
    }
    // Prints current board position
    public void print_Board() {
        // Space unicode character used so board lines up better
        String space = "     ";
        int n = board.length;
        // Create header for all the columns
        StringBuilder labels = new StringBuilder();
        for (int i = 0; i < n; i++) {
            labels.append((char) ('a' + i)).append("           ");
        }
        StringBuilder divider = new StringBuilder();
        divider.append("+-+-+-+-+-+-".repeat(n));
        // Print the board
        System.out.println(space + space + labels);
        System.out.println(space + divider);
        for (int i = n; i >= 1; i--) {
            System.out.print(i + space.substring(0,space.length() - 1) + "|");
            for (int j = 0; j < n; j++) {
                System.out.print(space + board[n - i][j] + space + "|");
            }
            System.out.print(space + i + "\n");
            System.out.print(space);
            System.out.print(divider);
            // Print column labels above the gaps
            System.out.print("    ");
            System.out.println();
        }
        System.out.println(space + space + labels);
    }
    // Get method for board
    public char[][] getBoard() {
        return board;
    }
    // Creates our board with black pawns at 2nd highest row and white pawns at 2nd lowest rows
    private char[][] create_Board(int n) {
        char[][] newBoard = new char[n][n];

        // Initialize the board with spaces
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = ' ';
            }
        }

        // Fill the top row with black pawns
        for (int i = 0; i < n; i++) {
            newBoard[1][i] = 'O';
        }

        // Fill the bottom row with white pawns
        for (int i = 0; i < n; i++) {
            newBoard[n - 2][i] = 'X';
        }

        return newBoard;
    }
    // Returns the current piece occupying that location in the board
    public char getValue(Pawntastic_Location l){
        return board[l.getRow()][l.getCol()];
    }
    // Sets a certain location in the board to a new piece
    public void setValue(Pawntastic_Location l, char c){
        board[l.getRow()][l.getCol()] = c;
    }
    // Moves pawn to destination and sets previously occupied square to a space
    public void makeMove(Pawntastic_Move move) {
        // Temp variable to store value
        char pawn = board[move.getFrom().getRow()][move.getFrom().getCol()];
        board[move.getFrom().getRow()][move.getFrom().getCol()] = ' ';
        board[move.getTo().getRow()][move.getTo().getCol()] = pawn;
    }
}