package life.community.community.dto;

import life.community.community.exceptions.CustomizeErrorCode;
import life.community.community.exceptions.CustomizeException;
import lombok.Data;

@Data
public class ResultDto<T> {
    private Integer code;
    private String message;
    private T data;


    public static ResultDto errorOf(Integer code, String message) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeErrorCode noLogin) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(noLogin.getCode());
        resultDto.setMessage(noLogin.getMessage());
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeException ex) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(ex.getCode());
        resultDto.setMessage(ex.getMessage());
        return resultDto;
    }

    public static ResultDto successOf() {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(1000);
        resultDto.setMessage("请求成功");
        return resultDto;
    }

    public static <T> ResultDto successOf(T t) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(1000);
        resultDto.setMessage("请求成功");
        resultDto.setData(t);
        return  resultDto;
    }
}
