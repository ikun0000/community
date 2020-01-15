package life.community.community.services;
import life.community.community.dto.CommentDto;
import life.community.community.dto.QuestionDto;
import life.community.community.entity.Comment;
import life.community.community.entity.Question;
import life.community.community.entity.User;
import life.community.community.enums.CommentTypeEnum;
import life.community.community.exceptions.CustomizeErrorCode;
import life.community.community.exceptions.CustomizeException;
import life.community.community.mappers.CommentMapper;
import life.community.community.mappers.QuestionMapper;
import life.community.community.mappers.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserMapper userMapper;


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

    @Transactional
    public void incCommentCount(Integer id) {
        Comment comment = commentMapper.getCommentById(id);

        if (comment != null) {
            comment.setCommentCount(comment.getCommentCount() + 1);
            commentMapper.updateCommentById(comment);
        }
    }

    public List<CommentDto> getCommentsByQuestionId(Integer id) {
        List<Comment> firstLevelReviewByQuestionId = commentMapper.getFirstLevelReviewByQuestionId(id);
        List<CommentDto> commentDtoList = new ArrayList<CommentDto>();
        CommentDto commentDto = null;
        if (firstLevelReviewByQuestionId == null) {
            return null;
        }

        for (Comment comment : firstLevelReviewByQuestionId) {
            commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            User user = userMapper.getUserById(comment.getCommentator());
            commentDto.setUser(user);
            commentDtoList.add(commentDto);
        }

        return commentDtoList;
    }

    public List<CommentDto> getCommentsByCommentId(Integer id) {
        List<Comment> secondaryReviewByParentId = commentMapper.getSecondaryReviewByParentId(id);
        List<CommentDto> commentDtoList = new ArrayList<CommentDto>();
        CommentDto commentDto = null;
        if (secondaryReviewByParentId == null) {
            return null;
        }

        for (Comment comment : secondaryReviewByParentId) {
            commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            User user = userMapper.getUserById(comment.getCommentator());
            commentDto.setUser(user);
            commentDtoList.add(commentDto);
        }

        return commentDtoList;
    }
}
