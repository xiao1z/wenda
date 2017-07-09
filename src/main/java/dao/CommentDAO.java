package dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import model.Comment;

public interface CommentDAO {
	String TABLE_NAME=" comment ";
	String INSERT_FIELDS = " user_id,entity_id,entity_type,content,create_date,status ";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS; 
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{id}"})
	public Comment getCommentById(int id);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where entity_id=#{entityId} and entity_type=#{entityType}"})
	public List<Comment>getCommentsByEntity(@Param("entityId") int entityId,@Param("entityType") int entityType);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where user_id=#{userId}"})
	public List<Comment> getCommentsByUserId(int userId);
	
	@Insert({"Insert into",TABLE_NAME,"(",INSERT_FIELDS,")"
		,"values(#{userId},#{entityId},#{entityType},#{content},#{createDate},#{status})"})
	public int addComment(Comment comment);
	
}
