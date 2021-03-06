package util;

public class RedisKeyUtil {
	private static final String SPLIT = ":";
	private static final String BIZ_LIKE = "LIKE";
	private static final String BIZ_DISLIKE = "DISLIKE";
	private static final String ASYNC_QUEUE = "ASYNC_QUEUE";
	private static final String QUESTION_ADD_CACHE = "QUESTION_ADD_CACHE";
	private static final String USER_CACHE = "USER_CACHE";
	private static final String FEED_FLOW = "FEED_FLOW";
	
	
	/*
	 * 返回该实体对应的key
	 */
	public static String getLikeKey(int entityId,int entityType)
	{
		return BIZ_LIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
	}
	
	public static String getUserCacheKey(int userId)
	{
		return USER_CACHE+SPLIT+userId;
	}
	
	public static String getDislikeKey(int entityId,int entityType)
	{
		return BIZ_DISLIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
	}
	
	public static String getAsyncQueueKey(int priority)
	{
		return ASYNC_QUEUE+SPLIT+priority;
	}
	
	public static String getQusetionAddCacheKey(int userId)
	{
		return QUESTION_ADD_CACHE+SPLIT+userId;
	}
	
	public static String getFeedFlowKey(int userId)
	{
		return FEED_FLOW+SPLIT+userId;
	}
	
	public static String getLoginTicketKey(String ticket)
	{
		return ticket;
	}
	
}
