package life.community.community.controllers;

import life.community.community.dto.QuestionDto;
import life.community.community.entity.Question;
import life.community.community.entity.User;
import life.community.community.mappers.QuestionMapper;
import life.community.community.mappers.UserMapper;
import life.community.community.services.QuestionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Value("${github.client.id}")
    private String clientID;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/publish")
    public String getPublish(@NotNull Model model) {
        model.addAttribute("clientID", clientID);
        model.addAttribute("redirectUri", redirectUri);
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,
                       Model model) {
        model.addAttribute("clientID", clientID);
        model.addAttribute("redirectUri", redirectUri);

        QuestionDto question = questionService.getQuestionDtoByQuestionId(id);
        if (question == null) {
            return "redirect:/index";
        }

        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());

        return "publish";
    }

    @PostMapping("/publish")
    public String postPublish(@RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("tag") String tag,
                              @RequestParam(value = "id", required = false) Integer id,
                              HttpServletRequest request,
                              @NotNull Model model) {
        model.addAttribute("clientID", clientID);
        model.addAttribute("redirectUri", redirectUri);

        if (title == null || description == null || tag == null ||
        title.isEmpty() || description.isEmpty() || tag.isEmpty()) {
            model.addAttribute("title", title);
            model.addAttribute("description", description);
            model.addAttribute("tag", tag);
            model.addAttribute("warn", "内容不完整");
            return "publish";
        }


        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            Question question = new Question();
            question.setId(id);
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user.getId());
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setViewCount(0);
            questionService.addNewQuestionOrUpdateQuestion(question);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

    }

}
