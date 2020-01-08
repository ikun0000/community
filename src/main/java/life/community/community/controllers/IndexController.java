package life.community.community.controllers;


import life.community.community.dto.PaginationDto;
import life.community.community.dto.QuestionDto;
import life.community.community.entity.Question;
import life.community.community.entity.User;
import life.community.community.mappers.QuestionMapper;
import life.community.community.mappers.UserMapper;
import life.community.community.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @Value("${github.client.id}")
    private String clientID;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping({ "/", "/index" })
    public String index(@RequestParam(name = "page", defaultValue = "1") int page,
                        HttpServletRequest request,
                        Model model) {
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

        if (page < 1 || page > Math.ceil(questionMapper.getItemCount() / 5.0)) {
            page = 1;
        }

        PaginationDto paginationDto = questionService.getPage(page);
        model.addAttribute("paginationDto", paginationDto);
        model.addAttribute("clientID", clientID);
        model.addAttribute("redirectUri", redirectUri);
        return "index";
    }

}
