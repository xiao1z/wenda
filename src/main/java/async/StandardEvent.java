package async;

public class StandardEvent implements Event{
	private EventType type;
	private int actorId;
	private int entityType;
	private int entityId;
	private int entityOwnerId;
	private int status = NORMAL_STATUS;
	private int priority = NORMAL_PRIORITY;
	
	
	
	public static final int NORMAL_STATUS = 0;
	
	public static final int REMOTE_STATUS = 1;
	
	public static final int LOW_PRIORITY = 0;
	public static final int NORMAL_PRIORITY = 1;
	public static final int HIGH_PRIORITY = 2;
	
	public EventType getType() {
		return type;
	}
	public StandardEvent setType(EventType type) {
		this.type = type;
		return this;
	}
	public int getActorId() {
		return actorId;
	}
	public StandardEvent setActorId(int actorId) {
		this.actorId = actorId;
		return this;
	}
	public int getEntityType() {
		return entityType;
	}
	public StandardEvent setEntityType(int entityType) {
		this.entityType = entityType;
		return this;
	}
	public int getEntityId() {
		return entityId;
	}
	public StandardEvent setEntityId(int entityId) {
		this.entityId = entityId;
		return this;
	}
	public int getEntityOwnerId() {
		return entityOwnerId;
	}
	public StandardEvent setEntityOwnerId(int entityOwnerId) {
		this.entityOwnerId = entityOwnerId;
		return this;
	}
	public int getStatus() {
		return status;
	}
	public StandardEvent setStatus(int status) {
		this.status = status;
		return this;
	}
	public int getPriority() {
		return priority;
	}
	public StandardEvent setPriority(int priority) {
		this.priority = priority;
		return this;
	}
	
}
