package xiaozhu.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import xiaozhu.dto.PaginationDto;
import xiaozhu.service.QuestionService;

@Controller
public class IndexController {

	
	@Autowired
	QuestionService questionService;
	
	@GetMapping("/")
	public String Index(HttpServletRequest request,Model model,
			@RequestParam(name="page",defaultValue = "1")Integer page,
			@RequestParam(name="size",defaultValue = "5")Integer size)
	{
		
		 
		PaginationDto list=questionService.list(page,size);
		model.addAttribute("pagination",list);
		return "index";
	}
	
	
}
