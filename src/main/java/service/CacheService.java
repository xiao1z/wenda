package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import model.User;
import util.JSONUtil;
import util.RedisKeyUtil;

@Service
public class CacheService implements InitializingBean{
	
	@Autowired
	RedisDBForNormalService redisDBForNormalService;
	
	
	@Autowired
	ConfigService configService;
	
	/*
	 * staus值和含义：
	 * 0表示正常
	 * -1 表示缓存不可用
	 * -2表示停止添加新的记录
	 */
	private volatile int status = 0;
	
	
	private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
	
	public User getCachedUser(int userId)
	{
		if(status==-1)
			return null;
		try
		{
			String redisKey = RedisKeyUtil.getUserCacheKey(userId);
			String value = redisDBForNormalService.get(redisKey);
			if(value==null)
				return null;
			else
			{
				return JSON.parseObject(value, User.class);
			}
		}catch(Exception e)
		{
			logger.error("从缓存中获取user错误 "+e.getMessage());
			return null;
		}
	}
	
	public void addUserToCache(User user)
	{
		if(status!=0)
			return;
		try
		{
			String redisKey = RedisKeyUtil.getUserCacheKey(user.getId());
			String jsonStringOfUser = JSONUtil.getJSONStringOfUser(user);
			redisDBForNormalService.setex(redisKey, 
					configService.getCache_USER_CACHE_SECONDS(), jsonStringOfUser);
		}catch(Exception e)
		{
			logger.error("向缓存中添加user错误 "+e.getMessage());
		}
		
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		//每隔10分钟判断缓存是否超过了最大缓存数量,并修改缓存状态
		Thread a = new Thread(new Runnable(){
			
			int maxCacheSize = configService.getCache_MAX_USER_CACHE_SIZE();
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(redisDBForNormalService.dbsize()>maxCacheSize)
				{
					status = -2;
				}else
				{
					status = 0;
				}
				
				try {
					Thread.sleep(10*60*1000);
				} catch (InterruptedException e) {
					logger.error("检测缓存数量错误 "+e.getMessage());
				}
			}
			
		});
		a.start();
	}
}
