package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.EntityType;
import util.RedisKeyUtil;

@Service
public class LikeService {
	
	@Autowired
	RedisAdapter redisAdapter;
	
	/*
	 * 这里应该考虑事务
	 */
	public long likeComment(int userId,int commentId)
	{
		String likeKey = RedisKeyUtil.getLikeKey(commentId, EntityType.COMMENT);
		String dislikeKey = RedisKeyUtil.getDislikeKey(commentId, EntityType.COMMENT);
		redisAdapter.srem(dislikeKey, String.valueOf(userId));
		return redisAdapter.sadd(likeKey, String.valueOf(userId));
	}
	
	public long dislikeComment(int userId,int commentId)
	{
		String likeKey = RedisKeyUtil.getLikeKey(commentId, EntityType.COMMENT);
		String dislikeKey = RedisKeyUtil.getDislikeKey(commentId, EntityType.COMMENT);
		redisAdapter.srem(likeKey, String.valueOf(userId));
		return redisAdapter.sadd(dislikeKey, String.valueOf(userId));
	}
	
	public long getCommentLikeCount(int commentId)
	{
		String key = RedisKeyUtil.getLikeKey(commentId, EntityType.COMMENT);
		return redisAdapter.scard(key);
	}
	
	public void cancelLikeOrDisLike(int userId,int commentId)
	{
		String likeKey = RedisKeyUtil.getLikeKey(commentId, EntityType.COMMENT);
		String dislikeKey = RedisKeyUtil.getDislikeKey(commentId, EntityType.COMMENT);
		redisAdapter.srem(likeKey, String.valueOf(userId));
		redisAdapter.srem(dislikeKey, String.valueOf(userId));
	}
	
	public boolean isLikeComment(int userId,int commentId)
	{
		String key = RedisKeyUtil.getLikeKey(commentId, EntityType.COMMENT);
		if(redisAdapter.sismember(key, String.valueOf(userId)))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public boolean isDislikeComment(int userId,int commentId)
	{
		String key = RedisKeyUtil.getDislikeKey(commentId, EntityType.COMMENT);
		if(redisAdapter.sismember(key, String.valueOf(userId)))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
}
