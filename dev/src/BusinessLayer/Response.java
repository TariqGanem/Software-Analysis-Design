package BusinessLayer;

public class Response {
    private String errorMessage;

    public Response() {

    }

    public Response(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean getErrorOccurred() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
