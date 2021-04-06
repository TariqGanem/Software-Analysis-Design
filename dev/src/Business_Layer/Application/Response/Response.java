package Business_Layer.Application.Response;

public class Response<T> {
    private T value;
    private String errorMessage;

    public Response(String errorMessage){
        this.errorMessage = errorMessage;
        this.value = null;
    }

    public Response(T value){
        this.errorMessage = null;
        this.value = value;
    }

    public Response(String errorMessage, T value){
        this.errorMessage = errorMessage;
        this.value = value;
    }

    public Response() {
        this.errorMessage = null;
        this.value = null;
    }

    public boolean isError(){
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getValue(){
        return value;
    }
}
