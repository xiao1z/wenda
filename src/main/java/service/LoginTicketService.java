package service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.LoginTicket;
import util.RedisKeyUtil;



@Service
public class LoginTicketService {
	
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private RedisDBForKeyService redisDBForKeyService;
	
	
	public String addTicket(int userId,boolean rememberMe){
		LoginTicket loginTicket = new LoginTicket();
		loginTicket.setUserId(userId);
		loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
		
		int expireSeconds =  -1;
		if(rememberMe)
			expireSeconds = configService.getLoginTicket_EXPIRED_TIME();
		else
			expireSeconds = configService.getLoginTicket_EXPIRED_TIME_IF_NOTREMEBERME();
		
		addLoginTicketToRedisDB(loginTicket,expireSeconds);
		
		return loginTicket.getTicket();
	}
	
	private void addLoginTicketToRedisDB(LoginTicket loginTicket,int expireSeconds)
	{
		String redisKey = RedisKeyUtil.getLoginTicketKey(loginTicket.getTicket());
		
		//如果这个ticket在redis中已经存在，则重新随机一个uuid
		if(redisDBForKeyService.get(redisKey)!=null)
		{
			loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
		}
		redisDBForKeyService.setex(redisKey,expireSeconds,String.valueOf(loginTicket.getUserId()));
	}
	
	/*
	 * 如果验证成功，即Ticket有效，返回userId
	 * 否则，返回-1
	 */
	public int validateTicket(String ticket)
	{
		String redisKey = RedisKeyUtil.getLoginTicketKey(ticket);
		String value = redisDBForKeyService.get(redisKey);
		if(value!=null)
		{
			return Integer.parseInt(value);
		}else
		{
			return -1;
		}
	}
	
	/*
	 * 使给定的ticket无效
	 */
	public void discardTicket(String ticket)
	{
		String redisKey = RedisKeyUtil.getLoginTicketKey(ticket);
		redisDBForKeyService.del(redisKey);
	}
}
