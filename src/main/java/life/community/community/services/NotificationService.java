package life.community.community.services;


import life.community.community.dto.NotificationDto;
import life.community.community.entity.Notification;
import life.community.community.enums.NotificationStatusEnum;
import life.community.community.enums.NotificationTypeEnum;
import life.community.community.mappers.CommentMapper;
import life.community.community.mappers.NotificationMapper;
import life.community.community.mappers.QuestionMapper;
import life.community.community.mappers.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;


    /**
     * 添加一条通知
     * @param type          通知类型
     * @param notifier      通知发起者
     * @param receiver      通知接收者
     * @param outerId       通知对象的ID
     */
    public void addANotification(Integer type,
                                 Integer notifier,
                                 Integer receiver,
                                 Integer outerId) {
        Notification notification = new Notification();
        notification.setType(type);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setNotifier(notifier);
        notification.setReceiver(receiver);
        notification.setOuterId(outerId);
        notificationMapper.addNewNotification(notification);
    }

    /**
     * 根据通知接收者的ID返回通知列表
     * @param id        通知接收者ID
     * @return          通知列表
     */
    public List<Notification> getNotificationsByReceiverId(Integer id) {
        List<Notification> notificationList = notificationMapper.getNotificationsByReceiver(id);

        if (notificationList != null) {
            return  notificationList;
        }

        return new ArrayList<Notification>();
    }

    /**
     * 根据通知接收者ID返回通知的传输对象列表
     * @param id        通知接收者ID
     * @return          通知传输对象列表
     */
    public List<NotificationDto> getNotificationDtosByReceiverId(Integer id) {
        List<Notification> notificationList = getNotificationsByReceiverId(id);
        List<NotificationDto> result = new ArrayList<NotificationDto>();
        NotificationDto notificationDto = null;


        for (Notification notification : notificationList) {
            notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification, notificationDto);
            notificationDto.setUser(userMapper.getUserById(notification.getNotifier()));

            if (notification.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()) {
                // 通知是评论
                // 设置评论的对象
                notificationDto.setNotifyType(NotificationTypeEnum.REPLY_COMMENT.getName());
                notificationDto.setComment(commentMapper.getCommentById(notification.getOuterId()));
                // 设置评论对象所属问题的对象
                notificationDto.setQuestion(questionMapper.getQuestionById(commentMapper.getCommentById(notification.getOuterId()).getParentId()));
            } else {
                // 通知是问题
                notificationDto.setNotifyType(NotificationTypeEnum.REPLY_QUESTION.getName());
                notificationDto.setQuestion(questionMapper.getQuestionById(notification.getOuterId()));
            }

            result.add(notificationDto);
            notification.setStatus(NotificationStatusEnum.READ.getStatus());
        }

        return result;
    }

    /**
     * 获取指定用户的未读的通知数
     * @param id        用户的ID
     * @return          未读通知数
     */
    public Integer getNotificationCountByReceiverId(Integer id) {
        return notificationMapper.getCountByReceiverId(id);
    }

    /**
     * 设置指定用户的通知状态未已读
     * @param id        用户的ID
     */
    public void setNotificationReadedById(Integer id) {
        notificationMapper.updateNotificationStatusToReaded(id);
    }
}
