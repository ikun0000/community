package life.community.community.entity;


import lombok.Data;
import java.util.Date;

@Data
public class Notification {
    private Integer id;
    private Integer notifier;
    private Integer receiver;
    private Integer outerId;
    private Integer type;
    private Date gmtCreate;
    private Integer status;
}
