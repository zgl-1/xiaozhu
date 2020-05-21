package xiaozhu.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xiaozhu.dto.PaginationDto;
import xiaozhu.dto.QuestionDto;
import xiaozhu.exception.CustomErrorEnum;
import xiaozhu.exception.CustomException;
import xiaozhu.mapper.QuestionMapper;
import xiaozhu.mapper.QuestionMapperEx;
import xiaozhu.mapper.UserMapper;
import xiaozhu.model.Question;
import xiaozhu.model.QuestionExample;
import xiaozhu.model.User;

@Service
public class QuestionService {

	@Autowired
	UserMapper usermapper;

	@Autowired
	QuestionMapper questionMapper;
	
	@Autowired
	QuestionMapperEx questionMapperEx;

	public PaginationDto list(Integer page, Integer size) {
		
		Integer count = (int)questionMapper.countByExample(new QuestionExample());
		PaginationDto paginationDto = new PaginationDto();
		Integer totalPage;
		if (count % size == 0) {
			totalPage = count / size;
		} else {
			totalPage = count / size + 1;
		}
		
		paginationDto.setPagination(totalPage, page);
		
		if (page < 1) {
			page = 1;
		}
		
		if (page > paginationDto.getTotalPage()) {
			page = paginationDto.getTotalPage();
		}
		
		Integer offset = size * (page - 1);
		List<Question> list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset,size));
		List<QuestionDto> questionDtos = new ArrayList<>();
		
		for (Question question : list) {
			User user = usermapper.selectByPrimaryKey(question.getCreator());
			QuestionDto questionDto = new QuestionDto();
			BeanUtils.copyProperties(question, questionDto);
			questionDto.setUser(user);
			questionDtos.add(questionDto);
		}
		paginationDto.setQuestions(questionDtos);

		
		return paginationDto;
	}
	
	public PaginationDto list(long userid,Integer page, Integer size) {
		QuestionExample example=new QuestionExample();
		example.createCriteria().andCreatorEqualTo(userid);
		Integer count = (int)questionMapper.countByExample(example);
		PaginationDto paginationDto = new PaginationDto();
		Integer totalPage;
		if (count % size == 0) {
			totalPage = count / size;
		} else {
			totalPage = count / size + 1;
		}
		
		paginationDto.setPagination(totalPage, page);
		
		if (page < 1) {
			page = 1;
		}
		
		if (page > paginationDto.getTotalPage()) {
			page = paginationDto.getTotalPage();
		}
		
		Integer offset = size * (page - 1);
		QuestionExample questionExample=new QuestionExample();
		questionExample.createCriteria().andCreatorEqualTo(userid);
		List<Question> list = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset,size));
		List<QuestionDto> questionDtos = new ArrayList<>();
		
		for (Question question : list) {
			User user = usermapper.selectByPrimaryKey(question.getCreator());
			QuestionDto questionDto = new QuestionDto();
			BeanUtils.copyProperties(question, questionDto);
			questionDto.setUser(user);
			questionDtos.add(questionDto);
		}
		paginationDto.setQuestions(questionDtos);

		
		return paginationDto;
	}

	public QuestionDto findById(long id) {
		Question question = questionMapper.selectByPrimaryKey(id);
		
		if(question==null)
		{
			throw new CustomException(CustomErrorEnum.QUESTION_NOT_FOUND);
		}
		User user = usermapper.selectByPrimaryKey(question.getCreator());
		QuestionDto questionDto = new QuestionDto();
		BeanUtils.copyProperties(question, questionDto);
		questionDto.setUser(user);
		return questionDto;
	}

	public void createOrUpdate(Question question) {
		if(question.getId() == null)
		{
			question.setGmtCreate(System.currentTimeMillis());
			question.setGmtModified(System.currentTimeMillis());
			question.setCommentCount(0);
			question.setViewCount(0);
			question.setLikeCount(0);
			questionMapper.insert(question);
		}else {
			Question question2=new Question();
			question2.setGmtModified(System.currentTimeMillis());
			question2.setTitle(question.getTitle());
			question2.setDescription(question.getDescription());
			question2.setTag(question.getTag());
			question.setGmtModified(System.currentTimeMillis());
			QuestionExample questionExample=new QuestionExample();
			questionExample.createCriteria().andIdEqualTo(question.getId());
			int updateByExampleSelective = questionMapper.updateByExampleSelective(question2, questionExample);
			if(updateByExampleSelective!=1) {
				throw new CustomException(CustomErrorEnum.QUESTION_NOT_FOUND);
			}
		}
	}

	public void incView(long id) {
		// TODO Auto-generated method stub
		Question question=new Question();
		question.setId(id);
		question.setViewCount(1);
		
		questionMapperEx.incView(question);
	}

}
