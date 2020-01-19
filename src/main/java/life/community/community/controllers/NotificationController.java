package life.community.community.controllers;


import life.community.community.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 处理用户点击通知时的controller，本来可以直接用question的controller，
     * 在这里拦截，将通知数量自减，然后在重定向到question
     * @param notificationId        通知的ID
     * @param qid                   通知的问题的ID
     * @return
     */
    @GetMapping("/notification/{notificationId}")
    public String notification(@PathVariable("notificationId") Integer notificationId,
                               @RequestParam("qid") Integer qid) {
        notificationService.setNotificationReadedById(notificationId);
        return "redirect:/question/" + qid;
    }

}
