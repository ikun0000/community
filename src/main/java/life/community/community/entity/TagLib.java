package life.community.community.entity;

import lombok.Data;

/**
 * 标签表的实体类
 */
@Data
public class TagLib {
    /**
     * 标签的ID
     */
    private Integer id;
    /**
     * 标签的类型，暂时未使用
     */
    private Integer type;
    /**
     * 背签的内容
     */
    private String tagName;
}
