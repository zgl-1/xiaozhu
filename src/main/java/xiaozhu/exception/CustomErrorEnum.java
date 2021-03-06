package xiaozhu.exception;

public enum CustomErrorEnum implements ICustomErrorCode {
	QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不要换一个试试！"),
	TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复！"),
	NO_LOGIN(2003,"未登录，不能进行评论，请先登录！"),
	SYS_ERROR(2004,"你的服务器已经冒烟了！"),
	TYPE_PARAM_WRONG(2005,"评论类型错误或不存在！"),
	COMMENT_NOT_FOUND(2005,"回复的评论不存在了，要不要换一个试试！"),
	COMENT_IS_EMPTY(2005,"输入内容不为空！"),
	READ_NOTIFICATION_FAIL(2006,"你这是在读别人的信息呢！"),
	NOTIFICATION_NOT_FOUND(2007,"消息不在了！"),
	FILE_UPLOAD_FAIL(2008,"图片上传失败！");

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
