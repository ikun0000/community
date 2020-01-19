package life.community.community.dto;


import life.community.community.entity.User;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


/**
 * 给前端传送的评论
 */
@Data
public class CommentDto implements Serializable {
    /**
     * 评论的ID
     */
    private Integer id;
    /**
     * 评论的父级的ID
     */
    private Integer parentId;
    /**
     * 这个评论的类型
     */
    private Integer type;
    /**
     * 发起这条评论的用户ID
     */
    private Integer commentator;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModify;
    /**
     * 点赞数
     */
    private Integer likeCount;
    /**
     * 评论的内容
     */
    private String comments;
    /**
     * 被评论数
     */
    private Integer commentCount;
    /**
     * 发起这条评论的用户
     */
    private User user;
}
