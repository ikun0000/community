package life.community.community.controllers;


import life.community.community.dto.CommentDto;
import life.community.community.dto.ResultDto;
import life.community.community.entity.Comment;
import life.community.community.entity.User;
import life.community.community.exceptions.CustomizeErrorCode;
import life.community.community.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public Object post(@RequestBody CommentDto commentDto,
                       HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();
        comment.setType(commentDto.getType());
        comment.setParentId(commentDto.getParentId());
        comment.setComments(commentDto.getContext());
        comment.setCommentator(user.getId());
        commentService.addNewComment(comment);
        return ResultDto.successOf();
    }
}
