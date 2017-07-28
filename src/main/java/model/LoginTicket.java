package model;

public class LoginTicket {
	
	//v2版本移除
	//private int id;
	
	private int userId;
	private String ticket;
	
	//v2版本移除 ticket过期时间有redis控制
	//private Date expired;
	
	
	//v2版本移除
	//status:0表示有效，-1表示失效
	//private int status;
	
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

}
