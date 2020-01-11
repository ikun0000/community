package life.community.community.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    private String message;

    public QuestionNotFoundException(ICustomizeErrorCode iCustomizeErrorCode) {
        message = iCustomizeErrorCode.getMessage();
    }

}
