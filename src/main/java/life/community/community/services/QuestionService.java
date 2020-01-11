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


    public List<QuestionDto> getAllQuestionAndUser() {
        List<Question> questionList = questionMapper.getAllQuestion();

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

    public List<QuestionDto> getUserQuestionAndUser(Integer userid) {
        List<Question> questionList = questionMapper.getUserQuestions(userid);

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

    public PaginationDto getPage(Integer page) {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setCurrentPage(page);         // 这个方法一定要先于setPagination调用，setPagination里面依赖currentPage
        paginationDto.setQuestionDtoList(getNumberPageData(page));

        Integer totalCount = questionMapper.getItemCount();
        paginationDto.setPagination(totalCount, page, 5);

        return paginationDto;
    }

    public PaginationDto getCurrentUserQuestion(Integer id, Integer page) {
        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setCurrentPage(page);
        paginationDto.setQuestionDtoList(getNumberPageDataFromUser(id, page));

        Integer totalCount = questionMapper.getUserQuestionCount(id);
        paginationDto.setPagination(totalCount, page, 5);

        return paginationDto;
    }

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

    public void addNewQuestionOrUpdateQuestion(Question question) {
        if (question.getId() == null) {
            questionMapper.addNewQuestion(question);
        } else {
            questionMapper.updateQuestionById(question);
        }

    }

    @Transactional
    public void incViewCount(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        int viewCount = question.getViewCount();
        question.setViewCount(viewCount + 1);
        questionMapper.updateQuestionById(question);
    }
}
