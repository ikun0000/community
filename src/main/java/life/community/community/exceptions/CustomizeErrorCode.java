package life.community.community.exceptions;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "问题没有找到"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中问题回复"),
    NO_LOGIN(2003, "未登录不能进行评论，请先登录"),
    TYPE_PARAM_WARN(2004, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2005, "评论没有找到");

    private Integer code;
    private String message;


    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
