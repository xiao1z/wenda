package intercepter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import model.HostHolder;
import service.LoginTicketService;
import service.RedisDBForKeyService;
import service.UserService;
import util.RedisKeyUtil;


@Component
public class PassportIntercepter implements HandlerInterceptor{

	
	private static final Logger logger = LoggerFactory.getLogger(PassportIntercepter.class);

	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	RedisDBForKeyService redisDBForKeyService;
	
	@Autowired
	LoginTicketService loginTicketService;
	
	@Autowired
	UserService userService;
	
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		hostHolder.clear();
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView modelAndView)
			throws Exception {
		// TODO Auto-generated method stub
		if(modelAndView!=null)
		{
			modelAndView.addObject("user", hostHolder.getUser());
			if(hostHolder.getUser()!=null)
			{
				String key = RedisKeyUtil.getQusetionAddCacheKey(hostHolder.getUser().getId());
				String cache = redisDBForKeyService.get(key);
				if(cache!=null)
				{
					JSONObject json = JSON.parseObject(cache);
					modelAndView.addObject("cache_title", json.get("title"));
					modelAndView.addObject("cache_content", json.get("content"));
					redisDBForKeyService.del(key);
				}
			}
		}
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		

		if(request.getCookies()!=null)
		{
			String ticket = null;
			for(Cookie cookie:request.getCookies())
			{
				if(cookie.getName().equals("ticket"))
				{
					ticket=cookie.getValue();
					break;
				}
			}
			if(ticket!=null)
			{
				int userId = loginTicketService.validateTicket(ticket);
				if(userId!=-1)
				{
					hostHolder.setUser(userService.getUser(userId));
				}
				
				/*
				 * v2版本废弃
				 *
				SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
				LoginTicketDAO loginTicketDAO;
				LoginTicket loginTicket;
				User user = null;
				try{
					loginTicketDAO = session.getMapper(LoginTicketDAO.class);
					loginTicket = loginTicketDAO.selectByTicket(ticket);
					if(loginTicket!=null && loginTicket.getStatus()==0 
							&& loginTicket.getExpired().after(new Date())){
						UserDAO userDAO= session.getMapper(UserDAO.class);
						user = userDAO.selectUserById(loginTicket.getUserId());
						hostHolder.setUser(user);
					}
				}catch(Exception e)
				{
					logger.error("passport拦截器错误 "+e.getMessage());
				}finally
				{
					if(session!=null)
					{
						session.close();
					}
				}
				*/
			}
		}
		return true;
	}

}
