package kr.khlee.icecreamhaggendazs.exceptions;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class MyException extends Exception {

    private HttpStatus status;

    public MyException(String message){
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public MyException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }
}
