package xiaozhu.dto;

import lombok.Data;

@Data
public class CommentDto {
	private Long parent;
	private String content;
	private Integer type;
}
