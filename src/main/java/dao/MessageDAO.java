package dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import model.Message;

public interface MessageDAO {

	String TABLE_NAME=" message ";
	String INSERT_FIELDS = " from_id,to_id,content,create_date,status,conversation_id ";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS; 
	
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")"
			," values(#{fromId},#{toId},#{content},#{createDate},#{status},#{conversationId})"})
	public int addMessage(Message message);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{id}"})
	public Message selectMessageById(int id);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,
		"where conversation_id=#{conversationId} order by create_date desc limit #{offset},#{limit}"})
	public List<Message> selectMessageByConversationId(@Param("conversationId") String conversationId
														,@Param("offset") int offset
														,@Param("limit") int limit);
	
	/*
	 * 注：该函数取得的Message的ID表示的是该会话（conversation）的总消息数
	 */
	@Select({"select t.count as id,m.from_id,m.to_id,"
			+ "m.content,m.create_date,m.status,m.conversation_id from "
			+ "(select count(*) as count,max(create_date) as max_date "
			+ "from message where to_id=#{userId} or from_id=#{userId} group by conversation_id) t "
			+ "left join message as m on m.create_date=t.max_date "
			+ "order by create_date desc limit #{offset},#{limit}"})
	public List<Message> selectLastReplyMessagesByUserId(@Param("userId") int userId
														,@Param("offset") int offset
														,@Param("limit") int limit);
}
