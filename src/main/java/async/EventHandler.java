package async;

import java.util.List;

public interface EventHandler {
	void doHandle(Event event);
	
	List<EventType> getInterestedEventType();
}
