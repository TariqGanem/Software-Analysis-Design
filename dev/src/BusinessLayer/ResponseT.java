package BusinessLayer;

public class ResponseT<T> extends Response {
    private T value;

    public ResponseT(String errorMessage) {
        super(errorMessage);
    }

    public ResponseT(T value) {
        super();
        this.value = value;
    }

    public ResponseT(String errorMessage, T value) {
        super(errorMessage);
        this.value = value;
    }
}
