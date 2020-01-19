package life.community.community.controllers;


import life.community.community.dto.PaginationDto;
import life.community.community.mappers.QuestionMapper;
import life.community.community.services.QuestionService;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 处理首页的controller，根据用户的登录情况设置Model
     * @param page      评论的页数，可选，默认为1
     * @param search    查找的评论内容，可选
     * @param model
     * @return
     */
    @GetMapping({ "/", "/index" })
    public String index(@RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "search", required = false, defaultValue = "") String search,
                        Model model) {

        if (page < 1 || page > Math.ceil(questionMapper.getItemCount() / 5.0)) {
            page = 1;
        }

        PaginationDto paginationDto = null;
                questionService.getPage(page);

        if (StringUtils.isBlank(search)) {
            // 如果没有搜索内容则返回全部问题
            paginationDto = questionService.getPage(page);
        } else {
            // 有搜索内容返回搜索的问题
            paginationDto = questionService.getPageBySearchQuestion(page, search);
        }

        model.addAttribute("paginationDto", paginationDto);
        model.addAttribute("search", search);
        model.addAttribute("clientID", clientID);
        model.addAttribute("redirectUri", redirectUri);
        return "index";
    }

}
