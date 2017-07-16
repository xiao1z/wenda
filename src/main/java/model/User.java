package model;

public class User {
	
	public static final String DEFAULT_HEAD_URL = "/wenda/static/images/img/default_head.jpg";

	private int id;
	private String username;
	private String password;
	private String salt;
	private String headUrl;
	
	//系统用户的初始化在async包的EventConsumer类中，因为只有事件处理会用到系统用户
	public static final int SYSTEM_USER_ID = 1;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}



}
