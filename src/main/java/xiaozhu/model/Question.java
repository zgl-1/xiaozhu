package xiaozhu.model;

import lombok.Data;

@Data
public class Question {
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
}
