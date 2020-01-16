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

    public List<Notification> getNotificationsByReceiverId(Integer id) {
        List<Notification> notificationList = notificationMapper.getNotificationsByReceiver(id);

        if (notificationList != null) {
            return  notificationList;
        }

        return new ArrayList<Notification>();
    }

    public List<NotificationDto> getNotificationDtosByReceiverId(Integer id) {
        List<Notification> notificationList = getNotificationsByReceiverId(id);
        List<NotificationDto> result = new ArrayList<NotificationDto>();
        NotificationDto notificationDto = null;


        for (Notification notification : notificationList) {
            notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification, notificationDto);
            notificationDto.setUser(userMapper.getUserById(notification.getNotifier()));

            if (notification.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()) {
                notificationDto.setNotifyType(NotificationTypeEnum.REPLY_COMMENT.getName());
                notificationDto.setComment(commentMapper.getCommentById(notification.getOuterId()));
                notificationDto.setQuestion(questionMapper.getQuestionById(commentMapper.getCommentById(notification.getOuterId()).getParentId()));
            } else {
                notificationDto.setNotifyType(NotificationTypeEnum.REPLY_QUESTION.getName());
                notificationDto.setQuestion(questionMapper.getQuestionById(notification.getOuterId()));
            }

            result.add(notificationDto);
            notification.setStatus(NotificationStatusEnum.READ.getStatus());
        }

        return result;
    }

    public Integer getNotificationCountByReceiverId(Integer id) {
        return notificationMapper.getCountByReceiverId(id);
    }

    public void setNotificationReadedById(Integer id) {
        notificationMapper.updateNotificationStatusToReaded(id);
    }
}
