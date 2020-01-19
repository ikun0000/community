package life.community.community.controllers;


import life.community.community.dto.CommentDto;
import life.community.community.dto.CommentDtoCreate;
import life.community.community.dto.ResultDto;
import life.community.community.entity.Comment;
import life.community.community.entity.User;
import life.community.community.exceptions.CustomizeErrorCode;
import life.community.community.services.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 提供评论功能的API，根据用户的评论生成通知信息
     * @param commentDtoCreate  包含了评论对象的ID，评论类型，评论内容
     * @param request
     * @return                  失败成功都返回ResultDto
     */
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDtoCreate commentDtoCreate,
                       HttpServletRequest request) {

        // 查看用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        // 判断评论内容的合法性
        if (commentDtoCreate == null
                || StringUtils.isBlank(commentDtoCreate.getContext())) {
            return ResultDto.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setType(commentDtoCreate.getType());
        comment.setParentId(commentDtoCreate.getParentId());
        comment.setComments(commentDtoCreate.getContext());
        comment.setCommentator(user.getId());
        commentService.addNewComment(comment);
        return ResultDto.successOf();
    }

    /**
     * 获取指定ID评论的二级评论，返回一个成功的ResultDto和所有的二级评论
     * @param id    评论的ID
     * @return      返回ResultDto中封装二级评论的列表
     */
    @GetMapping("/comment/{id}")
    public ResultDto post2(@PathVariable("id") Integer id ) {
        List<CommentDto> resultDtoList = commentService.getCommentsByCommentId(id);
        return ResultDto.successOf(resultDtoList);
    }
}
