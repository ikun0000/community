package life.community.community.controllers;


import life.community.community.dto.NotificationDto;
import life.community.community.dto.PaginationDto;
import life.community.community.entity.User;
import life.community.community.mappers.QuestionMapper;
import life.community.community.services.NotificationService;
import life.community.community.services.QuestionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户点击左上角的统一用户信息页
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @Value("${github.client.id}")
    private String clientID;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    /**
     * 获取用户提问的问题
     * @param page          和首页一样的页数
     * @param request
     * @param model
     * @return
     */
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
        // thymeleaf根据section选择适合的div块展示
        model.addAttribute("section", "question");
        // thymeleaf展示在div块左上角的文本
        model.addAttribute("sectionname", "我的问题");
        // 设置用户的未读通知数
        model.addAttribute("unreadCount", notificationService.getNotificationCountByReceiverId(user.getId()));


        return "profile";
    }

    /**
     * 用户点击通知
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/reply")
    public String reply(@NotNull HttpServletRequest request,
                        Model model) {
        User user = (User) request.getSession().getAttribute("user");

        List<NotificationDto> notificationdtos = notificationService.getNotificationDtosByReceiverId(user.getId());
        model.addAttribute("unreadNotifications", notificationdtos);
        model.addAttribute("clientID", clientID);
        model.addAttribute("redirectUri", redirectUri);
        // 下面三个同上
        model.addAttribute("section", "reply");
        model.addAttribute("sectionname", "最新回复");
        model.addAttribute("unreadCount", notificationService.getNotificationCountByReceiverId(user.getId()));


        return "profile";
    }

}
