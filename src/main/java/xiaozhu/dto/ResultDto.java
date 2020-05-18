
package xiaozhu.dto;

import lombok.Data;
import xiaozhu.exception.CustomErrorEnum;
import xiaozhu.exception.CustomException;

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
	public static ResultDto errorOf(CustomException e) {
		return errorOf(e.getCode(),e.getMessage());
	}
	
	
	public static ResultDto okOf() {
		ResultDto resultDto=new ResultDto();
		resultDto.setCode(200);
		resultDto.setMessage("请求成功");
		return resultDto;
	}
	public static ResultDto errorOf(CustomErrorEnum noLogin) {
		// TODO Auto-generated method stub
		return errorOf(noLogin.getCode(),noLogin.getMessgae());
	}
}    
