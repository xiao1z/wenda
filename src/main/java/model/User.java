package model;

public class User {
	
	private static User DEFAULT_USER;  //匿名默认用户
	public static final String DEFAULT_HEAD_URL = "/wenda/static/images/img/default_head.jpg";
	static
	{
		DEFAULT_USER=new User();
		DEFAULT_USER.setId(0);
		DEFAULT_USER.setUsername("(游客)");
		DEFAULT_USER.setPassword("admin");
		DEFAULT_USER.setHeadUrl(DEFAULT_HEAD_URL);
		DEFAULT_USER.setSalt("admin");
	}
	
	private int id;
	private String username;
	private String password;
	private String salt;
	private String headUrl;
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

	public static User getDEFAULT_USER() {
		return DEFAULT_USER;
	}

}
