package BusinessLayer;

public class ResponseT<T> extends Response {
    private T value;

    public ResponseT(String msg) {
        super(msg);
    }

    public ResponseT(T val) {
        value = val;
    }

    public ResponseT(T val, String msg) {
        super(msg);
        value = val;
    }

    public T getValue(){
        return value;
    }
}