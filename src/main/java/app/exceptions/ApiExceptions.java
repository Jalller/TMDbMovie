package app.exceptions;

public class ApiExceptions extends RuntimeException {
    private int code;

    public ApiExceptions(int code, String msg){
        super(msg);
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
