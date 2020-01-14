package life.community.community.mappers;

import life.community.community.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id, type, commentator, comments) values(#{ parentId }, #{ type }, #{ commentator }, #{ comments })")
    void addNewComment(Comment comment);

    @Select("select * from comment")
    List<Comment> getAllComment();

    @Select("select * from comment where type = 1")
    List<Comment> getAllFirstLevelReview();

    @Select("select * from comment where type = 1 and parent_id = #{ parentId } order by gmt_create desc")
    List<Comment> getFirstLevelReviewByQuestionId(Integer parentId);

    @Select("select * from comment where type = 2 and parent_id = #{ parentId }")
    List<Comment> getSecondaryReviewByParentId(Integer parentId);

    @Select("select * from comment where id = #{ id }")
    Comment getCommentById(Integer id);

    @Update("update comment set like_count = #{ likeCount }, comments = #{ comments }, gmt_modify = now() where id = #{ id }")
    void updateCommentById(Comment comment);

    @Delete("delete from comment where id = #{ id }")
    void deleteCommentById(Integer id);

}
