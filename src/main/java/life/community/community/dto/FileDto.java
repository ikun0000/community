package life.community.community.dto;


import lombok.Data;
import java.io.Serializable;

@Data
public class FileDto implements Serializable {
    /**
     * 0 | 1, 0 表示上传失败，1 表示上传成功
     */
    private Integer success;
    /**
     * 提示的信息，上传成功或上传失败及错误信息等
     */
    private String message;
    /**
     * 图片地址，上传成功时才返回
     */
    private String url;
}
