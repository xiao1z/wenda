package model;

import java.util.Date;

public class UserInfo {
	private int id;
	private int userId;
	private Date  registerDate;
	private SEX sex = SEX.SECRECY;
	private String description;
	private String location;
	private String   otherInfo;
	private int status = NORMAL_STATUS;

	public static final int NORMAL_STATUS = 0;
	
	public enum SEX{
		MALE('M'),
		FEMALE('F'),
		SECRECY('S');
		private char value;
		
		SEX(char value)
		{
			this.value=value;
		}
		
		public char getValue()
		{
			return this.value;
		}
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getRegisterDate() {
		return registerDate;
	}


	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}


	public char getSex() {
		return sex.getValue();
	}


	public void setSex(SEX sex) {
		this.sex = sex;
	}
	
	public void setSex(char sex) {
		if(sex=='F')
			this.setSex(SEX.FEMALE);
		if(sex=='M')
			this.setSex(SEX.MALE);
		if(sex=='S')
			this.setSex(SEX.SECRECY);
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getOtherInfo() {
		return otherInfo;
	}


	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


}
