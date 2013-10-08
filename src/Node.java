import java.util.ArrayList;

/**
 * User: Sigurd
 * Date: 03.10.13
 * Time: 09:49
 */
public class Node<T> implements Comparable<Node> {
    private State<T> state;
    private double g;
    private double h;
    private double f;
    private boolean open;
    private Node<T> parent;
    private ArrayList<Node<T>> kids;

    public Node(State<T> state, double g, double h, double f, boolean open, Node<T> parent, ArrayList<Node<T>> kids) {
        this.state = state;
        this.g = g;
        this.h = h;
        this.f = f;
        this.open = open;
        this.parent = parent;
        this.kids = kids;
    }

    public Node(State<T> state) {
        this(state, 0, 0, 0, true, null, new ArrayList<Node<T>>());
    }

    public State<T> getState() {
        return state;
    }

    public void setState(State<T> state) {
        this.state = state;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF(double g, double h) {
        this.f = g + h;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public ArrayList<Node<T>> getKids() {
        return kids;
    }

    public void setKids(ArrayList<Node<T>> kids) {
        this.kids = kids;
    }

    public void addKid(Node<T> kid) {
        this.kids.add(kid);
    }

    @Override
    public int compareTo(Node o) {
        if (o.getF() > this.getF()) {
            return -1;
        } else if (o.getF() < this.getF()) {
            return 1;
        }

        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        Node<T> objNode = (Node<T>) obj;

        if (this.getState().equals(objNode.getState())) {
            return true;
        }

        return false;
    }
}
