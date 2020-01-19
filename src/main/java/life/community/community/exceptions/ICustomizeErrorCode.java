package life.community.community.exceptions;


/**
 * 用户异常的统一接口
 */
public interface ICustomizeErrorCode {
    String getMessage();
    Integer getCode();
}
