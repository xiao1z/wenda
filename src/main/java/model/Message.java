package model;

import java.util.Date;


public class Message {
	
	
	private int id;
	private int fromId = Integer.MIN_VALUE;
	private int toId = Integer.MIN_VALUE;//表示未初始化
	private String content;
	private Date createDate;
	private int status;
	private String conversationId;
	
	public static final int DELETED_STATUS = -1;
	public static final int HAS_READ_STATUS = 1;
	public static final int NOT_READ = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFromId() {
		return fromId;
	}
	public void setFromId(int fromId) {
		this.fromId = fromId;
		if(this.toId!=Integer.MIN_VALUE)
		{
			this.setConversationIdAuto();
		}
	}
	public int getToId() {
		return toId;
	}
	public void setToId(int toId) {
		this.toId = toId;
		if(this.fromId!=Integer.MIN_VALUE)
		{
			this.setConversationIdAuto();
		}
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getConversationId() {
		return conversationId;
	}
	
	private void setConversationIdAuto() {
		if(this.fromId<this.toId)
		{
			this.conversationId=String.format("%d_%d", fromId,toId);
		}else
		{
			this.conversationId=String.format("%d_%d", toId,fromId);
		}
	}
}
