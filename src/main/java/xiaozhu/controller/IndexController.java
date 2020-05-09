package xiaozhu.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import xiaozhu.mapper.UserMapper;
import xiaozhu.model.User;

@Controller
public class IndexController {
	@Autowired
	UserMapper usermapper;
	
	@GetMapping("/")
	public String Index(HttpServletRequest request )
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
		return "index";
	}
	
	
}
