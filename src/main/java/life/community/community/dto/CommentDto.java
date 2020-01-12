package life.community.community.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Integer parentId;
    private String context;
    private Integer type;
}
