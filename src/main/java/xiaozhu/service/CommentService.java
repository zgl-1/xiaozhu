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
import xiaozhu.enums.NotificationStatusEnum;
import xiaozhu.enums.NotificationTypeEnum;
import xiaozhu.exception.CustomErrorEnum;
import xiaozhu.exception.CustomException;
import xiaozhu.mapper.CommentMapper;
import xiaozhu.mapper.CommentMapperEx;
import xiaozhu.mapper.NotificationMapper;
import xiaozhu.mapper.QuestionMapper;
import xiaozhu.mapper.QuestionMapperEx;
import xiaozhu.mapper.UserMapper;
import xiaozhu.model.Comment;
import xiaozhu.model.CommentExample;
import xiaozhu.model.Notification;
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
	UserMapper userMapper;

	@Autowired
	QuestionMapperEx questionMapperEx;

	@Autowired
	CommentMapperEx commentMapperEx;

	@Autowired
	NotificationMapper notificationMapper;

	@Transactional
	public void insert(Comment comment, User user) {
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

			// 增加评论数
			Comment parentComment = new Comment();
			parentComment.setId(comment.getParentId());
			parentComment.setCommentCount(1);
			commentMapperEx.incCommentCount(parentComment);

			// 回复问题
			Question qurQuestion = questionMapper.selectByPrimaryKey(selectByPrimaryKey.getParentId());
			if (qurQuestion == null) {
				throw new CustomException(CustomErrorEnum.QUESTION_NOT_FOUND);
			}

			// 创建通知
			createNotify(comment, selectByPrimaryKey.getCommentator(), NotificationTypeEnum.REPLY_COMMRNT,
					user.getName(), qurQuestion.getTitle(),qurQuestion.getId());
		} else {
			// 回复问题
			Question selectByPrimaryKey = questionMapper.selectByPrimaryKey(comment.getParentId());
			if (selectByPrimaryKey == null) {
				throw new CustomException(CustomErrorEnum.QUESTION_NOT_FOUND);
			}
			commentMapper.insert(comment);
			selectByPrimaryKey.setCommentCount(1);
			questionMapperEx.incComment(selectByPrimaryKey);

			// 创建通知
			createNotify(comment, selectByPrimaryKey.getCreator(), NotificationTypeEnum.REPLY_QUESTION, user.getName(),
					selectByPrimaryKey.getTitle(),selectByPrimaryKey.getId());
		}
	}

	private void createNotify(Comment comment, Long Commentator, NotificationTypeEnum notificationTypeEnum,
			String notifierName, String outerTitle,Long outerId) {
		if(Commentator==comment.getCommentator())
		{
			return;
		}
		Notification notification = new Notification();
		notification.setGmtCreater(System.currentTimeMillis());
		notification.setType(notificationTypeEnum.getType());
		notification.setOuterid(outerId);
		notification.setNotifier(comment.getCommentator());
		notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
		notification.setReceiver(Commentator);
		notification.setNotifierName(notifierName);
		notification.setOuterTitle(outerTitle);
		notificationMapper.insert(notification);
	}

	public List<CommentDto> listByQuestionId(Long id, Integer type) {
		// TODO Auto-generated method stub
		CommentExample example = new CommentExample();
		example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type);
		example.setOrderByClause("gmt_create desc");
		List<Comment> comments = commentMapper.selectByExample(example);
		if (comments.size() == 0) {
			return new ArrayList<>();
		}
		// 获取去重的评论人
		Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.addAll(commentators);

		// 获取评论人并转换为map
		UserExample userExample = new UserExample();
		userExample.createCriteria().andIdIn(userIdList);
		List<User> users = userMapper.selectByExample(userExample);
		Map<Long, User> collect = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

		// 转化comment 为commentDto
		List<CommentDto> collect2 = comments.stream().map(comment -> {
			CommentDto commentDto = new CommentDto();
			BeanUtils.copyProperties(comment, commentDto);
			commentDto.setUser(collect.get(comment.getCommentator()));
			return commentDto;
		}).collect(Collectors.toList());

		return collect2;
	}

}
