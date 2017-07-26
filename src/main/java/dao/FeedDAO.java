package dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import model.Feed;

public interface FeedDAO {
	
	String TABLE_NAME=" feed ";
	String INSERT_FIELDS = " type,actor_id,actor_type,create_date,data ";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS; 
	
	
	@Select({"select",SELECT_FIELDS," from ",TABLE_NAME,"where id=#{feedId}"})
	public Feed selectFeedById(int feedId);
	
	//查找关注的人的新鲜事
	public List<Feed> selectFeedsOfFollowUser(@Param("maxId") int maxId,
											@Param("userIds") List<Integer> userIds,
											@Param("count") int count);
	
	//查找收藏的问题的新鲜事
	public List<Feed> selectFeedsOfFollowQuestion(@Param("maxId") int maxId,
												@Param("questionIds") List<Integer> questionIds,
												@Param("count") int count);
	
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values(#{type},#{actorId},#{actorType},#{createDate},#{data})"})
	public int addFeed(Feed feed);
	
	
}
