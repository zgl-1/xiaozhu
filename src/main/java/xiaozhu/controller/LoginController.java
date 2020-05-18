package xiaozhu.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/removeLogin")
	public String removeLogin(HttpServletRequest request,HttpServletResponse response)
	{
		request.getSession().removeAttribute("user");
		Cookie cookie =new Cookie("token", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/";
	}
}
