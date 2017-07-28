package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisDBForNormalService implements InitializingBean{
	
	@Autowired
	ConfigService configService;
	
	private JedisPool pool=null;
	private static final Logger logger = LoggerFactory.getLogger(RedisDBForNormalService.class);
	
	
	public long dbsize()
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.dbSize();
		}catch(Exception e)
		{
			logger.error("redis切换数据库错误"+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return -1;
	}
	
	public String setex(String key,int seconds,String value)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.setex(key, seconds, value);
		}catch(Exception e)
		{
			logger.error("redis队列从队头增加元素错误 "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return null;
	}
	
	public long expire(String key,int seconds)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.expire(key, seconds);
		}catch(Exception e)
		{
			logger.error("redis队列从队头增加元素错误 "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return 0;
	}
	
	
	public List<String> lrange(String key,int start,int end)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.lrange(key, start, end);
		}catch(Exception e)
		{
			logger.error("redis队列从队头增加元素错误 "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return null;
	}
	
	public long lpush(String key,String value)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.lpush(key,value);
		}catch(Exception e)
		{
			logger.error("redis队列从队头增加元素错误 "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return 0;
	}
	
	public String rpop(String key)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.rpop(key);
		}catch(Exception e)
		{
			logger.error("redis队列从队头增加元素错误 "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return null;
	}

	
	
	public long del(String key)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.del(key);
		}catch(Exception e)
		{
			logger.error("redis del元素错误 "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return 0;
	}
	
	
	public String get(String key)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.get(key);
		}catch(Exception e)
		{
			logger.error("redis get元素错误 "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return null;
	}
	public long llen(String key)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.llen(key);
		}catch(Exception e)
		{
			logger.error("redis获取列表长度错误 "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return 0;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		pool =new JedisPool(configService.getRedis_NORMAL_SERVICE_POOL_LOC());

	}

}
