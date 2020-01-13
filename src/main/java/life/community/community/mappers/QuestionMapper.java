package life.community.community.mappers;

import life.community.community.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title, description, creator, comment_count, view_count, like_count, tag) values (#{title}, #{description}, #{creator}, #{commentCount}, #{viewCount}, #{likeCount}, #{tag})")
    void addNewQuestion(Question question);

    @Select("select * from question order by gmt_create desc")
    List<Question> getAllQuestion();

    @Select("select * from question where creator = #{ userid }")
    List<Question> getUserQuestions(Integer userid);

    @Select("select count(id) from question where creator = #{ userid }")
    int getUserQuestionCount(Integer userid);

    @Select("select count(id) from question")
    int getItemCount();

    @Select("select * from question order by gmt_create desc limit #{ offset }, 5")
    List<Question> getQuestionFromIndex(Integer offset);

    @Select("select * from question where creator = #{ userid } order by gmt_create desc limit #{ offset }, 5")
    List<Question> getUserQuestionFromIndex(Integer userid, Integer offset);

    @Select("select * from question where id = #{ id }")
    Question getQuestionById(Integer id);

    @Update("update question set title = #{ title }, description = #{ description }, comment_count = #{ commentCount }, view_count = #{ viewCount }, like_count = #{ likeCount }, gmt_modify = now() where id = #{id}")
    void updateQuestionById(Question question);

}
