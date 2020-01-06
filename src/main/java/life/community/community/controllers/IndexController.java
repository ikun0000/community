package life.community.community.controllers;


import life.community.community.entity.User;
import life.community.community.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientID;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping({ "/", "/index" })
    public String index(HttpServletRequest request, Model model) {
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
        model.addAttribute("clientID", clientID);
        model.addAttribute("redirectUri", redirectUri);
        return "index";
    }

}
