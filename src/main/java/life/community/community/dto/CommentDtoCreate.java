package life.community.community.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 闯将评论时传输到服务端的对象
 */
@Data
public class CommentDtoCreate implements Serializable {
    /**
     * 评论对象的ID（评论或者问题）
     */
    private Integer parentId;
    /**
     * 评论的内容
     */
    private String context;
    /**
     * 评论对象的类型
     */
    private Integer type;
}
