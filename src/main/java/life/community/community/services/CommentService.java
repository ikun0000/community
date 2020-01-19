package life.community.community.services;


import life.community.community.dto.CommentDto;
import life.community.community.entity.Comment;
import life.community.community.entity.Question;
import life.community.community.entity.User;
import life.community.community.enums.CommentTypeEnum;
import life.community.community.enums.NotificationTypeEnum;
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

    @Autowired
    private NotificationService notificationService;


    /**
     * 添加一条评论
     * @param comment
     */
    @Transactional
    public void addNewComment(Comment comment) {
        // 判断评论的合法性
        if (comment.getParentId() == null || comment.getParentId() <= 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.exists(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WARN);
        }

        // 验证通过
        // 同时给指定用户发送通知
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 评论了评论
            Comment dbComment = commentMapper.getCommentById(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            incCommentCount(dbComment.getId());
            notificationService.addANotification(NotificationTypeEnum.REPLY_COMMENT.getType(), comment.getCommentator(), dbComment.getCommentator(), comment.getParentId());
        } else {
            // 评论了问题
            Question question = questionMapper.getQuestionById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            questionService.incCommentCount(question.getId());
            notificationService.addANotification(NotificationTypeEnum.REPLY_QUESTION.getType(), comment.getCommentator(), question.getCreator(), comment.getParentId());
        }

        commentMapper.addNewComment(comment);
    }

    /**
     * 增加指定文章的阅读数
     * @param id        文章的ID
     */
    @Transactional
    public void incLikeCount(Integer id) {
        Comment comment = commentMapper.getCommentById(id);

        if (comment != null) {
            comment.setLikeCount(comment.getLikeCount() + 1);
            commentMapper.updateCommentById(comment);
        }

    }

    /**
     * 增加评论数
     * @param id    评论的ID
     */
    @Transactional
    public void incCommentCount(Integer id) {
        Comment comment = commentMapper.getCommentById(id);

        if (comment != null) {
            comment.setCommentCount(comment.getCommentCount() + 1);
            commentMapper.updateCommentById(comment);
        }
    }

    /**
     * 根据问题的ID获取它所有的一级评论
     * @param id        问题的ID
     * @return          一级评论列表
     */
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

    /**
     * 获取指定一级评论ID的二级评论
     * @param id        一级评论的ID
     * @return          所属一级平路的二级评论的列表
     */
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
