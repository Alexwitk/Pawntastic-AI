import java.util.List;

public class HMinimax {

    // Finds optimal move for the player using our heuristic minimax
    public Pawntastic_Move getBestMove(Pawntastic_Game board, String playerColor, int depth) {
        List<Pawntastic_Move> possibleMoves = board.generateAllMovesForCurrentPlayer();
        int bestEval = Integer.MIN_VALUE;
        Pawntastic_Move bestMove = null;

        for (Pawntastic_Move move : possibleMoves) {
            Pawntastic_Game newBoard = board.copy();
            newBoard.makeMove(move);

            int eval = hMinimax(newBoard, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false, playerColor);

            if (eval > bestEval) {
                bestEval = eval;
                bestMove = move;
            }
        }
        return bestMove;
    }

    // Using heuristic evaluation function with a depth limit and alpha beta pruning to decrease time costs
    private int hMinimax(Pawntastic_Game board, int depth, int alpha, int beta, boolean maximizingPlayer, String playerColor) {
        if (depth == 0 || board.isGameOver()) {
            return evaluate(board, playerColor);
        }

        List<Pawntastic_Move> possibleMoves = board.generateAllMovesForCurrentPlayer();

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;

            for (Pawntastic_Move move : possibleMoves) {
                Pawntastic_Game newBoard = board.copy();
                newBoard.makeMove(move);
                int eval = hMinimax(newBoard, depth - 1, alpha, beta, false, playerColor);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);

                if (beta <= alpha)
                    break; // Beta cut-off
            }

            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;

            for (Pawntastic_Move move : possibleMoves) {
                Pawntastic_Game newBoard = board.copy();
                newBoard.makeMove(move);
                int eval = hMinimax(newBoard, depth - 1, alpha, beta, true, playerColor);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);

                if (beta <= alpha)
                    break; // Alpha cut-off
            }

            return minEval;
        }
    }

    // Evaluation function using material, development and furthest passed pawn subfunctions
    private int evaluate(Pawntastic_Game board, String color) {
        int score = 0;
        score += countMaterial(board,color);
        String opponentColor = (color.equals("white")) ? "black" : "white";
        score += countDevelopment(board,color) - countDevelopment(board,opponentColor);
        int computerPassed = findFurthestPassedPawn(board,color);
        int opponentPassed = findFurthestPassedPawn(board,opponentColor);
        // Multiplied by 5 so it prioritizes a win more than just pushing meaningless pawns
        if (computerPassed >= opponentPassed){
            score += 5 * computerPassed;
        }
        else{
            score -= 5 * opponentPassed;
        }
        return score;
    }
    private int countMaterial(Pawntastic_Game board, String color) {
        int count = 0;
        char playerPiece = (color.equals("white")) ? 'X' : 'O';
        char opponentPiece = (color.equals("white")) ? 'O' : 'X';
        for (int row = 0; row < board.boardSize; row++) {
            for (int col = 0; col < board.boardSize; col++) {
                char cur_Val = board.getBoard().getValue(new Pawntastic_Location(row, col,board.boardSize));
                if (cur_Val == playerPiece){
                    count++;
                }
                else if (cur_Val == opponentPiece){
                    count--;
                }
            }
        }
        return count;
    }
    private int countDevelopment(Pawntastic_Game board, String color) {
        int development = 0;
        char playerPiece = (color.equals("white")) ? 'X' : 'O';

        for (int row = 0; row < board.boardSize; row++) {
            for (int col = 0; col < board.boardSize; col++) {
                char cur_Val = board.getBoard().getValue(new Pawntastic_Location(row, col, board.boardSize));
                if (cur_Val == playerPiece) {
                    development += (color.equals("white")) ? row : (board.boardSize - 1 - row);
                }
            }
        }

        return development;
    }

    public int findFurthestPassedPawn(Pawntastic_Game board, String color){
        int size = board.getBoardSize();
        if (color.equals("white")){
            for(int i = 0; i < size; i++){
                for(int j = 0; j<size; j++){
                    Pawntastic_Location cur_Location = new Pawntastic_Location(i,j,size);
                    if(board.getBoard().getValue(cur_Location) == 'X' && clear_Path(board,color,cur_Location)){
                        return size - i - 1;
                    }
                }
            }
        }
        else{
            for(int i = size - 1; i>=0; i--){
                for(int j = 0; j<size; j++){
                    Pawntastic_Location cur_Location = new Pawntastic_Location(i,j,size);
                    if(board.getBoard().getValue(cur_Location) == 'O' && clear_Path(board,color,cur_Location)){
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    // Helper function for furthest passed pawn to see if no opposing pawns are in the path of the pawn
    private boolean clear_Path(Pawntastic_Game board, String color, Pawntastic_Location location){
        int size = board.getBoardSize();
        int left = Math.max(0,location.getCol()-1);
        int right = Math.min(size-1,location.getCol() + 1);

        if(color.equals("white")){
            for (int row = location.getRow() - 1; row >= 0; row--){
                for (int col = left; col <= right; col++){
                    if (board.getBoard().getValue(new Pawntastic_Location(row,col,size)) == 'O'){
                        return false;
                    }
                }
            }
            return true;
        }
        else{
            for (int row = location.getRow() + 1; row < size; row++){
                for (int col = left; col <= right; col++){
                    if (board.getBoard().getValue(new Pawntastic_Location(row,col,size)) == 'X'){
                        return false;
                    }
                }
            }
            return true;
        }
    }
}