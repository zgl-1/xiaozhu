package xiaozhu.mapper;

import java.util.List;

import xiaozhu.dto.QuestionQueryDto;
import xiaozhu.model.Question;

public interface QuestionMapperEx {
    
    int incView(Question record);
    int incComment(Question record);
    List<Question> selectRelated(Question question);
	Integer countBySearch(QuestionQueryDto questionQueryDto);
	List<Question> selectBySearch(QuestionQueryDto questionQueryDto);
}