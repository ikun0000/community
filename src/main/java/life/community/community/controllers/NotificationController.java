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

    @GetMapping("/notification/{notificationId}")
    public String notification(@PathVariable("notificationId") Integer notificationId,
                               @RequestParam("qid") Integer qid) {
        notificationService.setNotificationReadedById(notificationId);
        return "redirect:/question/" + qid;
    }

}
