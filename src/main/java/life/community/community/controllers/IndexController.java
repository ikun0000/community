package life.community.community.controllers;


import life.community.community.entity.User;
import life.community.community.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping({ "/", "/index" })
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        User user = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                user = userMapper.getUserByToken(cookie.getValue());
                request.getSession().setAttribute("user", user);
                break;
            }
        }
        return "index";
    }

}
