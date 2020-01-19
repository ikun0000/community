package life.community.community.dto;


import life.community.community.exceptions.CustomizeErrorCode;
import life.community.community.exceptions.CustomizeException;
import lombok.Data;
import java.io.Serializable;

/**
 * 使用评论API时返回的统一对象
 * @param <T>
 */
@Data
public class ResultDto<T> implements Serializable {
    /**
     * 状态码，表示成功或者什么失败了
     */
    private Integer code;
    /**
     * 成功或者失败的信息
     */
    private String message;
    /**
     * 向前端发送的数据
     * 目前只有请求二级评论时返回CommentDto列表
     */
    private T data;


    /**
     * 根据错误码和错误信息生成ResultDto
     * @param code      错误码
     * @param message   错误信息
     * @return
     */
    public static ResultDto errorOf(Integer code, String message) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }

    /**
     * 使用封装好的错误枚举生成ResultDto
     * @param noLogin   传入的错误枚举
     * @return
     */
    public static ResultDto errorOf(CustomizeErrorCode noLogin) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(noLogin.getCode());
        resultDto.setMessage(noLogin.getMessage());
        return resultDto;
    }

    /**
     * 根据异常返回ResultDto
     * @param ex        发生的异常
     * @return
     */
    public static ResultDto errorOf(CustomizeException ex) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(ex.getCode());
        resultDto.setMessage(ex.getMessage());
        return resultDto;
    }

    /**
     * 返回没有data的成功的ResultDto
     * @return
     */
    public static ResultDto successOf() {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(1000);
        resultDto.setMessage("请求成功");
        return resultDto;
    }

    /**
     * 返回携带二级评论列表的成功的ResultDto
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ResultDto successOf(T t) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(1000);
        resultDto.setMessage("请求成功");
        resultDto.setData(t);
        return  resultDto;
    }
}
