package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.FeedDAO;
import dao.MybatisSqlSessionFactory;
import model.EntityType;
import model.Feed;
import model.Question;
import model.User;
import util.RedisKeyUtil;

@Service
public class FeedService {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedService.class);
	
	@Autowired
	private RedisDBForNormalService redisDBForNormalService;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConfigService configService;
	
	public int addFeed(Feed feed)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FeedDAO feedDAO;
		int result = -1;
		try{
			feedDAO = session.getMapper(FeedDAO.class);
			result = feedDAO.addFeed(feed);
			session.commit();
			pushFeed(feed);
		}catch(Exception e)
		{
			logger.error("添加feed错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	public List<Feed> getFeedsOfUser(int userId,int maxId,int count)
	{
		String key = RedisKeyUtil.getFeedFlowKey(userId);
		List<String> cacheFeed = redisDBForNormalService.lrange(key, 0, -1);
		int min = Integer.MAX_VALUE;
		if(cacheFeed!=null)
		{
			List<Feed> feedsList = new LinkedList<Feed>();
			Feed temp ;
			for(String id_:cacheFeed)
			{
				int id = Integer.parseInt(id_);
				if(id<min)
					min = id;
				if(id<maxId)
				{
					temp = getFeedById(id);
					if(temp!=null)
						feedsList.add(temp);
				}
			}
			redisDBForNormalService.expire(key, configService.getFeed_MAX_CACHE_SECONDS());//刷缓存过期时间
			
			//如果缓存中数量不足count,再去数据库拉feed
			if(feedsList.size()<count)
			{
				feedsList.addAll(pullFeeds(userId,min,count-feedsList.size()));
			}
			return feedsList;
		}else
		{
			return pullFeeds(userId, maxId, count);
		}
	}
	
	public Feed getFeedById(int feedId)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FeedDAO feedDAO;
		Feed result = null;
		try{
			feedDAO = session.getMapper(FeedDAO.class);
			result = feedDAO.selectFeedById(feedId);
		}catch(Exception e)
		{
			logger.error("根据Id获取feed错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	

	private List<Feed> pullFeeds(int userId,int maxId,int count)
	{
		List<Feed> questionFeeds = this.getFeedsOfFollowQuestion(userId, maxId, count);
		List<Feed> userFeeds = this.getFeedsOfFollowUser(userId, maxId, count);
		
		questionFeeds=filterFeeds(questionFeeds);
		userFeeds=filterFeeds(userFeeds);

		List<Feed> result = new ArrayList<Feed>();
		
		if(questionFeeds!=null)
			result.addAll(questionFeeds);
		if(userFeeds!=null)
			result.addAll(userFeeds);
		
		Collections.sort(result, new Comparator<Feed>(){

			@Override
			public int compare(Feed o1, Feed o2) {
				return -o1.getCreateDate().compareTo(o2.getCreateDate());
			}
			
		});
		
		//将多取出来的feed存入缓存
		if(result.size()>count)
		{
			for(int i=count;i<result.size();i++)
			{
				addFeedToCache(result.get(i),userId);
			}
			
			return result.subList(0, count);
		}
		
		return result;
	}
	
	
	/*
	 * 对拉取出来的feed进行过滤等操作（包括合并相关feed、删除重复feed等）
	 */
	private List<Feed> filterFeeds(List<Feed> feeds)
	{
		return feeds;
	}
	
	public List<Feed> getFeedsOfFollowUser(int userId,int maxId,int count)
	{
		List<User> followUser = followService.getFollowUserByUserId(userId);
		if(followUser==null||followUser.size()==0)
			return null;
		List<Integer> followUserIds = new LinkedList<Integer>();
		for(User user:followUser)
		{
			followUserIds.add(user.getId());
		}
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FeedDAO feedDAO;
		List<Feed> result = null;
		try{
			feedDAO = session.getMapper(FeedDAO.class);
			result = feedDAO.selectFeedsOfFollowUser(maxId, followUserIds, count);
		}catch(Exception e)
		{
			logger.error("根据Id获取关注的人的feed错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	public List<Feed> getFeedsOfFollowQuestion(int userId,int maxId,int count)
	{
		List<Question> followQuestion = followService.getFollowQuestionByUserId(userId);
		if(followQuestion==null||followQuestion.size()==0)
			return null;
		List<Integer> followQuestionIds = new LinkedList<Integer>();
		for(Question question:followQuestion)
		{
			followQuestionIds.add(question.getId());
		}
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FeedDAO feedDAO;
		List<Feed> result = null;
		try{
			feedDAO = session.getMapper(FeedDAO.class);
			result = feedDAO.selectFeedsOfFollowQuestion(maxId, followQuestionIds,count);
		}catch(Exception e)
		{
			logger.error("根据Id获取关注问题的feed错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	public List<Feed> getFeedsOfDefault(int maxId,int count)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FeedDAO feedDAO;
		List<Feed> result = null;
		try{
			feedDAO = session.getMapper(FeedDAO.class);
			result = filterFeeds(feedDAO.selectFeedsOfFollowQuestion(maxId, null,count/2));
			List<Feed> result_ = filterFeeds(feedDAO.selectFeedsOfFollowUser(maxId, null,count/2));
			result.addAll(result_);
			
		}catch(Exception e)
		{
			logger.error("获取默认feeds列表错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	private void addFeedToCache(Feed feed,int userId)
	{
		String key = RedisKeyUtil.getFeedFlowKey(userId);
		if(redisDBForNormalService.llen(key)<configService.getFeed_MAX_CACHE_SZIE())
		{
			redisDBForNormalService.lpush(key, String.valueOf(feed.getId()));
			if(redisDBForNormalService.llen(key)==1)
			{
				redisDBForNormalService.expire(key, configService.getFeed_MAX_CACHE_SECONDS());
			}
		}else
		{
			redisDBForNormalService.rpop(key);
			redisDBForNormalService.lpush(key, String.valueOf(feed.getId()));
		}
	}
	
	private int pushFeed(Feed feed)
	{
		if(feed.getActorType()==EntityType.QUESTION)
		{
			List<User> userList = followService.getFollowerOfQuestionByQuestionId(feed.getActorId());
			for(User user:userList)
			{
				if(userService.isActiveUser(user))
				{
					String key = RedisKeyUtil.getFeedFlowKey(user.getId());
					if(redisDBForNormalService.llen(key)<configService.getFeed_MAX_CACHE_SZIE())
					{
						redisDBForNormalService.lpush(key, String.valueOf(feed.getId()));
						if(redisDBForNormalService.llen(key)==1)
						{
							redisDBForNormalService.expire(key, configService.getFeed_MAX_CACHE_SECONDS());
						}
					}else
					{
						redisDBForNormalService.rpop(key);
						redisDBForNormalService.lpush(key, String.valueOf(feed.getId()));
					}
				}
			}
		}else
		{
			List<User> userList = followService.getFollowerOfUserByUserId(feed.getActorId());
			for(User user:userList)
			{
				if(userService.isActiveUser(user))
				{
					String key = RedisKeyUtil.getFeedFlowKey(user.getId());
					if(redisDBForNormalService.llen(key)<configService.getFeed_MAX_CACHE_SZIE())
					{
						redisDBForNormalService.lpush(key, String.valueOf(feed.getId()));
						if(redisDBForNormalService.llen(key)==1)
						{
							redisDBForNormalService.expire(key,configService.getFeed_MAX_CACHE_SECONDS());
						}
					}else
					{
						redisDBForNormalService.rpop(key);
						redisDBForNormalService.lpush(key, String.valueOf(feed.getId()));
					}
				}
			}
		}
		return 0;
	}
}
