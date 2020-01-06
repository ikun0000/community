package life.community.community.controllers;

import life.community.community.dto.AccessTokenDto;
import life.community.community.dto.GitHubUser;
import life.community.community.entity.User;
import life.community.community.mappers.UserMapper;
import life.community.community.provider.GithubProvider;
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

    @Value("${github.client.id}")
    private String clientID;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

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
            // request.getSession().setAttribute("user", gitHubUser);
            User user = new User();
            user.setAccountID(gitHubUser.getId());
            user.setName(gitHubUser.getName());
            user.setBio(gitHubUser.getBio());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAvatarUrl(gitHubUser.getAvatar_url());
            userMapper.deleteFromAccountID(user.getAccountID());
            userMapper.addUser(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:index";
        } else {
            // 登陆失败
            return "redirect:index";
        }
    }

}
