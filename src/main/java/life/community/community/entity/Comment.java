package life.community.community.entity;


import lombok.Data;
import java.util.Date;

/**
 * 评论表的实体类
 */
@Data
public class Comment {
    /**
     * 评论的ID
     */
    private Integer id;
    /**
     * 评论对象的ID
     */
    private Integer parentId;
    /**
     * 评论的类型
     */
    private Integer type;
    /**
     * 评论发起者的ID
     */
    private Integer commentator;
    /**
     * 创建评论的时间
     */
    private Date gmtCreate;
    /**
     * 修改评论的时间
     */
    private Date gmtModify;
    /**
     * 点赞人数
     */
    private Integer likeCount;
    /**
     * 评论内容
     */
    private String comments;
    /**
     * 被评论数
     */
    private Integer commentCount;
}
