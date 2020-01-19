package life.community.community.mappers;


import life.community.community.entity.Comment;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 新建评论
     * @param comment   评论的实体类
     */
    @Insert("insert into comment (parent_id, type, commentator, comments) values(#{ parentId }, #{ type }, #{ commentator }, #{ comments })")
    void addNewComment(Comment comment);

    /**
     * 根据问题的ID获取该问题的所有一级评论
     * @param parentId      问题的ID
     * @return              一级评论列表
     */
    @Select("select * from comment where type = 1 and parent_id = #{ parentId } order by gmt_create desc")
    List<Comment> getFirstLevelReviewByQuestionId(Integer parentId);

    /**
     * 根据评论的ID获取指定一级评论的二级评论
     * @param parentId      一级评论的ID
     * @return              二级评论列表
     */
    @Select("select * from comment where type = 2 and parent_id = #{ parentId }")
    List<Comment> getSecondaryReviewByParentId(Integer parentId);

    /**
     * 获得指定ID的评论
     * @param id    评论的ID
     * @return      评论实体
     */
    @Select("select * from comment where id = #{ id }")
    Comment getCommentById(Integer id);

    /**
     * 更新一条评论
     * @param comment   评论的实体类
     */
    @Update("update comment set comment_count = #{ commentCount }, like_count = #{ likeCount }, comments = #{ comments }, gmt_modify = now() where id = #{ id }")
    void updateCommentById(Comment comment);
}
