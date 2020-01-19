package life.community.community.enums;

/**
 * 评论类型的枚举
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean exists(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (type == commentTypeEnum.getType()) {
                return true;
            }
        }
        return false;
    }


    public Integer getType() {
        return type;
    }
}
