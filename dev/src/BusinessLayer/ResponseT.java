package BusinessLayer;

public class ResponseT<T> extends Response {
    private T value;

    public ResponseT(String msg) {
        super(msg);
        this.value = null;
    }

    public ResponseT(T value) {
        super();
        this.value = value;
    }

    public ResponseT(String msg, T value) {
        super(msg);
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
