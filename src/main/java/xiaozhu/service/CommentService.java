package xiaozhu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xiaozhu.enums.CommentTypeEnum;
import xiaozhu.exception.CustomErrorEnum;
import xiaozhu.exception.CustomException;
import xiaozhu.mapper.CommentMapper;
import xiaozhu.mapper.QuestionMapper;
import xiaozhu.mapper.QuestionMapperEx;
import xiaozhu.model.Comment;
import xiaozhu.model.Question;

@Service
public class CommentService {

	@Autowired
	CommentMapper commentMapper;
	
	@Autowired
	QuestionMapper questionMapper;
	
	@Autowired
	QuestionMapperEx questionMapperEx;
	
	@Transactional
	public void insert(Comment comment) {
		// TODO Auto-generated method stub
		if(comment.getParentId()==null ||comment.getParentId()==0) {
			
		}
		
		if(comment.getType()==null||!CommentTypeEnum.isExist(comment.getType()))
		{
			throw new CustomException(CustomErrorEnum.TYPE_PARAM_WRONG);
		}
		
		if(comment.getType()==CommentTypeEnum.COMMENT.getType()) {
			//回复评论
			Comment selectByPrimaryKey = commentMapper.selectByPrimaryKey(comment.getId());
			if(selectByPrimaryKey==null) {
				throw new CustomException(CustomErrorEnum.TYPE_PARAM_WRONG);
			}
			commentMapper.insert(comment);
		}else {
			//回复问题
			Question selectByPrimaryKey = questionMapper.selectByPrimaryKey(comment.getParentId());
			if(selectByPrimaryKey==null) {
				throw new CustomException(CustomErrorEnum.QUESTION_NOT_FOUND);
			}
			commentMapper.insert(comment);
			selectByPrimaryKey.setCommentCount(1);
			questionMapperEx.incComment(selectByPrimaryKey);
		}
	}

}
