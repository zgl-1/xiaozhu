package xiaozhu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/removeLogin")
	public String removeLogin(HttpServletRequest request)
	{
		request.getSession().removeAttribute("user");
		return "redirect:/";
	}
}
