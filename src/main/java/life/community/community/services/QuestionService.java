package life.community.community.services;


import life.community.community.dto.PaginationDto;
import life.community.community.dto.QuestionDto;
import life.community.community.entity.Question;
import life.community.community.entity.User;
import life.community.community.mappers.QuestionMapper;
import life.community.community.mappers.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 获取指定页的问题传输对象的列表
     * @param page      页数
     * @return          问题传输对象的列表
     */
    public List<QuestionDto> getNumberPageData(Integer page) {
        List<Question> questionList = questionMapper.getQuestionFromIndex((int) Math.ceil(5.0 * (page - 1)));

        if (questionList == null) {
            return null;
        }

        List<QuestionDto> questionDtoList = new ArrayList<QuestionDto>();
        QuestionDto questionDto = null;
        User user = null;

        for (Question question : questionList) {
            questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            user = userMapper.getUserById(question.getCreator());
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        return questionDtoList;
    }

    /**
     * 获取指定的用户的指定页数的问题列表
     * @param userid        用户的ID
     * @param page          页数
     * @return              问题传输对象的列表
     */
    public List<QuestionDto> getNumberPageDataFromUser(Integer userid, Integer page) {
        List<Question> questionList = questionMapper.getUserQuestionFromIndex(userid, (int) Math.ceil(5.0 * (page - 1)));

        if (questionList == null) {
            return null;
        }

        List<QuestionDto> questionDtoList = new ArrayList<QuestionDto>();
        QuestionDto questionDto = null;
        User user = null;

        for (Question question : questionList) {
            questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            user = userMapper.getUserById(question.getCreator());
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        return questionDtoList;
    }

    /**
     * 获取指定页的页对象
     * @param page      页数
     * @return          页对象
     */
    public PaginationDto getPage(Integer page) {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setCurrentPage(page);         // 这个方法一定要先于setPagination调用，setPagination里面依赖currentPage
        paginationDto.setQuestionDtoList(getNumberPageData(page));

        Integer totalCount = questionMapper.getItemCount();
        paginationDto.setPagination(totalCount, page, 5);

        return paginationDto;
    }

    /**
     * 获取通过搜索问题的页数
     * @param page          页数
     * @param search        搜索内容
     * @return              页对象
     */
    public PaginationDto getPageBySearchQuestion(Integer page, String search) {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setCurrentPage(page);
        paginationDto.setQuestionDtoList(getNumberPageSearchDto(page, search));

        Integer totalCount = questionMapper.getItemCountBySearchQuestion(search);
        paginationDto.setPagination(totalCount, page, 5);

        return paginationDto;
    }

    /**
     * 获取指定页数和搜索的问题的问题传输列表
     * @param page          页数
     * @param search        搜索内容
     * @return              问题传输对象列表
     */
    private List<QuestionDto> getNumberPageSearchDto(Integer page, String search) {
        List<Question> questionList = questionMapper.getQuestionFromIndexAndSearch((int) Math.ceil(5.0 * (page - 1)), search);

        if (questionList == null) {
            return null;
        }

        List<QuestionDto> questionDtoList = new ArrayList<QuestionDto>();
        QuestionDto questionDto = null;
        User user = null;

        for (Question question : questionList) {
            questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            user = userMapper.getUserById(question.getCreator());
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        return questionDtoList;
    }

    /**
     * 获取指定用户的指定页数的页对象
     * @param id            用户ID
     * @param page          页数
     * @return              页对象
     */
    public PaginationDto getCurrentUserQuestion(Integer id, Integer page) {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setCurrentPage(page);
        paginationDto.setQuestionDtoList(getNumberPageDataFromUser(id, page));

        Integer totalCount = questionMapper.getUserQuestionCount(id);
        paginationDto.setPagination(totalCount, page, 5);

        return paginationDto;
    }

    /**
     * 获取指定ID的的问题传输对象
     * @param id        问题的ID
     * @return          问题传输对象
     */
    public QuestionDto getQuestionDtoByQuestionId(Integer id) {
        QuestionDto questionDto = new QuestionDto();
        Question question = questionMapper.getQuestionById(id);

        if (question != null) {
            User user = userMapper.getUserById(question.getCreator());

            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);

            return questionDto;

        }
        return null;
    }

    /**
     * 根据问题实体更新或添加问题
     * @param question      问题实体
     */
    public void addNewQuestionOrUpdateQuestion(Question question) {
        // 如果question的id为空则是新问题
        if (question.getId() == null) {
            questionMapper.addNewQuestion(question);
        } else {
            questionMapper.updateQuestionById(question);
        }

    }

    /**
     * 增加指定问题的阅读数
     * @param id        问题ID
     */
    @Transactional
    public void incViewCount(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        int viewCount = question.getViewCount();
        question.setViewCount(viewCount + 1);
        questionMapper.updateQuestionById(question);
    }

    /**
     * 增加指定问题的评论数
     * @param id        问题ID
     */
    @Transactional
    public void incCommentCount(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        int commentCount = question.getCommentCount();
        question.setCommentCount(commentCount + 1);
        questionMapper.updateQuestionById(question);
    }

    /**
     * 获取拥有相同标签的问题的问题传输对象的列表
     * @param questionDto       问题传输对象
     * @return                  问题传输对象的列表
     */
    public List<QuestionDto> getLikeTagQuestions(QuestionDto questionDto) {
        List<QuestionDto> resultQuestionDto = new ArrayList<QuestionDto>();
        List<Question> questionsByRegexpAndExceptId = questionMapper.getQuestionsByRegexpAndExceptId(questionDto.getTag().trim().replaceAll(",", "|"), questionDto.getId());

        if (questionsByRegexpAndExceptId == null) {
            return null;
        }

        QuestionDto tmpQuestionDto = null;
        for (Question question : questionsByRegexpAndExceptId) {
            tmpQuestionDto = new QuestionDto();
            BeanUtils.copyProperties(question, tmpQuestionDto);
            User user = userMapper.getUserById(question.getCreator());
            tmpQuestionDto.setUser(user);
            resultQuestionDto.add(tmpQuestionDto);
        }

        return resultQuestionDto;
    }
}
