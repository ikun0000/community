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

    @PostMapping("/comment")
    public Object post(@RequestBody CommentDtoCreate commentDtoCreate,
                       HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

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

    @GetMapping("/comment/{id}")
    public ResultDto post2(@PathVariable("id") Integer id ) {
        List<CommentDto> resultDtoList = commentService.getCommentsByCommentId(id);
        commentService.incCommentCount(id);
        return ResultDto.successOf(resultDtoList);
    }
}
