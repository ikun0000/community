package life.community.community.controllers;


import life.community.community.dto.PaginationDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Value("${github.client.id}")
    private String clientID;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/question")
    public String question(@RequestParam(name = "page", defaultValue = "1") int page,
                           @NotNull HttpServletRequest request,
                           Model model) {

        User user = (User) request.getSession().getAttribute("user");

        if (page < 1 || page > Math.ceil(questionMapper.getUserQuestionCount(user.getId()) / 5.0)) {
            page = 1;
        }

        PaginationDto paginationDto = questionService.getCurrentUserQuestion(user.getId(), page);
        model.addAttribute("paginationDto", paginationDto);

        model.addAttribute("clientID", clientID);
        model.addAttribute("redirectUri", redirectUri);
        model.addAttribute("section", "question");
        model.addAttribute("sectionname", "我的问题");


        return "profile";
    }

    @GetMapping("/reply")
    public String reply(@NotNull HttpServletRequest request,
                        Model model) {
        Cookie[] cookies = request.getCookies();
        User user = null;


        model.addAttribute("clientID", clientID);
        model.addAttribute("redirectUri", redirectUri);
        model.addAttribute("section", "reply");
        model.addAttribute("sectionname", "最新回复");


        return "profile";
    }

}
