/**
 * User: Sigurd
 * Date: 03.10.13
 * Time: 09:27
 */
public class LinearCheckersBoard {
    public static final int PIECE_RED = 0;
    public static final int PIECE_BLUE = 1;

    /**
     * The Linear Checkers Puzzle-board containing all checkers
     */
    private Integer[] board;

    public LinearCheckersBoard(int numberOfPieces) {
        board = new Integer[numberOfPieces + 1];
    }

    /**
     * Initializes board with numberOfPieces
     */
    public void initialize() {
        int numberOfReds = board.length / 2;

        for (int i = 0; i < board.length; i++) {
            if (i < numberOfReds) {
                board[i] = PIECE_RED;
            } else if (i > numberOfReds) {
                board[i] = PIECE_BLUE;
            }
        }
    }

    public Integer[] getBoard() {
        return board;
    }

    public void setBoard(Integer[] board) {
        this.board = board;
    }

    public void movePiece(int pieceIndex, int toPosition) {
        board[toPosition] = board[pieceIndex];
        board[pieceIndex] = null;
    }

    public LinearCheckersBoard clone() {
        LinearCheckersBoard clone = new LinearCheckersBoard(board.length);
        Integer[] boardClone = new Integer[board.length];

        for (int i = 0; i < board.length; i++) {
            boardClone[i] = board[i];
        }

        clone.setBoard(boardClone);
        return clone;
    }

    public static LinearCheckersBoard getSolution(int numberOfPieces) {
        LinearCheckersBoard solution = new LinearCheckersBoard(numberOfPieces);
        Integer[] solutionBoard = solution.getBoard();

        int numberOfReds = solutionBoard.length / 2;

        for (int i = 0; i < solutionBoard.length; i++) {
            if (i < numberOfReds) {
                solutionBoard[i] = PIECE_BLUE;
            } else if (i > numberOfReds) {
                solutionBoard[i] = PIECE_RED;
            }
        }
        solution.setBoard(solutionBoard);
        return solution;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Integer i : board) {
            if (i != null) {
                if (i.equals(PIECE_RED)) {
                    stringBuilder.append("R");
                } else if (i.equals(PIECE_BLUE)) {
                    stringBuilder.append("B");
                }
            } else {
                stringBuilder.append("E");
            }

            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        Integer[] objBoard = ((LinearCheckersBoard) obj).getBoard();

        for (int i = 0; i < board.length; i++) {
            if (board[i] != objBoard[i]) {
                return false;
            }
        }

        return true;
    }
}
