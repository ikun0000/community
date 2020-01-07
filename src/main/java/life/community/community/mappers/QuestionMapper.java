package life.community.community.mappers;

import life.community.community.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title, description, creator, comment_count, view_count, like_count, tag) values (#{title}, #{description}, #{creator}, #{commentCount}, #{viewCount}, #{likeCount}, #{tag})")
    void addNewQuestion(Question question);

    @Select("select * from question")
    List<Question> getAllQuestion();

}
