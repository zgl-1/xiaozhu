package xiaozhu.dto;

import lombok.Data;

@Data
public class CommentDto {
	private Long parentId;
	private String content;
	private Integer type;
}
