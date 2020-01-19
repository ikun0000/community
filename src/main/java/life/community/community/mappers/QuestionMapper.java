package life.community.community.mappers;


import life.community.community.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface QuestionMapper {

    /**
     * 添加一个提问
     * @param question      问题的实体
     */
    @Insert("insert into question (title, description, creator, comment_count, view_count, like_count, tag) values (#{title}, #{description}, #{creator}, #{commentCount}, #{viewCount}, #{likeCount}, #{tag})")
    void addNewQuestion(Question question);

    /**
     * 获取所有的问题，并以创建时间倒序返回
     * @return              问题列表
     */
    @Select("select * from question order by gmt_create desc")
    List<Question> getAllQuestion();

    /**
     * 获取指定创建者的通知
     * @param userid        提问者ID
     * @return              问题列表
     */
    @Select("select * from question where creator = #{ userid }")
    List<Question> getUserQuestions(Integer userid);

    /**
     * 根据创建者获取它提问的问题数
     * @param userid        提问者ID
     * @return              问题数量
     */
    @Select("select count(id) from question where creator = #{ userid }")
    int getUserQuestionCount(Integer userid);

    /**
     * 获得所哟问题的问题数
     * @return              问题数量
     */
    @Select("select count(id) from question")
    int getItemCount();

    /**
     * 获取标题匹配的文章的数量
     * @param search        标题匹配的正则表达式
     * @return              问题数量
     */
    @Select("select count(id) from question where title regexp #{ search }")
    int getItemCountBySearchQuestion(String search);

    /**
     * 获取问题列表但返回指定偏移的5个问题
     * @param offset        偏移量
     * @return              问题列表
     */
    @Select("select * from question order by gmt_create desc limit #{ offset }, 5")
    List<Question> getQuestionFromIndex(Integer offset);

    /**
     * 获取指定提问者的问题的指定偏移的5个问题
     * @param userid        提问者ID
     * @param offset        偏移量
     * @return              问题列表
     */
    @Select("select * from question where creator = #{ userid } order by gmt_create desc limit #{ offset }, 5")
    List<Question> getUserQuestionFromIndex(Integer userid, Integer offset);

    /**
     * 获取指定ID的问题
     * @param id        问题的ID
     * @return          问题的实体
     */
    @Select("select * from question where id = #{ id }")
    Question getQuestionById(Integer id);

    /**
     * 根据问题的标签找到匹配到的问题，并且不返回被匹配到的但指定ID的问题
     * @param reg       匹配标签的正则表达式
     * @param id        要排除的问题ID
     * @return          问题列表
     */
    @Select("select * from question where tag regexp #{ reg } and id != #{ id }")
    List<Question> getQuestionsByRegexpAndExceptId(String reg, Integer id);

    /**
     * 更新问题
     * @param question  问题的实体
     */
    @Update("update question set title = #{ title }, description = #{ description }, comment_count = #{ commentCount }, view_count = #{ viewCount }, like_count = #{ likeCount }, gmt_modify = now() where id = #{id}")
    void updateQuestionById(Question question);

    /**
     * 获取匹配到标题的问题并返回指定偏移的5个问题
     * @param offset    偏移量
     * @param search    标题的正则表达式
     * @return          问题列表
     */
    @Select("select * from question where title regexp #{ search } limit #{ offset }, 5")
    List<Question> getQuestionFromIndexAndSearch(Integer offset, String search);
}
