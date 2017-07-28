package async;

public enum EventType {
	LIKE_EVENT(0),
	RAISE_COMMENT_EVENT(1),
	LOGIN_EVENT(2),
	MAIL_EVENT(3),
	RAISE_QUESTION_EVENT(4),
	FOLLOW_EVENT(5),
	NEW_ANSWER_EVENT(6),
	CANCEL_FOLLOW_EVENT(7);
	
	private int index;
	
	EventType(int index)
	{
		this.index = index;
	}
	
	public int getIndex()
	{
		return this.index;
	}
}
