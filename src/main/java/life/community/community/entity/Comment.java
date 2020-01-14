package life.community.community.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Date gmtCreate;
    private Date gmtModify;
    private Integer likeCount;
    private String comments;
    private Integer commentCount;
}
