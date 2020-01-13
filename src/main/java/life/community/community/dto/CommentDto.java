package life.community.community.dto;

import life.community.community.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Date gmtCreate;
    private Date gmtModify;
    private Integer likeCount;
    private String comments;
    private User user;
}
