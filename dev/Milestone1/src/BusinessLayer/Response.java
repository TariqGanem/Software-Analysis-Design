package BusinessLayer;

public class Response {
    private String errMsg;

    public Response(String msg) {
        errMsg = msg;
    }

    public boolean errorOccured() {
        return errMsg != null;
    }

    public String getMsg() {
        return errMsg;
    }
}