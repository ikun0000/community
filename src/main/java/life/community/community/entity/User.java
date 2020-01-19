package life.community.community.entity;


import lombok.Data;
import java.util.Date;

/**
 * 用户表的实体类
 */
@Data
public class User {
    /**
     * 用户的ID
     */
    private Integer id;
    /**
     * 用户的Github ID
     */
    private String accountID;
    /**
     * 用户的昵称
     */
    private String name;
    /**
     * 用户持有的令牌
     */
    private String token;
    /**
     * 用户创建时间
     */
    private Date gmtCreate;
    /**
     * 用户修改时间
     */
    private Date gmtModify;
    /**
     * 用户在Github的bio
     */
    private String bio;
    /**
     * 用户Github头像的URL
     */
    private String avatarUrl;
}
