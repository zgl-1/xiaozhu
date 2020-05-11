package xiaozhu.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import xiaozhu.mapper.QuestionMapper;
import xiaozhu.mapper.UserMapper;
import xiaozhu.model.Question;
import xiaozhu.model.User;

@Controller
public class PublishController {
	@GetMapping("/publish")
	public String publish() {

		return "publish";
	}

	@Autowired
	UserMapper usermapper;

	@Autowired
	QuestionMapper questionMapper;

	@PostMapping("/publish")
	public String dbpublish(@RequestParam(value = "title" ,required = false) String title, @RequestParam(value = "description",required = false) String description,
			@RequestParam(value = "tag",required = false) String tag, HttpServletRequest request, Model model) {
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
		
		User user = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("token".equals(cookie.getName())) {
					String token = cookie.getValue();
					user = usermapper.findUserByToken(token);
					if (user != null) {
						request.getSession().setAttribute("user", user);
					}
					break;
				}
			}
		}
		if (user == null) {
			model.addAttribute("error", "用户未登录");
			return "publish";
		}
		Question question = new Question();
		question.setDescription(description);
		question.setTag(tag);
		question.setTitle(title);
		question.setCreator(user.getId());
		question.setGmtCreate(System.currentTimeMillis());
		question.setGmtModified(System.currentTimeMillis());
		questionMapper.insert(question);
		return "redirect:/";
	}
}
