package BusinessLayer.ShipmentsModule;

public class ResponseT<T> extends Response {
    private T value;

    public ResponseT(String msg) {
        super(msg);
    }

    public ResponseT(T val) {
        value = val;
    }

    public T getValue() {
        return value;
    }
}