package life.community.community.dto;


import life.community.community.entity.Comment;
import life.community.community.entity.Question;
import life.community.community.entity.User;
import lombok.Data;
import java.util.Date;

@Data
public class NotificationDto {
    private Integer id;
    private Date gmtCreate;
    private Integer status;
    private Integer type;
    private User user;
    private Question question;
    private Comment comment;
    private String notifyType;
}
