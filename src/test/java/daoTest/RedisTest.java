package daoTest;

import async.EventType;
import async.StandardEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import util.JSONUtil;

public class RedisTest {
	
	public static void main(String args[])
	{
		testSelect();
	}
	
	
	public static void testSelect()
	{
		
		JedisPool pool1 =new JedisPool("redis://localhost:6379/10");
		JedisPool pool2 =new JedisPool("redis://localhost:6379/11");
		Jedis jedis1 = pool1.getResource();
		System.out.println(jedis1.getDB());
		jedis1.select(6);
		System.out.println(jedis1.getDB()+" ss");
		
		Jedis jedis2 = pool2.getResource();
		System.out.println(jedis2.getDB());
		jedis2.select(6);
		System.out.println(jedis2.getDB());
		pool1.close();
		pool2.close();
		
		
		
	}
	
	public static void testEvent()
	{
		JedisPool pool =new JedisPool("redis://localhost:6379/10");
		Jedis jedis = pool.getResource();
		StandardEvent e = new StandardEvent();
		e.setActorId(0);
		e.setEntityId(0);
		e.setEntityOwnerId(0);
		e.setEntityType(0);
		e.setPriority(StandardEvent.NORMAL_PRIORITY);
		e.setStatus(StandardEvent.NORMAL_STATUS);
		e.setType(EventType.RAISE_COMMENT_EVENT);
		
		jedis.lpush("eventTest", JSONUtil.getJSONStringOfEvent(e));
		pool.close();
	}
}
