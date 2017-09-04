package model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

	

	private int id;
	
	@NotNull(message="{user.username.notNull}")
	@Size(min = 2, max = 14,message="{user.username.length}")
	private String username;
	
	@NotNull(message="{user.password.notNull}")
	@Size(min = 2, max = 14,message="{user.password.length}")
	private String password;
	private String salt;
	private String headUrl;
	private String nickname;
	private int  status = NOMROL_STATUS_TYPE;
	private String briefIntroduction;
	
	public static final int NOMROL_STATUS_TYPE = 0;
	public static final int MANAGER_STATUS_TYPE = 1;
	
	
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
	

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}



}
