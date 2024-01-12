package lk.ijse.dep11.lms.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException{
    private final int errorCode;
    public AppException(int errorCode){this.errorCode=errorCode;}

    public AppException(int errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AppException(int errorCode,String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public AppException(Throwable cause, int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }
}
