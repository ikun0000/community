package life.community.community.entity;


import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String accountID;
    private String name;
    private String token;
    private Date gmtCreate;
    private Date gmtModify;
    private String bio;
    private String avatarUrl;
}
