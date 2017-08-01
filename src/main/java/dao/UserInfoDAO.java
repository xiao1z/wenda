package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import model.UserInfo;

public interface UserInfoDAO {

	
	
	String TABLE_NAME=" user_info ";
	String INSERT_FIELDS = " user_id,register_date,sex,description,location,other_info,status ";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS; 
	
	@Insert({"Insert into ",TABLE_NAME,"(",INSERT_FIELDS,") "
			+ "values(#{userId},#{registerDate},#{sex},#{description},#{location},#{otherInfo},#{status})"})
	public int addUserInfo(UserInfo userInfo);
	
	public int updateUserInfo(UserInfo userInfo);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,"where user_id=#{userId}"})
	public UserInfo selectUserInfoByUserId(int userId);
}
