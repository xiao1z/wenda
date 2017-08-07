package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Follow;

public interface FollowDAO {
	
	String TABLE_NAME=" follow ";
	String INSERT_FIELDS = " entity_id,entity_type,follow_id,follow_type,create_date,status ";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS; 
	

	public int addFollowAndGetId(Follow follow);
	
	@Select({"select count(id) from ",TABLE_NAME,"where follow_id=#{entityId} and follow_type=#{entityType} and status=0"})
	public int getFollowerCountOfEntity(@Param("entityId") int entityId,@Param("entityType") int entityType);
	
	
	@Select({"select ",SELECT_FIELDS,"from ",TABLE_NAME,"where follow_id=#{entityId} and follow_type=#{entityType} and status=0 order by create_date desc"})
	public List<Follow> selectFollowerOfEntity(@Param("entityId") int entityId,@Param("entityType") int entityType);
	
	@Select({"select ",SELECT_FIELDS,"from ",TABLE_NAME,
		"where entity_id=#{entityId} and entity_type=#{entityType} and follow_type=#{followType} and status=0 order by create_date desc"})
	public List<Follow> selectFollowEntityOfEntity(@Param("entityId") int entityId
													,@Param("entityType") int entityType
													,@Param("followType") int followType);
	
	/*
	 * 测试两个实体是否具有有效关联关系
	 */
	@Select({"select id from ",TABLE_NAME,
	"where entity_id=#{entityId} and entity_type=#{entityType} and "
	+ "follow_id=#{followId} and follow_type=#{followType} and status=0 "})
	public String selectIdOfEntityAndfollow(@Param("entityId") int entityId
										,@Param("entityType") int entityType
										,@Param("followId") int followId
										,@Param("followType") int followType);
	
	@Update({"update ",TABLE_NAME,"set status=-1 where id=#{id}"})
	public int cancelFollow(int id);
}
