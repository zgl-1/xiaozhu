package xiaozhu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import xiaozhu.dto.NotificationDto;
import xiaozhu.dto.PaginationDto;
import xiaozhu.mapper.UserMapper;
import xiaozhu.model.User;
import xiaozhu.service.NotificationService;
import xiaozhu.service.QuestionService;

@Controller
public class ProfileController {
	@Autowired
	UserMapper usermapper;

	@Autowired
	QuestionService questionService;
	
	@Autowired
	NotificationService notificationService;

	@GetMapping("/profile/{action}")
	public String profile(HttpServletRequest request, Model model, @PathVariable("action") String action,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "5") Integer size) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "redirect:/";
		}

		if ("questions".equals(action)) {
			model.addAttribute("section", "questions");
			model.addAttribute("sectionName", "我的提问");
			PaginationDto list = questionService.list(user.getId(), page, size);
			model.addAttribute("pagination", list);
		} else if ("replies".equals(action)) {
			Long unreadCount=notificationService.unreadCount(user.getId());
			model.addAttribute("section", "replies");
			model.addAttribute("sectionName", "最新回复");
			model.addAttribute("unreadCount", unreadCount);
			PaginationDto paginationDto=notificationService.list(user.getId(),page,size);
			model.addAttribute("pagination", paginationDto);
		}
		
		return "profile";
	}
}
