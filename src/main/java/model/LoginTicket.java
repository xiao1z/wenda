package model;

import java.util.Date;

public class LoginTicket {
	private int id;
	private int userId;
	private String ticket;
	private Date expired;
	
	//status:0表示有效，-1表示失效
	private int status;
	
	//单位为毫秒
	public final static long EXPIRED_TIME_MILLISECONDS = 30*24*60*60*1000;
	
	//单位为毫秒
	public final static long EXPIRED_TIME_MILLISECONDS_IF_NOTREMEBERME = 1*60*60*1000;
	
	//单位为秒
	public final static int EXPIRED_TIME_SECONDS = 30*24*60*60;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public Date getExpired() {
		return expired;
	}
	public void setExpired(Date expired) {
		this.expired = expired;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
