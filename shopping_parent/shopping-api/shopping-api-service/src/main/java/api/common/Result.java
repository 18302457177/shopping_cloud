package api.common;

import api.code.ApiCode;
import lombok.Data;

@Data
public class Result<T> {

    private int code;
    private T data;
    private String message;

    public Result() {
    }
    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }


    public Result(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static Result fail(String message){
        return new Result(ApiCode.FAIL_CONTANT,message);
    }

    public static <T> Result fail(T data){
        return new Result(ApiCode.FAIL_CONTANT,data);
    }
    public static <T> Result success(T data){
        return new Result(ApiCode.SUCCESS_CONTANT,data);
    }
    public static <T> Result success(){
        return new Result(ApiCode.SUCCESS_CONTANT,null);
    }

    public static <T> Result success(T data,String message){
        return new Result(ApiCode.SUCCESS_CONTANT,data,message);
    }
}
