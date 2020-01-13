package life.community.community.advice;

import life.community.community.dto.ResultDto;
import life.community.community.exceptions.CustomizeException;
import life.community.community.exceptions.QuestionNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(QuestionNotFoundException.class)
    ModelAndView handleQuestionNotFoundException(HttpServletRequest request, @NotNull Throwable ex, @NotNull Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("warnmsg", "网络安全法警告");
        return new ModelAndView("exception");
    }

    @ExceptionHandler(CustomizeException.class)
    @ResponseBody
    ResultDto handleCustomizeException(HttpServletRequest request, CustomizeException ex) {
         return ResultDto.errorOf(ex);
    }

    @ExceptionHandler(Exception.class)
    ModelAndView unknowException(HttpServletRequest request, Throwable ex, @NotNull Model model) {
            model.addAttribute("message", "无法处理的异常");
        model.addAttribute("warnmsg", "网络安全法警告");
        return new ModelAndView("exception");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
