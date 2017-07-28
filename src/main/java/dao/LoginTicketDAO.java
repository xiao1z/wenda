package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.LoginTicket;


/*
 * v2版本废弃
 */
public interface LoginTicketDAO {
	String TABLE_NAME=" login_ticket ";
	String INSERT_FIELDS = " user_id,expired,status,ticket ";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	
	@Select({"SELECT ",SELECT_FIELDS," FROM ",TABLE_NAME, " WHERE ticket = #{ticket}"})
	LoginTicket selectByTicket(String  ticket);
	
	@Insert({"Insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values(#{userId},#{expired},#{status},#{ticket})"})
	int addTicket(LoginTicket loginTicket);
	
	@Update({"update ",TABLE_NAME,"set status=#{status} WHERE ticket = #{ticket}"})
	void updateStatus(@Param("status") int status,@Param("ticket") String ticket);
}
