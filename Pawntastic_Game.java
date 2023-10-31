import java.util.ArrayList;
import java.util.List;

public class Pawntastic_Game {
    private Pawntastic_Board board;
    private int currentPlayerIndex; // 0 for white and 1 for black
    private final char[] currentPlayerPieces;
    int boardSize = 0;
    // Initializes game with chosen game size and whether player chose to start with white or black
    public Pawntastic_Game(int boardSize, boolean choseWhite) {
        this.board = new Pawntastic_Board(boardSize);
        this.boardSize = boardSize;
        currentPlayerPieces = new char[] {'X', 'O'};
        this.currentPlayerIndex = 0;
    }
    // Get method to see whether its white or blacks turn
    public char getCurrentPlayerPiece(){
        return currentPlayerPieces[currentPlayerIndex];
    }
    // Get method to get board size
    public int getBoardSize(){
        return boardSize;
    }
    // Get method to get board
    public Pawntastic_Board getBoard(){
        return board;
    }
    // Switches player from white <-> black
    public void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }
    // Index of current player turn pointing to chess piece array
    public int getCurrentPlayerIndex(){
        return currentPlayerIndex;
    }
    // Generates all possible moves for the current player
    public List<Pawntastic_Move> generateAllMovesForCurrentPlayer() {
        List<Pawntastic_Move> allMoves = new ArrayList<>();
        char currentPlayerPiece = getCurrentPlayerPiece(); // Get the current player's piece

        // Iterate through the entire board to find the pieces of the current player
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Pawntastic_Location currentLocation = new Pawntastic_Location(row, col, boardSize);
                char piece = board.getValue(currentLocation);

                if (piece == currentPlayerPiece) {
                    List<Pawntastic_Move> pieceMoves = generateMovesForPiece(currentLocation);
                    allMoves.addAll(pieceMoves);
                }
            }
        }
        return allMoves;
    }
    // Generates all possible valid moves for the given piece
    public List<Pawntastic_Move> generateMovesForPiece(Pawntastic_Location l) {
        List<Pawntastic_Move> moveList = new ArrayList<>();

        char piece = board.getValue(l); // Get piece we are observing possible moves

        if (piece == 'O') {
            int row = l.getRow();
            int col = l.getCol();

            // Check if the pawn can move one square forward
            if (row < boardSize - 1 && board.getValue(new Pawntastic_Location(row + 1, col,boardSize)) == ' ') {
                Pawntastic_Location nextLocation = new Pawntastic_Location(row + 1, col,boardSize);
                Pawntastic_Move validMove = new Pawntastic_Move(l, nextLocation);
                moveList.add(validMove); // Add the valid move to the list
            }
            // Check if pawn can move diagonally left
            if (row < boardSize - 1 && col < boardSize - 1 && board.getValue(new Pawntastic_Location(row + 1, col + 1,boardSize)) == 'X'){
                Pawntastic_Location nextLocation = new Pawntastic_Location(row + 1, col + 1,boardSize);
                Pawntastic_Move validMove = new Pawntastic_Move(l, nextLocation);
                moveList.add(validMove); // Add the valid move to the list
            }
            // Check if pawn can move diagonally right
            if (row < boardSize - 1 && col > 0 && board.getValue(new Pawntastic_Location(row + 1, col - 1,boardSize)) == 'X'){
                Pawntastic_Location nextLocation = new Pawntastic_Location(row + 1, col - 1,boardSize);
                Pawntastic_Move validMove = new Pawntastic_Move(l, nextLocation);
                moveList.add(validMove); // Add the valid move to the list
            }
            // Check if pawn can move two spaces forward
            if (row == 1 && board.getValue(new Pawntastic_Location(row + 1, col,boardSize)) == ' ' && board.getValue(new Pawntastic_Location(row + 2, col,boardSize)) == ' '){
                Pawntastic_Location nextLocation = new Pawntastic_Location(row + 2, col,boardSize);
                Pawntastic_Move validMove = new Pawntastic_Move(l, nextLocation);
                moveList.add(validMove); // Add the valid move to the list
            }
        }
        else{
            int row = l.getRow();
            int col = l.getCol();

            // Check if the pawn can move one square forward
            if (row > 0 && board.getValue(new Pawntastic_Location(row - 1, col,boardSize)) == ' ') {
                Pawntastic_Location nextLocation = new Pawntastic_Location(row - 1, col,boardSize);
                Pawntastic_Move validMove = new Pawntastic_Move(l, nextLocation);
                moveList.add(validMove); // Add the valid move to the list
            }
            // Check if the pawn can move diagonally right
            if (row > 0 && col < boardSize - 1 && board.getValue(new Pawntastic_Location(row - 1, col + 1,boardSize)) == 'O'){
                Pawntastic_Location nextLocation = new Pawntastic_Location(row - 1, col + 1,boardSize);
                Pawntastic_Move validMove = new Pawntastic_Move(l, nextLocation);
                moveList.add(validMove); // Add the valid move to the list
            }
            // Check if pawn can move diagonally left
            if (row > 0 && col > 0 && board.getValue(new Pawntastic_Location(row - 1, col - 1,boardSize)) == 'O'){
                Pawntastic_Location nextLocation = new Pawntastic_Location(row - 1, col - 1,boardSize);
                Pawntastic_Move validMove = new Pawntastic_Move(l, nextLocation);
                moveList.add(validMove); // Add the valid move to the list
            }
            // Check if pawn can move two spaces forward
            if (row == boardSize - 2 && board.getValue(new Pawntastic_Location(row - 1, col,boardSize)) == ' ' && board.getValue(new Pawntastic_Location(row - 2, col,boardSize)) == ' '){
                Pawntastic_Location nextLocation = new Pawntastic_Location(row - 2, col,boardSize);
                Pawntastic_Move validMove = new Pawntastic_Move(l, nextLocation);
                moveList.add(validMove); // Add the valid move to the list
            }
        }
        return moveList;
    }
    // Makes proposed move if valid otherwise prompts the user to enter a new move
    public void makeMove(Pawntastic_Move move) {
        if (isValid(move)) {
            board.makeMove(move);
            switchPlayer();
        }
        else {
            System.out.println("Please enter a valid move");
        }
    }
    // Determines if game is in a terminal state
    public boolean isGameOver() {
        // Check if white won
        for (int i = 0; i<boardSize; i++){
            if (board.getValue(new Pawntastic_Location(0,i,boardSize)) == 'X'){
                return true;
            }
        }
        // Check if black won
        for (int i = 0; i<boardSize; i++){
            if (board.getValue(new Pawntastic_Location(boardSize - 1 ,i,boardSize)) == 'O'){
                return true;
            }
        }
         return generateAllMovesForCurrentPlayer().isEmpty();
    }
    // Evaluate terminal states and assign a value of 1 if current player would win, -1 if current player would lose and 0 if draw
    public int evaluateBoard(String color){
        if (color.equals("white")) {
            for (int i = 0; i < boardSize; i++) {
                if (board.getValue(new Pawntastic_Location(0, i,boardSize)) == 'X') {
                    return 1;
                }
            }
            for (int i = 0; i < boardSize; i++) {
                if (board.getValue(new Pawntastic_Location(boardSize - 1, i,boardSize)) == 'O') {
                    return -1;
                }
            }
        }
        else{
            for (int i = 0; i < boardSize; i++) {
                if (board.getValue(new Pawntastic_Location(0, i,boardSize)) == 'X') {
                    return -1;
                }
            }
            for (int i = 0; i < boardSize; i++) {
                if (board.getValue(new Pawntastic_Location(boardSize - 1, i,boardSize)) == 'O') {
                    return 1;
                }
            }
        }
        return 0;
    }
    // Checks whether a move is valid
    public boolean isValid(Pawntastic_Move m) {
        List<Pawntastic_Move> allMoves = generateAllMovesForCurrentPlayer();
        return allMoves.contains(m);
    }

    // Creates a new instance of our game to be used in minimax
    public Pawntastic_Game copy() {
        Pawntastic_Game copy = new Pawntastic_Game(boardSize, currentPlayerIndex == 0);

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                copy.getBoard().setValue(new Pawntastic_Location(row, col,boardSize), this.board.getValue(new Pawntastic_Location(row, col,boardSize)));
            }
        }

        copy.currentPlayerIndex = this.currentPlayerIndex;
        return copy;
    }
}