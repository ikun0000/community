package life.community.community.dto;

import life.community.community.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String description;
    private Date gmtCreate;
    private Date gmtModify;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
