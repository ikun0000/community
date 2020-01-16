package life.community.community.controllers;


import life.community.community.dto.PaginationDto;
import life.community.community.mappers.QuestionMapper;
import life.community.community.services.NotificationService;
import life.community.community.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

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
                        Model model) {

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
