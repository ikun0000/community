package life.community.community.controllers;


import life.community.community.dto.CommentDto;
import life.community.community.dto.QuestionDto;
import life.community.community.exceptions.CustomizeErrorCode;
import life.community.community.exceptions.QuestionNotFoundException;
import life.community.community.services.CommentService;
import life.community.community.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;


    /**
     * 用户点击问题时展示问题的详细内容
     * 这个时/question，显示指定用户提问的是/profile/question
     * @param id        问题的ID
     * @param model
     * @return
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model) {

        QuestionDto questionDto = questionService.getQuestionDtoByQuestionId(id);
        if (questionDto == null) {
            // 如果问题为空使用使用异常和统一错误页面
            throw new QuestionNotFoundException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        // 同时返回问题和问题的一级评论，不反悔二级评论，二级评论通过点击一级评论展开是通过AJAX方式请求
        List<QuestionDto> relateQuestionDtos = questionService.getLikeTagQuestions(questionDto);
        List<CommentDto> commentDtoList = commentService.getCommentsByQuestionId(id);

        // 增加问题的阅读数
        // 先增加阅读数在返回最新的阅读数到前端
        questionService.incViewCount(questionDto.getId());
        questionDto.setViewCount(questionDto.getViewCount() + 1);
        model.addAttribute("relateQuestionDtos", relateQuestionDtos);
        model.addAttribute("commentDtoList", commentDtoList);
        model.addAttribute("questionDto", questionDto);
        return "question";
    }

}
