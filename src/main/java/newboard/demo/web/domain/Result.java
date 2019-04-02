package newboard.demo.web.domain;

import lombok.Getter;

@Getter
public class Result {
    private boolean success;

    Result(){
        this(true,"");
    }
    Result(boolean success,String errorMessage){
        this.success=success;
        this.errorMessage=errorMessage;
    }
    private String errorMessage;

    public static Result fail(String errorMessage){
        return  new Result(false,errorMessage);
    }
    public static Result ok(){
        return new Result();
    }

}
