package xiaozhu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xiaozhu.dto.PaginationDto;
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

	public PaginationDto list(Integer page, Integer size) {
		Integer count = questionMapper.count();
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
		List<Question> list = questionMapper.list(offset, size);
		List<QuestionDto> questionDtos = new ArrayList<>();
		
		for (Question question : list) {
			User user = usermapper.findUserById(question.getCreator());
			QuestionDto questionDto = new QuestionDto();
			BeanUtils.copyProperties(question, questionDto);
			questionDto.setUser(user);
			questionDtos.add(questionDto);
		}
		paginationDto.setQuestions(questionDtos);

		
		return paginationDto;
	}
	
	public PaginationDto list(long userid,Integer page, Integer size) {
		Integer count = questionMapper.count();
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
		List<Question> list = questionMapper.listByUser(userid,offset, size);
		List<QuestionDto> questionDtos = new ArrayList<>();
		
		for (Question question : list) {
			User user = usermapper.findUserById(question.getCreator());
			QuestionDto questionDto = new QuestionDto();
			BeanUtils.copyProperties(question, questionDto);
			questionDto.setUser(user);
			questionDtos.add(questionDto);
		}
		paginationDto.setQuestions(questionDtos);

		
		return paginationDto;
	}

}
