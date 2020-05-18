package xiaozhu.advice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import xiaozhu.dto.ResultDto;
import xiaozhu.exception.CustomErrorEnum;
import xiaozhu.exception.CustomException;

@ControllerAdvice
public class CustomzeExceptionHandler {
	@ExceptionHandler(Exception.class)
	ModelAndView handleControllerException(Throwable ex, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String contentType = request.getContentType();
		ResultDto resultDto ;
		if ("application/json".equals(contentType)) {
			if (ex instanceof CustomException) {
				resultDto = ResultDto.errorOf((CustomException) ex);
			} else {
				resultDto = ResultDto.errorOf(CustomErrorEnum.SYS_ERROR);
			}
			
			try {
				response.setContentType("application/json");
				response.setStatus(200);
				response.setCharacterEncoding("utf-8");
				PrintWriter writer = response.getWriter();
				writer.write(JSON.toJSONString(resultDto));
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			// 页面跳转
			if (ex instanceof CustomException) {
				model.addAttribute("message", ex.getMessage());
			} else {
				model.addAttribute("message", CustomErrorEnum.SYS_ERROR.getMessgae());
			}
			return new ModelAndView("error");
		}

	}
}
