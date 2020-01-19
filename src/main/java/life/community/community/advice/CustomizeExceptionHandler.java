package life.community.community.advice;


import life.community.community.dto.FileDto;
import life.community.community.dto.ResultDto;
import life.community.community.exceptions.CustomizeException;
import life.community.community.exceptions.QuestionNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * 处理controller抛出的异常
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    /**
     * 处理用户请求不存在文章
     * @param request
     * @param ex
     * @param model
     * @return
     */
    @ExceptionHandler(QuestionNotFoundException.class)
    ModelAndView handleQuestionNotFoundException(HttpServletRequest request, @NotNull Throwable ex, @NotNull Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("warnmsg", "问题没有找到");
        return new ModelAndView("exception");
    }

    /**
     * 处理用户各种异常
     * @param request
     * @param ex
     * @return          返回JSON
     */
    @ExceptionHandler(CustomizeException.class)
    @ResponseBody
    ResultDto handleCustomizeException(HttpServletRequest request, CustomizeException ex) {
         return ResultDto.errorOf(ex);
    }

    /**
     * 处理Editor.md上传失败
     * @param ex
     * @return      返回Editor.md可以解析的JSON
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    FileDto handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        FileDto fileDto = new FileDto();
        fileDto.setSuccess(0);
        fileDto.setMessage(ex.getMessage());
        return fileDto;
    }

    /**
     * 统一处理不能识别的异常
     * @param request
     * @param ex
     * @param model
     * @return
     */
    @ExceptionHandler(Exception.class)
    ModelAndView unknowException(HttpServletRequest request, Throwable ex, @NotNull Model model) {
            model.addAttribute("message", "无法处理的异常");
        model.addAttribute("warnmsg", ex.getMessage());
        ex.printStackTrace();
        return new ModelAndView("exception");
    }

}
