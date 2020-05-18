package xiaozhu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xiaozhu.dto.CommentDto;
import xiaozhu.dto.ResultDto;
import xiaozhu.exception.CustomErrorEnum;
import xiaozhu.model.Comment;
import xiaozhu.model.User;
import xiaozhu.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	CommentService commentService;

	@ResponseBody
	@RequestMapping(value = "comment", method = RequestMethod.POST)
	public Object post(@RequestBody CommentDto commentDto, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return ResultDto.errorOf(CustomErrorEnum.NO_LOGIN);
		}
		Comment comment = new Comment();
		comment.setParentId(commentDto.getParentId());
		comment.setContent(commentDto.getContent());
		comment.setType(commentDto.getType());
		comment.setGmtCreate(System.currentTimeMillis());
		comment.setGmtModified(System.currentTimeMillis());
		comment.setCommentator(1L);
		comment.setLikeCount(0l);
		commentService.insert(comment);
		return ResultDto.okOf();
	}
}
