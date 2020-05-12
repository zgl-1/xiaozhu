package xiaozhu.dto;

import lombok.Data;
import xiaozhu.model.User;

@Data
public class QuestionDto {
	private long id;
	private String title;
	private String description;
	private String tag;
	private long gmtCreate;
	private long gmtModified;
	private long creator;
	private Integer viewCount;
	private Integer commentCount;
	private Integer likeCount; 
	private User user;
}
