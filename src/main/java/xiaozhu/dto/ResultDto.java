
package xiaozhu.dto;

import lombok.Data;

@Data
public class ResultDto {
	private Integer code;
	private String message;
	public static ResultDto errorOf(Integer code,String message) {
		ResultDto resultDto=new ResultDto();
		resultDto.setCode(code);
		resultDto.setMessage(message);
		return resultDto;
	}
}    
