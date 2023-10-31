import java.util.List;

public class MiniMax {

    // Finds best move using minimax algorithm
    public Pawntastic_Move getBestMove(Pawntastic_Game board, String playerColor) {
        List<Pawntastic_Move> possibleMoves = board.generateAllMovesForCurrentPlayer();
        int bestEval = Integer.MIN_VALUE;
        Pawntastic_Move bestMove = null;

        for (Pawntastic_Move move : possibleMoves) {
            Pawntastic_Game newBoard = board.copy(); // Create a copy of the board
            newBoard.makeMove(move); // Apply the move to the copied board
            int eval = minimax(newBoard, false, playerColor);
            if (eval > bestEval) {
                bestEval = eval;
                bestMove = move;
            }
        }

        return bestMove;
    }

    // Alternates between maximizing and minimizing utility to find optimal move
    private int minimax(Pawntastic_Game board, boolean maximizingPlayer, String playerColor) {
        // Base case: If game ends then checks utility of terminal state
        if (board.isGameOver()) {
            return board.evaluateBoard(playerColor);
        }

        List<Pawntastic_Move> possibleMoves = board.generateAllMovesForCurrentPlayer();
        int maxEval = Integer.MIN_VALUE; // Initialize to negative infinity
        int minEval = Integer.MAX_VALUE; // Initialize to positive infinity
        // Swaps between maximizing and minimizing player on each iteration
        if (maximizingPlayer) {
            for (Pawntastic_Move move : possibleMoves) {
                Pawntastic_Game newBoard = board.copy(); // Create a copy of the board so we don't have to deal with backtracking
                newBoard.makeMove(move);
                int eval = minimax(newBoard,  false, playerColor);
                maxEval = Math.max(maxEval, eval);
            }

            return maxEval;
        } else {
            for (Pawntastic_Move move : possibleMoves) {
                Pawntastic_Game newBoard = board.copy(); // Create a copy of the board so we don't have to deal with backtracking
                newBoard.makeMove(move);
                int eval = minimax(newBoard, true,playerColor);
                minEval = Math.min(minEval, eval);
            }

            return minEval;
        }
    }
}
