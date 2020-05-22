package xiaozhu.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xiaozhu.dto.CommentCreateDto;
import xiaozhu.dto.CommentDto;
import xiaozhu.dto.ResultDto;
import xiaozhu.enums.CommentTypeEnum;
import xiaozhu.exception.CustomErrorEnum;
import xiaozhu.exception.CustomException;
import xiaozhu.model.Comment;
import xiaozhu.model.User;
import xiaozhu.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	CommentService commentService;

	@ResponseBody
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public Object post(@RequestBody CommentCreateDto commentDto, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return ResultDto.errorOf(CustomErrorEnum.NO_LOGIN);
		}
		
		if(commentDto==null||StringUtils.isBlank(commentDto.getContent()))
		{
			throw new  CustomException(CustomErrorEnum.COMENT_IS_EMPTY);
		}
		Comment comment = new Comment();
		comment.setParentId(commentDto.getParentId());
		comment.setContent(commentDto.getContent());
		comment.setType(commentDto.getType());
		comment.setGmtCreate(System.currentTimeMillis());
		comment.setGmtModified(System.currentTimeMillis());
		comment.setCommentator(user.getId());
		comment.setLikeCount(0l);
		commentService.insert(comment);
		return ResultDto.okOf();
	}
	

	@ResponseBody
	@RequestMapping(value = "comment/{id}", method = RequestMethod.GET)
	public ResultDto<List<CommentDto>> comments(@PathVariable("id")Long id ) {
		Integer type = CommentTypeEnum.COMMENT.getType();
		List<CommentDto> listByQuestionId = commentService.listByQuestionId(id,type);
		return ResultDto.okOf(listByQuestionId);
	}
}
