package xiaozhu.exception;

public enum CustomErrorEnum implements ICustomErrorCode {
	QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不要换一个试试！"),
	TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复！");

	private String messgae;
	private Integer code;
	CustomErrorEnum(Integer code,String message) {
		// TODO Auto-generated constructor stub
		this.messgae=message;
		this.code=code;
	}
	@Override
	public Integer getCode() {
		return code;
	}
	@Override
	public String getMessgae() {
		return messgae;
	}
	
}
