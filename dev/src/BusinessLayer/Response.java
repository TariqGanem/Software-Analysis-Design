package BusinessLayer;

public class Response {
    private String errorMessage;

    public Response() {
        errorMessage = null;
    }

    public Response(String msg) {
        this.errorMessage = msg;
    }

    public boolean getErrorOccurred() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}