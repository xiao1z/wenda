package service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import dao.MybatisSqlSessionFactory;
import dao.UserDAO;
import model.User;
import model.UserInfo;
import net.coobird.thumbnailator.Thumbnails;
import util.DateUtil;
import util.MD5Util;

@Service
public class UserService implements InitializingBean{
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	LoginTicketService loginTicketService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	SensitiveWordsService sensitiveWordsService;
	
	@Autowired
	UserInfoService userInfoService;
	
	private static Validator validator;
	
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	
	
	public Map<String,String> login(String username,String password,boolean rememberMe)
	{
		Map<String,String> map = new HashMap<String,String>();
		
		if(StringUtils.isEmpty(username)){
			username=null;
		}
		if(StringUtils.isEmpty(password)){
			password=null;
		}
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		if(!constraintViolations.isEmpty())
		{
			for(ConstraintViolation<User> c:constraintViolations)
			{
				map.put("error", c.getMessage());
			}
			return map;
		}
		
		user = this.getUser(username);
		if(user==null)
		{
			map.put("error", "用户名或密码错误");
			return map;
		}
		
		if(!MD5Util.getMD5(password+user.getSalt()).equals(user.getPassword())){
			map.put("error", "用户名或密码错误");
			return map;
		}
		
		String ticket = loginTicketService.addTicket(user.getId(),rememberMe);
		map.put("ticket", ticket);
		return map;
		
	}
	
	public void logout(String ticket)
	{
		loginTicketService.discardTicket(ticket);
	}
	
	
	public Map<String,String> register(String username,String password,
			String briefIntroduction,boolean rememberMe)
	{
		Map<String,String> map = new HashMap<String,String>();
		User user = new User();
		if(StringUtils.isEmpty(username))
			username=null;
		if(StringUtils.isEmpty(password))
			password=null;
		user.setUsername(username);
		user.setPassword(password);
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		if(!constraintViolations.isEmpty())
		{
			for(ConstraintViolation<User> c:constraintViolations)
			{
				map.put("error", c.getMessage());
			}
			return map;
		}
		
		if(this.getUser(username)!=null)
		{
			map.put("error", "用户名已经被注册");
			return map;
		}
		username = HtmlUtils.htmlEscape(username);
		
		user.setSalt(UUID.randomUUID().toString().substring(0,5));
		user.setHeadUrl(configService.getUser_DEFAULT_HEAD_URL());
		user.setPassword(MD5Util.getMD5(password+user.getSalt()));
		user.setBriefIntroduction(briefIntroduction);
		user.setNickname(username);
	
		if(!this.addUser(user))
		{
			map.put("error", "用户注册错误");
			return map;
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(user.getId());
		userInfo.setRegisterDate(DateUtil.now());
		userInfoService.addUserInfo(userInfo);
		
		String ticket = loginTicketService.addTicket(user.getId(),rememberMe);
		map.put("ticket", ticket);
		return map;
	}
	
	
	private boolean addUser(User user){
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		UserDAO userDAO;
		try{
			userDAO = session.getMapper(UserDAO.class);
			userDAO.addUser(user);
			session.commit();
		}catch(Exception e)
		{
			logger.error("添加用户错误 "+e.getMessage());
			return false;
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		cacheService.addUserToCache(user);
		return true;
	}
	
	public String updateHeadUrl(int userId,MultipartFile headImg)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		UserDAO userDAO;
		try{
			StringBuilder realHeadUrlPath = new StringBuilder(configService.getUser_ROOT_HEAD_URL());
			File userPath = new File(realHeadUrlPath.append(userId).toString());
			if(!userPath.exists())
				userPath.mkdirs();
			
			
			realHeadUrlPath.append("/");
			realHeadUrlPath.append(HtmlUtils.htmlEscape(headImg.getOriginalFilename()));
			Thumbnails.of(headImg.getInputStream()).size(600, 800).toFile(realHeadUrlPath.toString());
			
			/*未压缩图片
			byte[] bytes = headImg.getBytes();
			FileOutputStream fileOutputStream = new FileOutputStream(realHeadUrlPath.toString());
			fileOutputStream.write(bytes);
			fileOutputStream.flush();
			fileOutputStream.close();
			*/
			userDAO = session.getMapper(UserDAO.class);
			
			StringBuilder headUrl = new StringBuilder("/wenda/headImg/")
					.append(userId).append("/").append(headImg.getOriginalFilename());
			userDAO.updateHeadUrl(userId, headUrl.toString());
			session.commit();
			
			cacheService.failCache(userId);
			
			//外部访问路径
			return headUrl.toString();
		}catch(Exception e)
		{
			logger.error("更新用户头像错误 "+e.getMessage());
			return null;
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
	}
	
	public int updateBriefIntroduction(int userId,String briefIntroduction){
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		if(briefIntroduction!=null)
		{
			briefIntroduction = sensitiveWordsService.filterWords(
					HtmlUtils.htmlEscape(briefIntroduction));
		}
		UserDAO userDAO;
		int result = -1;
		try{
			userDAO = session.getMapper(UserDAO.class);
			result = userDAO.updateBriefIntroduction(userId, briefIntroduction);
			session.commit();
		}catch(Exception e)
		{
			logger.error("更新用户简介错误  "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		cacheService.failCache(userId);
		return result;
	}
	
	
	private User getUser(String username){
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		UserDAO userDAO;
		User user = null;
		try{
			userDAO = session.getMapper(UserDAO.class);
			user = userDAO.selectUserByUsername(username);
			session.commit();
		}catch(Exception e)
		{
			logger.error("根据username查询用户错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return user;
		
	}
	
	public User getUser(int id){	
		User user = null;
		if((user = cacheService.getCachedUser(id))!=null)
			return user;
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		UserDAO userDAO;
		try{
			userDAO = session.getMapper(UserDAO.class);
			user = userDAO.selectUserById(id);
			session.commit();
		}catch(Exception e)
		{
			logger.error("根据Id查询用户错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		if(user!=null)
		{
			cacheService.addUserToCache(user);
		}
		return user;
	}
	
	/*
	 * 判断用户是否为活跃用户
	 */
	public boolean isActiveUser(int userId)
	{
		return true;
	}
	
	/*
	 * 判断用户是否为活跃用户
	 */
	public boolean isActiveUser(User user)
	{
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
	}
}
