/**
 * User: Sigurd
 * Date: 03.10.13
 * Time: 09:58
 */
public class State<T> {
    private int identifier;
    private T object;

    public State(int identifier, T object) {
        this.identifier = identifier;
        this.object = object;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object obj) {
        return ((State<T>) obj).getIdentifier() == this.getIdentifier();
    }
}
