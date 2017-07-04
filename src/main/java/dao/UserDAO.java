package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import model.User;


public interface UserDAO {
	String TABLE_NAME=" user ";
	String INSERT_FIELDS = " username,password,salt,head_url ";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	@Select({"SELECT ",SELECT_FIELDS," FROM ",TABLE_NAME, "WHERE id = #{id}"})
	User selectUserById(int id);
	
	@Select({"SELECT ",SELECT_FIELDS," FROM ",TABLE_NAME, " WHERE username = #{username}"})
	User selectUserByUsername(String  username);
	
	@Insert({"Insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values(#{username},#{password},#{salt},#{headUrl})"})
	int addUser(User user);
	
}
