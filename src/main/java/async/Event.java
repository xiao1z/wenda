package async;

public interface Event {
	public int getPriority();
	
	public EventType getType();
}
