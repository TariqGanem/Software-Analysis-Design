package BusinessLayer.ShipmentsModule;

public class Response {
    private String errMsg;

    public Response() {
    }

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