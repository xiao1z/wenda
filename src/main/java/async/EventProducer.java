package async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.RedisAdapter;
import util.JSONUtil;
import util.RedisKeyUtil;

@Service
public class EventProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

	
	@Autowired
	RedisAdapter redisAdapter;
	
	
	
	public boolean fireEvent(Event e)
	{
		String key = RedisKeyUtil.getAsyncQueueKey(e.getPriority());
		try
		{
			if(redisAdapter.lpush(key, JSONUtil.getJSONStringOfEvent(e))>0)
			{
				return true;
			}
			else return false;
		}catch(Exception exception)
		{
			logger.error("发布事件错误 "+exception.getMessage());
			return false;
		}
	}
}
