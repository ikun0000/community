package life.community.community.services;


import life.community.community.dto.QuestionDto;
import life.community.community.entity.Question;
import life.community.community.entity.User;
import life.community.community.mappers.QuestionMapper;
import life.community.community.mappers.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//            questionDto.setCommentCount(question.getCommentCount());
//            questionDto.setCreator(question.getCreator());
//            questionDto.setDescription(question.getDescription());
//            questionDto.setLikeCount(question.getLikeCount());
//            questionDto.setTag(question.getTag());
//            questionDto.setTitle(question.getTitle());
//            questionDto.setViewCount(question.getViewCount());
//            questionDto.setGmtCreate(question.getGmtCreate());
//            questionDto.setGmtModify(question.setGmtModify());
//            questionDto.setId(question.getId());
            BeanUtils.copyProperties(question, questionDto);
            user = userMapper.getUserById(question.getCreator());
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        return questionDtoList;
    }

}
