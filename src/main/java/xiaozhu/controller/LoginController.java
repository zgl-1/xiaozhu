package xiaozhu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String GetLogin()
	{
		return "login";
	}
	
	@PostMapping("/postLogin")
	public String PostLogin(@RequestParam(name = "username") String username,
							@RequestParam(name = "password") String password)
	{
		return "index";
	}
}
