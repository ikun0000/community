package life.community.community.interceptors;


import life.community.community.entity.User;
import life.community.community.mappers.UserMapper;
import life.community.community.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 这个拦截器判断用户的登录状态
 * 如果登录在session设置user
 * 如果没有登录则返回跳转信息
 */
@Component
public class SectionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        User user = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    user = userMapper.getUserByToken(cookie.getValue());
                    request.getSession().setAttribute("user", user);
                    return true;
                }
            }
        }

        // 对请求二级评论不需要判断登录状态
        if (request.getRequestURI().equals("/comment")) {
            return true;
        }

        response.sendRedirect("/index");
        return true;
    }
}
