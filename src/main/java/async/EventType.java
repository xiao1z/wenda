package async;

public enum EventType {
	LIKE(0),
	RAISE_COMMENT(1),
	LOGIN(2),
	MAIL(3),
	RAISE_QUESTION(4),
	FOLLOW(5),
	NEW_ANSWER(6),
	CANCEL_FOLLOW(7);
	
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
