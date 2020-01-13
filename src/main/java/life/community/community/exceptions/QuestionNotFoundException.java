package life.community.community.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    private String message;
    private Integer code;

    public QuestionNotFoundException(ICustomizeErrorCode iCustomizeErrorCode) {
        this.code = iCustomizeErrorCode.getCode();
        message = iCustomizeErrorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}