package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import model.User;


public interface UserDAO {
	String TABLE_NAME=" user ";
	String INSERT_FIELDS = " name,password,salt,head_url ";
	String SELECT_FIELDS = " id "+INSERT_FIELDS;
	
	@Select({"SELECT * FROM ",TABLE_NAME, "WHERE id = #{id}"})
	User selectUserById(int id);
	
	@Select({"SELECT * FROM ",TABLE_NAME, " WHERE name = #{name}"})
	User selectUserByUsername(String  name);
	
	@Insert({"Insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values(#{name},#{password},#{salt},#{headUrl})"})
	int addUser(User user);
	
	
}
