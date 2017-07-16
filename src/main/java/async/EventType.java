package async;

public enum EventType {
	LIKE(0),
	COMMENT(1),
	LOGIN(2),
	MAIL(3);
	
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
