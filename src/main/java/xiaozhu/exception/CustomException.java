package xiaozhu.exception;


public class CustomException extends RuntimeException{
	private String message;
	private Integer code;
	public CustomException(ICustomErrorCode errorCode) {
		this.code=errorCode.getCode();
		this.message=errorCode.getMessgae();
	}
	@Override
	public String getMessage() {
		return message;
	}
	public Integer getCode() {
		return code;
	}
	
}
