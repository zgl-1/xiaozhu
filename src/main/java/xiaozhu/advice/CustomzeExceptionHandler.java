package xiaozhu.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import xiaozhu.exception.CustomException;

@ControllerAdvice
public class CustomzeExceptionHandler {
	@ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(Throwable ex,Model model) {
        if(ex instanceof CustomException)
        {
        	 model.addAttribute("message",ex.getMessage());
        }
        else {
        	  model.addAttribute("message","你的服务器已经冒烟了");
		}
        return new ModelAndView("error");
    }
}
