package BusinessLayer;

public class Response {
    private String errorMessage;

    public Response() {
    	errorMessage = null;
    }

    public Response(Exception ex) {
        this.errorMessage = ex.getMessage();
    }

    public boolean getErrorOccurred() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}