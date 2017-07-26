package daoTest;

import async.EventType;
import async.StandardEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import util.JSONUtil;

public class RedisTest {
	
	public static void main(String args[])
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
		e.setType(EventType.RAISE_COMMENT);
		
		jedis.lpush("eventTest", JSONUtil.getJSONStringOfEvent(e));
	}
}
