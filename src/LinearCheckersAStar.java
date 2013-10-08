import java.util.ArrayList;

/**
 * User: Sigurd
 * Date: 04.10.13
 * Time: 11:33
 */
public class LinearCheckersAStar extends AStar<LinearCheckersBoard> {
    private int numberOfPieces;
    private State<LinearCheckersBoard> solution;

    public LinearCheckersAStar(int searchType, int numberOfPieces) {
        super(searchType);
        this.numberOfPieces = numberOfPieces;

        LinearCheckersBoard solutionBoard = LinearCheckersBoard.getSolution(numberOfPieces);
        this.solution = new State<LinearCheckersBoard>(createStateIdentifier(solutionBoard), solutionBoard);
    }

    @Override
    protected Node createRootNode() {
        LinearCheckersBoard initialBoard = new LinearCheckersBoard(numberOfPieces);
        initialBoard.initialize();
        Node<LinearCheckersBoard> root = new Node(new State(createStateIdentifier(initialBoard), initialBoard));
        return root;
    }

    @Override
    protected double heuristicEvaluation(Node<LinearCheckersBoard> node) {
        int evaluation = 0;

        if (isSolution(node)) {
            return 0;
        }

        LinearCheckersBoard boardClone = node.getState().getObject().clone();
        Integer[] board = boardClone.getBoard();

        int redPos = 0;
        int bluePos = board.length;

        for (int i = 0; i < board.length; i++) {
            if (board[i] != null) {
                if (board[i].equals(LinearCheckersBoard.PIECE_RED)) {
                    evaluation += (bluePos - i) / 2;
                    evaluation += (bluePos - i) % 2;
                    bluePos--;
                } else if (board[i].equals(LinearCheckersBoard.PIECE_BLUE)) {
                    evaluation += (i - redPos) / 2;
                    evaluation += (i - redPos) % 2;
                    redPos++;
                }
            }
        }

        return evaluation;
    }

    @Override
    protected ArrayList<Node<LinearCheckersBoard>> generateSuccessorNodes(Node<LinearCheckersBoard> node) {
        ArrayList<Node<LinearCheckersBoard>> successors = new ArrayList<Node<LinearCheckersBoard>>();
        Integer[] board = node.getState().getObject().getBoard();

        int emptyIndex = 0;

        for (; emptyIndex < board.length; emptyIndex++) {
            if (board[emptyIndex] == null) {
                break;
            }
        }

        if (((emptyIndex - 1) >= 0) && board[emptyIndex - 1].equals(LinearCheckersBoard.PIECE_RED)) {
            LinearCheckersBoard newBoard = node.getState().getObject().clone();
            newBoard.movePiece((emptyIndex - 1), emptyIndex);
            Node<LinearCheckersBoard> successor = new Node<LinearCheckersBoard>(new State<LinearCheckersBoard>(createStateIdentifier(newBoard), newBoard));
            successors.add(successor);
        }

        if (((emptyIndex + 1) < board.length) && board[emptyIndex + 1].equals(LinearCheckersBoard.PIECE_BLUE)) {
            LinearCheckersBoard newBoard = node.getState().getObject().clone();
            newBoard.movePiece((emptyIndex + 1), emptyIndex);
            Node<LinearCheckersBoard> successor = new Node<LinearCheckersBoard>(new State<LinearCheckersBoard>(createStateIdentifier(newBoard), newBoard));
            successors.add(successor);
        }

        if (((emptyIndex - 2) >= 0) && board[emptyIndex - 2].equals(LinearCheckersBoard.PIECE_RED)) {
            LinearCheckersBoard newBoard = node.getState().getObject().clone();
            newBoard.movePiece((emptyIndex - 2), emptyIndex);
            Node<LinearCheckersBoard> successor = new Node<LinearCheckersBoard>(new State<LinearCheckersBoard>(createStateIdentifier(newBoard), newBoard));
            successors.add(successor);
        }

        if ((emptyIndex + 2) < board.length && board[emptyIndex + 2].equals(LinearCheckersBoard.PIECE_BLUE)) {
            LinearCheckersBoard newBoard = node.getState().getObject().clone();
            newBoard.movePiece((emptyIndex + 2), emptyIndex);
            Node<LinearCheckersBoard> successor = new Node<LinearCheckersBoard>(new State<LinearCheckersBoard>(createStateIdentifier(newBoard), newBoard));
            successors.add(successor);
        }

        return successors;
    }

    @Override
    protected int createStateIdentifier(LinearCheckersBoard object) {
        Integer[] board = object.getBoard();
        StringBuilder stringBuilder = new StringBuilder();

        for (Integer i : board) {
            if (i != null) {
                if (i.equals(LinearCheckersBoard.PIECE_RED)) {
                    stringBuilder.append("R");
                } else if (i.equals(LinearCheckersBoard.PIECE_BLUE)) {
                    stringBuilder.append("B");
                }
            } else {
                stringBuilder.append("E");
            }
        }


        return stringBuilder.toString().hashCode();
    }

    @Override
    protected double arcCost(Node<LinearCheckersBoard> parent, Node<LinearCheckersBoard> child) {
        return 0.983;
    }

    @Override
    protected boolean isSolution(Node<LinearCheckersBoard> node) {
        return node.getState().equals(solution);
    }
}
