package service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.FollowDAO;
import dao.MybatisSqlSessionFactory;
import model.EntityType;
import model.Follow;
import model.Question;
import model.User;

@Service
public class FollowService {
	
	private static final Logger logger = LoggerFactory.getLogger(FollowService.class);

	@Autowired
	UserService userService;
	
	@Autowired
	QuestionService questionService;
	
	//获得粉丝列表
	public List<User> getFollowerOfUserByUserId(int userId)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FollowDAO followDAO;
		List<User> result = new ArrayList<User>();
		try{
			followDAO = session.getMapper(FollowDAO.class);
			List<Follow> follows = followDAO.selectFollowerOfEntity(userId, EntityType.USER);
			for(Follow follow:follows)
			{
				result.add(userService.getUser(follow.getEntityId()));
			}
		}catch(Exception e)
		{
			logger.error("获得粉丝列表错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	//获得关注的人的列表
	public List<User> getFollowUserByUserId(int userId)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FollowDAO followDAO;
		List<User> result = new ArrayList<User>();
		try{
			followDAO = session.getMapper(FollowDAO.class);
			List<Follow> follows = followDAO.selectFollowEntityOfEntity(userId, EntityType.USER,EntityType.USER);
			for(Follow follow:follows)
			{
				result.add(userService.getUser(follow.getFollowId()));
			}
		}catch(Exception e)
		{
			logger.error("获得关注的人的列表错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	//获得关注的问题的列表
	public List<Question> getFollowQuestionByUserId(int userId)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FollowDAO followDAO;
		List<Question> result = new ArrayList<Question>();
		try{
			followDAO = session.getMapper(FollowDAO.class);
			List<Follow> follows = followDAO.selectFollowEntityOfEntity(userId, EntityType.USER,EntityType.QUESTION);
			for(Follow follow:follows)
			{
				result.add(questionService.getQuestion(follow.getFollowId()));
			}
		}catch(Exception e)
		{
			logger.error("获得关注的问题的列表错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	public int addFollowAndGetId(Follow follow)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FollowDAO followDAO;
		int result = -1;
		try{
			followDAO = session.getMapper(FollowDAO.class);
			followDAO.addFollowAndGetId(follow);
			session.commit();
			result = follow.getId();//获得主键值
		}catch(Exception e)
		{
			logger.error("添加关注错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	/*
	 * 如果user关注了question，返回follow表中该行Id
	 * 否则返回null
	 */
	public String isFollowQuestion(int userId,int questionId)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FollowDAO followDAO;
		try{
			followDAO = session.getMapper(FollowDAO.class);
			return followDAO.selectIdOfEntityAndfollow(userId, EntityType.USER, questionId, EntityType.QUESTION);
			
		}catch(Exception e)
		{
			logger.error("判断是否关注错误  "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return null;
	}
	
	public String isFollowUser(int followerId,int userId)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FollowDAO followDAO;
		try{
			followDAO = session.getMapper(FollowDAO.class);
			return followDAO.selectIdOfEntityAndfollow(followerId, EntityType.USER, userId, EntityType.USER);
			
		}catch(Exception e)
		{
			logger.error("判断是否关注错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return null;
		
	}
	
	public int cancelFollow(int id)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FollowDAO followDAO;
		int result = -1;
		try{
			followDAO = session.getMapper(FollowDAO.class);
			result = followDAO.cancelFollow(id);
			session.commit();
		}catch(Exception e)
		{
			logger.error("取消关注错误 "+e.getMessage());
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
