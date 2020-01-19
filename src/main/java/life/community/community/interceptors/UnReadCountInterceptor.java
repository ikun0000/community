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
 * 这个拦截器用来设置登录用户的未读通知数
 */
@Component
public class UnReadCountInterceptor implements HandlerInterceptor {

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

                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                        request.getSession().setAttribute("uReacCount", notificationService.getNotificationCountByReceiverId(user.getId()));
                    } else {
                        request.getSession().setAttribute("uReacCount", 0);
                    }
                }
            }
        }
        return true;
    }
}
