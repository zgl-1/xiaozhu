package xiaozhu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import xiaozhu.dto.QuestionDto;
import xiaozhu.mapper.QuestionMapper;
import xiaozhu.mapper.UserMapper;
import xiaozhu.model.Question;
import xiaozhu.model.User;

@Service
public class QuestionService {

	
	@Autowired
	UserMapper usermapper;
	
	@Autowired
	QuestionMapper questionMapper;
	
	public List<QuestionDto> list(Integer page, Integer size) {
		List<Question> list = questionMapper.list();
		List<QuestionDto> questionDtos=new ArrayList<>();
		for (Question question : list) {
			User user = usermapper.findUserById(question.getCreator());
			QuestionDto questionDto=new QuestionDto();
			BeanUtils.copyProperties(question, questionDto);
			questionDto.setUser(user);
			questionDtos.add(questionDto);
		} 
		return questionDtos;
	}
	
}
