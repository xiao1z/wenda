package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.User;


public interface UserDAO {
	String TABLE_NAME=" user ";
	String INSERT_FIELDS = " username,password,salt,head_url,nickname,status,brief_introduction";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	@Select({"SELECT ",SELECT_FIELDS," FROM ",TABLE_NAME, "WHERE id = #{id}"})
	User selectUserById(int id);
	
	@Select({"SELECT ",SELECT_FIELDS," FROM ",TABLE_NAME, " WHERE username = #{username}"})
	User selectUserByUsername(String  username);
	
	@Insert({"Insert into ",TABLE_NAME,"(",INSERT_FIELDS,") "
			+ "values(#{username},#{password},#{salt},#{headUrl},#{nickname},#{status},#{briefIntroduction})"})
	int addUser(User user);
	
	@Update({"update ",TABLE_NAME,"set head_url=#{headUrl} where id=#{userId}"})
	int updateHeadUrl(@Param("userId")int userId,@Param("headUrl")String headUrl);
	
	@Update({"update ",TABLE_NAME,"set brief_introduction=#{briefIntroduction} where id=#{userId}"})
	int updateBriefIntroduction(@Param("userId")int userId,@Param("briefIntroduction")String briefIntroduction);
	
}
