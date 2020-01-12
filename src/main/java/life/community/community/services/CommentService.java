package life.community.community.services;
import life.community.community.entity.Comment;
import life.community.community.entity.Question;
import life.community.community.enums.CommentTypeEnum;
import life.community.community.exceptions.CustomizeErrorCode;
import life.community.community.exceptions.CustomizeException;
import life.community.community.mappers.CommentMapper;
import life.community.community.mappers.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;


    @Transactional
    public void addNewComment(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() <= 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.exists(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WARN);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            Comment dbComment = commentMapper.getCommentById(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
        } else {
            Question question = questionMapper.getQuestionById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            questionService.incCommentCount(question.getId());
        }

        commentMapper.addNewComment(comment);
    }

    @Transactional
    public void incLikeCount(Integer id) {
        Comment comment = commentMapper.getCommentById(id);

        if (comment != null) {
            comment.setLikeCount(comment.getLikeCount() + 1);
            commentMapper.updateCommentById(comment);
        }

    }
}
