package life.community.community.dto;


import life.community.community.entity.Comment;
import life.community.community.entity.Question;
import life.community.community.entity.User;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 向前端展示的通知
 */
@Data
public class NotificationDto implements Serializable {
    /**
     * 通知的ID
     */
    private Integer id;
    /**
     * 通知创建的时间
     */
    private Date gmtCreate;
    /**
     * 通知当前状态，已读或未读
     */
    private Integer status;
    /**
     * 通知的类型，通知评论问题还是评论了评论
     */
    private Integer type;
    /**
     * 评论用户
     */
    private User user;
    /**
     * 被评论的问题，如果评论的问题，则为被评论的问题
     * 如果评论的时评论，则为被评论的所属问题
     */
    private Question question;
    /**
     * 被评论的评论，如果评论的时问题，这个为空
     */
    private Comment comment;
    /**
     * 通知的字段
     * [评论了问题]或[者评论了评论]
     */
    private String notifyType;
}
