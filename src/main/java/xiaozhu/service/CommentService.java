package xiaozhu.service;

import org.springframework.stereotype.Service;

import xiaozhu.exception.CustomErrorEnum;
import xiaozhu.exception.CustomException;
import xiaozhu.model.Comment;

@Service
public class CommentService {

	public void insert(Comment comment) {
		// TODO Auto-generated method stub
		if(comment.getParentId()==null ||comment.getParentId()==0) {
			throw new CustomException(CustomErrorEnum.TARGET_PARAM_NOT_FOUND);
		}
		
		
	}

}
