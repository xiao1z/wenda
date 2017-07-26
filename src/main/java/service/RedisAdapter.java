package service;

import java.util.List;

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
	
	public String set(String key,String value)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.set(key,value);
		}catch(Exception e)
		{
			logger.error("redis set元素错误 "+e.getMessage());
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
	
	public List<String> brpop(int timeOut,String key)
	{
		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			return jedis.brpop(timeOut,key);
		}catch(Exception e)
		{
			logger.error("（阻塞）redis队列从队尾获取元素错误  "+e.getMessage());
		}finally
		{
			if(jedis!=null)
				jedis.close();
		}
		return null;
	}
	
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
		pool =new JedisPool("redis://localhost:6379/10");

	}

}
