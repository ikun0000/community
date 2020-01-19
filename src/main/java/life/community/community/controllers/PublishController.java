package life.community.community.controllers;


import life.community.community.dto.QuestionDto;
import life.community.community.entity.Question;
import life.community.community.entity.User;
import life.community.community.services.QuestionService;
import life.community.community.services.TagLibService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TagLibService tagLibService;

    @Value("${github.client.id}")
    private String clientID;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    /**
     * 当用户点击评论时触发，只返回页面
     * @param model
     * @return
     */
    @GetMapping("/publish")
    public String getPublish(@NotNull Model model) {
        model.addAttribute("clientID", clientID);
        model.addAttribute("redirectUri", redirectUri);

        model.addAttribute("title", "");
        model.addAttribute("description", "");
        model.addAttribute("tag", "");
        model.addAttribute("taglib", tagLibService.getAllTag());
        return "publish";
    }

    /**
     * 用户编辑时触发，返回评论页，并且带上指定问题的内容
     * @param id        问题的ID
     * @param model
     * @return      如果没有找到问题会重定向到首页
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,
                       Model model) {
        model.addAttribute("clientID", clientID);
        model.addAttribute("redirectUri", redirectUri);

        QuestionDto question = questionService.getQuestionDtoByQuestionId(id);
        if (question == null) {
            return "redirect:/index";
        }

        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("taglib", tagLibService.getAllTag());
        return "publish";
    }

    /**
     * 用户点击提交问题时触发
     * @param title         问题的标题
     * @param description   问题的实体
     * @param tag           给问题打上的标签
     * @param id            根据ID判断是修改后的提交还是新提交，如果为空则新建文章，否则修改对应id的文章
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/publish")
    public String postPublish(@RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("tag") String tag,
                              @RequestParam(value = "id", required = false) Integer id,
                              HttpServletRequest request,
                              @NotNull Model model) {
        model.addAttribute("clientID", clientID);
        model.addAttribute("redirectUri", redirectUri);

        // 判断内容的合法性，如果不合法返回信息到前端展示给用户
        if (title == null || description == null || tag == null ||
        title.isEmpty() || description.isEmpty() || tag.isEmpty()) {
            model.addAttribute("title", title);
            model.addAttribute("description", description);
            model.addAttribute("tag", tag);
            // 展示信息
            model.addAttribute("warn", "内容不完整");
            model.addAttribute("taglib", tagLibService.getAllTag());
            return "publish";
        }

        // 只有登录的用户才可以发出问题
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Question question = new Question();
            question.setId(id);
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user.getId());
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setViewCount(0);
            questionService.addNewQuestionOrUpdateQuestion(question);
            return "redirect:/index";
        } else {
            // 向publish页面展示用户未登录信息
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

    }

}
