package async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import dao.MybatisSqlSessionFactory;
import dao.UserDAO;
import model.User;
import service.ConfigService;
import service.RedisDBForKeyService;
import service.UserService;
import util.RedisKeyUtil;

@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware{
	
	
	private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

	@Autowired
	RedisDBForKeyService redisDBForKeyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ConfigService configService;
	
	Map<EventType,List<EventHandler>> configs = new HashMap<EventType,List<EventHandler>>();
	private ApplicationContext applicationContext;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
		//服务器第一运行，数据库中还没有系统用户的信息
		if(userService.getUser(User.SYSTEM_USER_ID)==null)
		{
			User user = new User();
			user.setHeadUrl(configService.getUser_DEFAULT_HEAD_URL());
			user.setPassword("");
			user.setSalt("");
			user.setUsername("系统通知");
			SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
			UserDAO userDAO;
			try{
				userDAO = session.getMapper(UserDAO.class);
				userDAO.addUser(user);
				session.commit();
			}catch(Exception e)
			{
				logger.error("添加系统用户错误 "+e.getMessage());
			}finally
			{
				if(session!=null)
				{
					session.close();
				}
			}
		}
		
		Map<String,EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
		if(beans==null)
			return;
		for(Map.Entry<String, EventHandler> entry:beans.entrySet())
		{
			List<EventType> list = entry.getValue().getInterestedEventType();
			
			for(EventType eventType:list)
			{
				if(configs.containsKey(eventType))
				{
					configs.get(eventType).add(entry.getValue());
				}else
				{
					ArrayList<EventHandler> newlist = new ArrayList<EventHandler>();
					newlist.add(entry.getValue());
					configs.put(eventType, newlist);
				}
			}
		}
		
		Thread consumer = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true)
				{
					String key = RedisKeyUtil.getAsyncQueueKey(StandardEvent.NORMAL_PRIORITY);
					List<String> list = redisDBForKeyService.brpop(0,key);
					if(list!=null)
					{
						for(String jsonString:list)
						{
							if(jsonString.equals(key))
								continue;
							StandardEvent event = JSON.parseObject(jsonString,StandardEvent.class);
							if(!configs.containsKey(event.getType()))
							{
								logger.error("不能识别的事件类型  ");
								continue;
							}
							
							for(EventHandler eventHandler:configs.get(event.getType()))
							{
								eventHandler.doHandle(event);
							}
						}
					}
				}
			}
			
		});
		
		consumer.start();
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
	
}
