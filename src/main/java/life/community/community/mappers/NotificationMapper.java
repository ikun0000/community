package life.community.community.mappers;


import life.community.community.entity.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface NotificationMapper {

    /**
     * 添加一条通知
     * @param notification      通知的实体类
     */
    @Insert("insert into notification (notifier, receiver, outer_id, type, status) values (#{ notifier }, #{ receiver }, #{ outerId }, #{ type }, #{ status })")
    void addNewNotification(Notification notification);

    /**
     * 根据通知接收者ID返回接收者是它的通知
     * @param id        通知接收者的ID
     * @return          这个人接收的通知列表
     */
    @Select("select * from notification where receiver = #{ id } and status = 0")
    List<Notification> getNotificationsByReceiver(Integer id);

    /**
     * 返回指定接收用户的未读通知数
     * @param id        接收通知的用户ID
     * @return          未读通知数
     */
    @Select("select count(id) from notification where receiver = #{ id } and status = 0")
    Integer getCountByReceiverId(Integer id);

    /**
     * 更新一个通知的状态
     * @param id        通知的ID
     */
    @Update("update notification set status = 1 where id = #{ id }")
    void updateNotificationStatusToReaded(Integer id);
}
