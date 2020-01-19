package life.community.community.entity;


import lombok.Data;
import java.util.Date;

/**
 * 问题表的实体类
 */
@Data
public class Question {
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
     * 问题创建时间
     */
    private Date gmtCreate;
    /**
     * 问题修改的时间
     */
    private Date gmtModify;
    /**
     * 问题的发起者ID
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
     * 点赞数
     */
    private Integer likeCount;
    /**
     * 问题的标签
     */
    private String tag;
}
