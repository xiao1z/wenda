package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisAdapter implements InitializingBean{
	private JedisPool pool=null;
	private static final Logger logger = LoggerFactory.getLogger(RedisAdapter.class);

	
	
	
	public long sadd(String key,String value)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.sadd(key, value);
		}catch(Exception e)
		{
			logger.error("redis集合增加元素错误 "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return 0;
	}
	
	public long srem(String key,String value)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.srem(key, value);
		}catch(Exception e)
		{
			logger.error("redis集合删除元素错误 "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return 0;
	}
	
	public long scard(String key)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.scard(key);
		}catch(Exception e)
		{
			logger.error("redis集合获取集合元素数量错误 "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return 0;
	}
	
	public boolean sismember(String key,String value)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.sismember(key, value);
		}catch(Exception e)
		{
			logger.error("redis集合删除元素错误 "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return false;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		pool =new JedisPool("redis://localhost:6379/10");

	}

}
