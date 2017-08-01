package service;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import dao.MybatisSqlSessionFactory;
import dao.UserInfoDAO;
import model.UserInfo;

@Service
public class UserInfoService {
	
	@Autowired
	SensitiveWordsService sensitiveWordsService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
	
	public int addUserInfo(UserInfo userInfo)
	{
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		if(userInfo.getDescription()!=null)
			userInfo.setDescription(sensitiveWordsService.filterWords(
					HtmlUtils.htmlEscape(userInfo.getDescription())));
		
		if(userInfo.getLocation()!=null)
			userInfo.setDescription(sensitiveWordsService.filterWords(
					HtmlUtils.htmlEscape(userInfo.getLocation())));
		
		UserInfoDAO userInfoDAO;
		int result = -1;
		try{
			userInfoDAO = session.getMapper(UserInfoDAO.class);
			result = userInfoDAO.addUserInfo(userInfo);
			session.commit();
		}catch(Exception e)
		{
			logger.error("添加用户信息错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	
	public int updateUserInfo(UserInfo userInfo)
	{
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		/*
		if(userInfo.getDescription()!=null)
			userInfo.setDescription(sensitiveWordsService.filterWords(
					HtmlUtils.htmlEscape(userInfo.getDescription())));
		
		if(userInfo.getLocation()!=null)
			userInfo.setDescription(sensitiveWordsService.filterWords(
					HtmlUtils.htmlEscape(userInfo.getLocation())));
		*/
		UserInfoDAO userInfoDAO;
		int result = -1;
		try{
			userInfoDAO = session.getMapper(UserInfoDAO.class);
			result = userInfoDAO.updateUserInfo(userInfo);
			session.commit();
		}catch(Exception e)
		{
			logger.error("修改用户信息错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	public UserInfo getUserInfo(int userId)
	{
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		UserInfoDAO userInfoDAO;
		UserInfo result = null;
		try{
			userInfoDAO = session.getMapper(UserInfoDAO.class);
			result = userInfoDAO.selectUserInfoByUserId(userId);
		}catch(Exception e)
		{
			logger.error("获取用户信息错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
}
