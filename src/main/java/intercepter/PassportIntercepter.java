package intercepter;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import dao.LoginTicketDAO;
import dao.MybatisSqlSessionFactory;
import dao.UserDAO;
import model.HostHolder;
import model.LoginTicket;
import model.User;
import service.RedisAdapter;
import util.RedisKeyUtil;


@Component
public class PassportIntercepter implements HandlerInterceptor{

	
	private static final Logger logger = LoggerFactory.getLogger(PassportIntercepter.class);

	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	RedisAdapter redisAdapter;
	
	
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
				String cache = redisAdapter.get(key);
				if(cache!=null)
				{
					JSONObject json = JSON.parseObject(cache);
					modelAndView.addObject("cache_title", json.get("title"));
					modelAndView.addObject("cache_content", json.get("content"));
					redisAdapter.del(key);
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
			}
		}
		return true;
	}

}
