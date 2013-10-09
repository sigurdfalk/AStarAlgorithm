import java.text.DecimalFormat;
import java.util.*;

/**
 * User: Sigurd
 * Date: 03.10.13
 * Time: 10:16
 */
public abstract class AStar<T> {
    public static final int TYPE_BEST_FIRST = 0;
    public static final int TYPE_DEPTH_FIRST = 1;
    public static final int TYPE_BREADTH_FIRST = 2;

    /**
     * Specifies the search type
     */
    private int searchType;

    private ArrayList<Node<T>> open;
    private ArrayList<Node<T>> closed;
    private Hashtable<Integer, Node<T>> generatedNodes;

    public AStar(int searchType) {
        this.searchType = searchType;
        this.open = new ArrayList<Node<T>>();
        this.closed = new ArrayList<Node<T>>();
        this.generatedNodes = new Hashtable<Integer, Node<T>>();
    }

    private void initializeSearch() {
        Node<T> root = createRootNode();
        root.setG(0);
        root.setH(heuristicEvaluation(root));
        root.setF(root.getG(), root.getH());
        addToOpen(root);
        generatedNodes.put(root.getState().getIdentifier(), root);
    }

    /**
     * Performs A* search
     * @return Node representing the solution of the search
     */
    public Node<T> search() {
        initializeSearch();

        while (!open.isEmpty()) {
            Node<T> node = open.remove(0);
            closed.add(node);

            if (isSolution(node)) {
                return node;
            }

            for (Node<T> successor : generateSuccessorNodes(node)) {
                Node<T> equal = generatedNodes.get(successor.getState().getIdentifier());

                if (equal != null) {
                    successor = equal;
                } else {
                    generatedNodes.put(successor.getState().getIdentifier(), successor);
                }

                node.addKid(successor);

                if (!open.contains(successor) && !closed.contains(successor)) {
                    attachAndEvaluate(successor, node);
                    addToOpen(successor);
                } else if ((node.getG() + arcCost(node, successor)) < successor.getG()) {
                    attachAndEvaluate(successor, node);

                    if (closed.contains(successor)) {
                        propagatePathImprovements(successor);
                    }
                }
            }
        }

        throw new RuntimeException("Search failed");
    }

    private void attachAndEvaluate(Node<T> child, Node<T> parent) {
        child.setParent(parent);
        child.setG(parent.getG() + arcCost(parent, child));
        child.setH(heuristicEvaluation(child));
        child.setF(child.getG(), child.getH());
    }

    private void propagatePathImprovements(Node<T> parent) {
        for (Node<T> child : parent.getKids()) {
            if ((parent.getG() + arcCost(parent, child)) < child.getG()) {
                child.setParent(parent);
                child.setG(parent.getG() + arcCost(parent, child));
                child.setF(child.getG(), child.getH());
                propagatePathImprovements(child);
            }
        }
    }

    /**
     * Adds a Node to the OPEN-list
     * @param node Node to be added
     */
    private void addToOpen(Node<T> node) {
        switch (searchType) {
            case TYPE_BEST_FIRST:
                addBestFirst(node);
                break;
            case TYPE_DEPTH_FIRST:
                addDepthFirst(node);
                break;
            case TYPE_BREADTH_FIRST:
                addBreadthFirst(node);
                break;
            default:
                throw new UnsupportedOperationException("Search type not valid");
        }
    }

    /**
     * Adds a node to the OPEN-list and performs sort on he list
     * @param node Node to be added
     */
    private void addBestFirst(Node<T> node) {
        open.add(node);
        Collections.sort(open);
    }

    /**
     * Adds a node to the start of the OPEN-list
     * @param node Node to be added
     */
    private void addDepthFirst(Node<T> node) {
        open.add(0, node);
    }

    /**
     * Adds a node to the end of the OPEN-list
     * @param node Node to be added
     */
    private void addBreadthFirst(Node<T> node) {
        open.add(open.size(), node);
    }

    /**
     * Creates the root-node for A*
     * @return Root-node
     */
    protected abstract Node<T> createRootNode();

    /**
     * Calculates the heuristic value for a given Node
     * @param node Node to be evaluated
     * @return The heuristic value
     */
    protected abstract double heuristicEvaluation(Node<T> node);

    /**
     * Creates a list of successor nodes for a given parent Node
     * @param node Node to create successors from
     * @return List of successors
     */
    protected abstract ArrayList<Node<T>> generateSuccessorNodes(Node<T> node);

    /**
     * Creates a unique value for a given object
     * @param object Object value is to be calculated from
     * @return Unique object-value
     */
    protected abstract int createStateIdentifier(T object);

    /**
     * Calculates the cost of traveling from a parent- to a child node
     * @param parent Parent Node
     * @param child Child Node
     * @return Calculated cost
     */
    protected abstract double arcCost(Node<T> parent, Node<T> child);

    /**
     * Checks if a given Node is a solution to A* or not
     * @param node Node to be checked
     * @return Indicates solution
     */
    protected abstract boolean isSolution(Node<T> node);

    public String getResultString(Node<T> node) {
        ArrayList<Node<T>> nodes = new ArrayList<Node<T>>();
        nodes.add(0, node);

        for (Node<T> parent = node.getParent(); parent != null; parent = parent.getParent()) {
            nodes.add(0, parent);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Result of performing " + getSearchTypeString() + " search\n");

        DecimalFormat decimalFormat = new DecimalFormat("0.000");

        for (Node<T> out : nodes) {
            stringBuilder.append("\nF: " + decimalFormat.format(out.getF()) + "  \tG: " + decimalFormat.format(out.getG()) + "  \tH: " + out.getH() + "  \t" + out.getState().getObject().toString());
        }

        stringBuilder.append("\n\nNumber of moves: " + (nodes.size() - 1));
        stringBuilder.append("\nNode count: " + getNodeCount());

        return stringBuilder.toString();
    }

    public String getSearchTypeString() {
        switch (searchType) {
            case TYPE_BEST_FIRST:
                return "Best-1st";
            case TYPE_DEPTH_FIRST:
                return "Depth-1st";
            case TYPE_BREADTH_FIRST:
                return "Breadth-1st";
            default:
                return null;
        }
    }

    public int getNodeCount() {
        return open.size() + closed.size();
    }
}
