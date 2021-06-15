package BusinessLayer.SuppliersModule.Response;

public class Response<T> {
    private final T value;
    private final String errorMessage;

    public Response(Exception exception) {
        this.errorMessage = exception.getMessage();
        this.value = null;
    }

    public Response(T value) {
        this.errorMessage = null;
        this.value = value;
    }

    public Response(String errorMessage, T value) {
        this.errorMessage = errorMessage;
        this.value = value;
    }

    public Response() {
        this.errorMessage = null;
        this.value = null;
    }

    public boolean isError() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getValue() {
        return value;
    }
}
