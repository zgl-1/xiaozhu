package xiaozhu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xiaozhu.dto.CommentDto;
import xiaozhu.enums.CommentTypeEnum;
import xiaozhu.exception.CustomErrorEnum;
import xiaozhu.exception.CustomException;
import xiaozhu.mapper.CommentMapper;
import xiaozhu.mapper.QuestionMapper;
import xiaozhu.mapper.QuestionMapperEx;
import xiaozhu.mapper.UserMapper;
import xiaozhu.model.Comment;
import xiaozhu.model.CommentExample;
import xiaozhu.model.Question;
import xiaozhu.model.User;
import xiaozhu.model.UserExample;

@Service
public class CommentService {

	@Autowired
	CommentMapper commentMapper;

	@Autowired
	QuestionMapper questionMapper;

	@Autowired
	UserMapper	userMapper;
	
	@Autowired
	QuestionMapperEx questionMapperEx;

	@Transactional
	public void insert(Comment comment) {
		// TODO Auto-generated method stub
		if (comment.getParentId() == null || comment.getParentId() == 0) {

		}

		if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
			throw new CustomException(CustomErrorEnum.TYPE_PARAM_WRONG);
		}

		if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
			// 回复评论
			Comment selectByPrimaryKey = commentMapper.selectByPrimaryKey(comment.getParentId());
			if (selectByPrimaryKey == null) {
				throw new CustomException(CustomErrorEnum.TYPE_PARAM_WRONG);
			}
			commentMapper.insert(comment);
		} else {
			// 回复问题
			Question selectByPrimaryKey = questionMapper.selectByPrimaryKey(comment.getParentId());
			if (selectByPrimaryKey == null) {
				throw new CustomException(CustomErrorEnum.QUESTION_NOT_FOUND);
			}
			commentMapper.insert(comment);
			selectByPrimaryKey.setCommentCount(1);
			questionMapperEx.incComment(selectByPrimaryKey);
		}
	}

	public List<CommentDto> listByQuestionId(Long id,Integer type) {
		// TODO Auto-generated method stub
		CommentExample example=new CommentExample();
		example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type);
		example.setOrderByClause("gmt_create desc");
		List<Comment> comments = commentMapper.selectByExample(example);
		if(comments.size()==0)
		{
			return new ArrayList<>();
		}
		//获取去重的评论人
		Set<Long> commentators = comments.stream().map(comment->comment.getCommentator()).collect(Collectors.toSet());
		List<Long> userIdList=new ArrayList<Long>();
		userIdList.addAll(commentators);
		
		//获取评论人并转换为map
		UserExample userExample =new UserExample();
		userExample.createCriteria().andIdIn(userIdList);
		List<User> users = userMapper.selectByExample(userExample);
		Map<Long, User> collect = users.stream().collect(Collectors.toMap(user->user.getId(), user->user));
		
		//转化comment 为commentDto
		List<CommentDto> collect2 = comments.stream().map(comment->
		{
			CommentDto commentDto=new CommentDto();
			BeanUtils.copyProperties(comment, commentDto);
			commentDto.setUser(collect.get(comment.getCommentator()));
			return commentDto;
		}).collect(Collectors.toList());
		
		return collect2;
	}

}
