package xiaozhu.enums;

public enum CommentTypeEnum {
	QUESTION(1),
	COMMENT(2);
	
	
	public Integer getType() {
		return type;
	}
	private Integer type;
	CommentTypeEnum(Integer type)
	{
		this.type=type;
	}
	public static boolean isExist(Integer type2) {
		// TODO Auto-generated method stub
		for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
			if(commentTypeEnum.getType()==type2) {
				return true;
			}
		}
		return false;
	}
}
