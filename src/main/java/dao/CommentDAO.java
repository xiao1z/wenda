package dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import model.Comment;

public interface CommentDAO {
	String TABLE_NAME=" comment ";
	String INSERT_FIELDS = " user_id,entity_id,entity_type,content,create_date,status,img_count ";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS; 
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{id}"})
	public Comment getCommentById(int id);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where entity_id=#{entityId} and entity_type=#{entityType} limit #{offset},#{limit}"})
	public List<Comment> getCommentsByEntity(@Param("entityId") int entityId,
									@Param("entityType") int entityType,
									@Param("offset") int offset,
									@Param("limit") int limit);
	
	
	@Select({"select count(id) from",TABLE_NAME,"where entity_id=#{entityId} and entity_type=#{entityType}"})
	public int getCommentsCountByEntity(@Param("entityId") int entityId,@Param("entityType") int entityType);
	
	//该查询会使用user_id列上的索引
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where user_id=#{userId} and entity_type=#{entityType} order by create_date desc"})
	public List<Comment> getCommentsByUserIdAndEntityType(@Param("userId") int userId,@Param("entityType") int entityType);
	
	@Insert({"Insert into",TABLE_NAME,"(",INSERT_FIELDS,")"
		,"values(#{userId},#{entityId},#{entityType},#{content},#{createDate},#{status},#{imgCount})"})
	public int addComment(Comment comment);
	
}
