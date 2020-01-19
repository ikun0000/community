package life.community.community.dto;


import lombok.Data;
import java.io.Serializable;

/**
 * 使用access token向Github获取用户信息时封装的对象
 */
@Data
public class GitHubUser implements Serializable {
    /**
     * Github上用户的ID
     */
    private String id;
    /**
     * Github上用户的昵称
     */
    private String name;
    /**
     * Github上用户的bio
     */
    private String bio;
    /**
     * Github上用户的头像地址
     */
    private String avatarUrl;
}
