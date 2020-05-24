package xiaozhu.mapper;

import java.util.List;

import xiaozhu.model.Question;

public interface QuestionMapperEx {
    
    int incView(Question record);
    int incComment(Question record);
    List<Question> selectRelated(Question question);
}