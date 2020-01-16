package life.community.community.mappers;


import life.community.community.entity.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (notifier, receiver, outer_id, type, status) values (#{ notifier }, #{ receiver }, #{ outerId }, #{ type }, #{ status })")
    void addNewNotification(Notification notification);

    @Select("select * from notification where receiver = #{ id } and status = 0")
    List<Notification> getNotificationsByReceiver(Integer id);

    @Select("select count(id) from notification where receiver = #{ id } and status = 0")
    Integer getCountByReceiverId(Integer id);

    @Update("update notification set notifier = #{ notifier }, receiver = #{ receiver }, outer_id = #{ outerId }, type = #{ type }, status = #{ status } where receiver = #{ receiver }")
    void updateNotifications(Notification notification);

    @Update("update notification set status = 1 where id = #{ id }")
    void updateNotificationStatusToReaded(Integer id);
}
