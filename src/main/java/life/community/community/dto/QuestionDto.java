package life.community.community.dto;

import life.community.community.entity.User;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


/**
 * 向前端展示的问题对象
 */
@Data
public class QuestionDto implements Serializable {
    /**
     * 问题的ID
     */
    private Integer id;
    /**
     * 问题的标题
     */
    private String title;
    /**
     * 问题的描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModify;
    /**
     * 提问用户的ID
     */
    private Integer creator;
    /**
     * 被评论数
     */
    private Integer commentCount;
    /**
     * 被阅读数
     */
    private Integer viewCount;
    /**
     * 被点赞数
     */
    private Integer likeCount;
    /**
     * 问题的标签
     */
    private String tag;
    /**
     * 提问用户
     */
    private User user;
}
