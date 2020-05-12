package xiaozhu.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import xiaozhu.dto.QuestionDto; 
import xiaozhu.mapper.UserMapper; 
import xiaozhu.model.User;
import xiaozhu.service.QuestionService;

@Controller
public class IndexController {
	@Autowired
	UserMapper usermapper;
	
	@Autowired
	QuestionService questionService;
	
	@GetMapping("/")
	public String Index(HttpServletRequest request,Model model,
			@RequestParam(name="page",defaultValue = "1")Integer page,
			@RequestParam(name="size",defaultValue = "5")Integer size)
	{
		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
		{
			for (Cookie cookie : cookies) {
				if("token".equals(cookie.getName()))
				{
					String token=cookie.getValue();
					User user =usermapper.findUserByToken(token);
					if(user!=null)
					{
						request.getSession().setAttribute("user", user);
					}
					break;
				}
			}
		}
		
		
		
		
		List<QuestionDto> list=questionService.list(page,size);
		model.addAttribute("questions",list);
		return "index";
	}
	
	
}
