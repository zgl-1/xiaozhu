package xiaozhu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import xiaozhu.dto.CommentDto;
import xiaozhu.dto.QuestionDto;
import xiaozhu.enums.CommentTypeEnum;
import xiaozhu.service.CommentService;
import xiaozhu.service.QuestionService;

@Controller
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private CommentService commentService;

	@GetMapping("/question/{id}")
	public String question(@PathVariable("id") Long id, Model model) {
		QuestionDto questionDto = questionService.findById(id);
		List<QuestionDto> relatedQuestions=questionService.selectRelated(questionDto);
		
		Integer type = CommentTypeEnum.QUESTION.getType();
		List<CommentDto> commList = commentService.listByQuestionId(questionDto.getId(),type);
		questionService.incView(questionDto.getId());
		model.addAttribute("question", questionDto);
		model.addAttribute("comments", commList);
		model.addAttribute("relatedQuestions", relatedQuestions);
		return "question";
	}
}
