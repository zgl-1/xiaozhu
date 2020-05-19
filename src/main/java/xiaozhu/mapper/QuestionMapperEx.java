package xiaozhu.mapper;

import xiaozhu.model.Question;

public interface QuestionMapperEx {
    
    int incView(Question record);
    int incComment(Question record);
}