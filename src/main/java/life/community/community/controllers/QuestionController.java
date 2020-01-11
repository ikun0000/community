package life.community.community.controllers;


import life.community.community.dto.QuestionDto;
import life.community.community.exceptions.CustomizeErrorCode;
import life.community.community.exceptions.QuestionNotFoundException;
import life.community.community.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model) {

        QuestionDto questionDto = questionService.getQuestionDtoByQuestionId(id);

        if (questionDto == null) {
            throw new QuestionNotFoundException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        questionService.incViewCount(questionDto.getId());
        questionDto.setViewCount(questionDto.getViewCount() + 1);
        model.addAttribute("questionDto", questionDto);
        return "question";
    }
}
