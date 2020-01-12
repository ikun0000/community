package life.community.community.exceptions;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;


    public CustomizeException(ICustomizeErrorCode iCustomizeErrorCode) {
        message = iCustomizeErrorCode.getMessage();
        code = iCustomizeErrorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
