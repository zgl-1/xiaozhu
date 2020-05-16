package xiaozhu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import xiaozhu.mapper.QuestionMapper;
import xiaozhu.model.Question;
import xiaozhu.model.User;
import xiaozhu.service.QuestionService;

@Controller
public class PublishController {
	@Autowired
	QuestionService questionService;
	
	@Autowired
	QuestionMapper questionMapper;
	
	@GetMapping("/publish")
	public String publish() {

		return "publish";
	}

	
	@GetMapping("/publish/{id}")
	public String edit(@PathVariable(name = "id") Long id,Model model) {
		Question question= questionMapper.selectByPrimaryKey(id);
		model.addAttribute("title", question.getTitle());
		model.addAttribute("description", question.getDescription());
		model.addAttribute("tag", question.getTag());
		model.addAttribute("id", question.getId());
		return "publish";
	}



	@PostMapping("/publish")
	public String dbpublish(@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "tag", required = false) String tag,
			@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request, Model model) {
		model.addAttribute("title", title);
		model.addAttribute("description", description);
		model.addAttribute("tag", tag);
		if (title == null || title == "") {
			model.addAttribute("error", "标题不能为空");
			return "publish";
		}
		if (description == null || description == "") {
			model.addAttribute("error", "内容不能为空");
			return "publish";
		}
		if (tag == null || tag == "") {
			model.addAttribute("error", "标签不能为空");
			return "publish";
		}
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			model.addAttribute("error", "用户未登录");
			return "publish";
		}
		Question question = new Question();
		question.setDescription(description);
		question.setTag(tag);
		question.setTitle(title);
		question.setCreator(user.getId());
		question.setId(id);
		questionService.createOrUpdate(question);
		return "redirect:/";
	}
}
