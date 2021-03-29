package BusinessLayer;

public class GenericResponse<T> extends Response {
    private T value;

    public GenericResponse(String errorMessage) {
        super(errorMessage);
    }

    public GenericResponse(T value) {
        super();
        this.value = value;
    }

    public GenericResponse(String errorMessage, T value) {
        super(errorMessage);
        this.value = value;
    }
}
