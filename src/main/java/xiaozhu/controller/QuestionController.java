package xiaozhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import xiaozhu.dto.QuestionDto;
import xiaozhu.service.QuestionService;

@Controller
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/question/{id}")
	public String  question(@PathVariable("id")Integer id,Model model) {
		QuestionDto questionDto = questionService.findById(id);
		questionService.incView(questionDto.getId());
		model.addAttribute("question",questionDto);
		return "question";
	}
}
