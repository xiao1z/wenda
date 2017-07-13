package daoTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTest {
	
	public static void main(String args[])
	{
		JedisPool pool =new JedisPool("redis://localhost:6379/10");
		Jedis jedis = pool.getResource();
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
	}
}
