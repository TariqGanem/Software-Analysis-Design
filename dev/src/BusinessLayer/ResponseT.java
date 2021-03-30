package BusinessLayer;

public class ResponseT<T> extends Response {
    private T value;

    public ResponseT(Exception ex) {
        super(ex);
        this.value = null;
    }

    public ResponseT(T value) {
        super();
        this.value = value;
    }

    public ResponseT(Exception ex, T value) {
        super(ex);
        this.value = value;
    }
}
