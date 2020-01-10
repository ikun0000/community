package life.community.community.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GitHubUser implements Serializable {
    private String id;
    private String name;
    private String bio;
    private String avatarUrl;
}
