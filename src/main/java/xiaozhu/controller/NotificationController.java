package xiaozhu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import xiaozhu.dto.NotificationDto;
import xiaozhu.enums.NotificationTypeEnum;
import xiaozhu.model.User;
import xiaozhu.service.NotificationService;

@Controller
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;
	
	@GetMapping("/notification/{id}")
	public String notification(@PathVariable("id") Long id, Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			model.addAttribute("error", "用户未登录");
			return "publish";
		}
		
		NotificationDto notificationDto = notificationService.read(id,user);
		if(notificationDto.getType()==NotificationTypeEnum.REPLY_COMMRNT.getType()||notificationDto.getType()==NotificationTypeEnum.REPLY_QUESTION.getType()) {
			return "redirect:/question/"+notificationDto.getOuterid();
		}else {
			return "redirect:/";
		}
	}
}
