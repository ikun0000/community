package life.community.community.controllers;


import life.community.community.dto.CommentDto;
import life.community.community.dto.CommentDtoCreate;
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


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model) {

        QuestionDto questionDto = questionService.getQuestionDtoByQuestionId(id);
        if (questionDto == null) {
            throw new QuestionNotFoundException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        List<QuestionDto> relateQuestionDtos = questionService.getLikeTagQuestions(questionDto);
        List<CommentDto> commentDtoList = commentService.getCommentsByQuestionId(id);

        questionService.incViewCount(questionDto.getId());
        questionDto.setViewCount(questionDto.getViewCount() + 1);
        model.addAttribute("relateQuestionDtos", relateQuestionDtos);
        model.addAttribute("commentDtoList", commentDtoList);
        model.addAttribute("questionDto", questionDto);
        return "question";
    }

}
