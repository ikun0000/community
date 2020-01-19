package life.community.community.controllers;


import life.community.community.dto.AccessTokenDto;
import life.community.community.dto.GitHubUser;
import life.community.community.entity.User;
import life.community.community.mappers.UserMapper;
import life.community.community.provider.GithubProvider;
import life.community.community.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientID;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    /**
     * 处理Github授权登录成功后的操作，主要是获取授权用户的Github用户名，用户ID，用户头像地址，bio
     * @param code      Github返回的code
     * @param state     原本登录页面随机生成的state由Github再发送回来
     * @param request
     * @param response
     * @return          登录成功或失败都返回index
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        // 这里获取access_token
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setClient_id(clientID);
        accessTokenDto.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);

        // 这里获取用户的信息
        GitHubUser gitHubUser = githubProvider.getUser(accessToken);

        if (gitHubUser != null && gitHubUser.getId() != null) {
            // 登陆成功

            String token = UUID.randomUUID().toString();
            User user = new User();
            user.setAccountID(gitHubUser.getId());
            user.setName(gitHubUser.getName());
            user.setBio(gitHubUser.getBio());
            user.setToken(token);
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            user = userService.createOrUpdate(user);

            request.getSession().setAttribute("user", user);
            Cookie cookieToken = new Cookie("token", token);
            cookieToken.setMaxAge(7*24*60*60);
            response.addCookie(cookieToken);
            return "redirect:index";
        } else {
            // 登陆失败
            return "redirect:index";
        }
    }

    /**
     * 用户发出退出登录请求，清空cookie和session
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie tokenCookie = new Cookie("token", null);
        tokenCookie.setMaxAge(-1);
        response.addCookie(tokenCookie);
        return "redirect:/index";
    }

}
