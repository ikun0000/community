package life.community.community.controllers;

import life.community.community.entity.Question;
import life.community.community.entity.User;
import life.community.community.mappers.QuestionMapper;
import life.community.community.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientID;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/publish")
    public String getPublish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String postPublish(@RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("tag") String tag,
                              HttpServletRequest request,
                              Model model) {
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

        Cookie[] cookies = request.getCookies();
        User user = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    user = userMapper.getUserByToken(cookie.getValue());
                    request.getSession().setAttribute("user", user);
                    break;
                }
            }
        }

        if (user != null) {
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user.getId());
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setViewCount(0);
            questionMapper.addNewQuestion(question);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

    }
}
