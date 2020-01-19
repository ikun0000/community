package life.community.community.entity;


import lombok.Data;
import java.util.Date;

/**
 * 通知表的实体类
 */
@Data
public class Notification {
    /**
     * 通知的ID
     */
    private Integer id;
    /**
     * 通知发起者ID
     */
    private Integer notifier;
    /**
     * 通知接收者ID
     */
    private Integer receiver;
    /**
     * 通知文章或者评论的ID
     */
    private Integer outerId;
    /**
     * 通知的类型，通知文章被评论或者评论被评论
     */
    private Integer type;
    /**
     * 通知时间
     */
    private Date gmtCreate;
    /**
     * 通知的当前状态
     * 0    未读
     * 1    已读
     */
    private Integer status;
}
