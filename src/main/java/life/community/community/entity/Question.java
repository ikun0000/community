package life.community.community.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Question {
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
}
