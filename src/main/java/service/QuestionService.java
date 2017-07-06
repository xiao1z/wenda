package service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dao.MybatisSqlSessionFactory;
import dao.QuestionDAO;
import model.Question;

@Service
public class QuestionService{
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
	
	public List<Question> getLatestQuestion(int userId,int offset,int limit){
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		List<Question> list = null;
		QuestionDAO questionDAO;
		try{
			questionDAO = session.getMapper(QuestionDAO.class);
			list= questionDAO.selectLatestQuestions(userId, offset, limit);
			session.commit();
		}catch(Exception e)
		{
			logger.error("获取最近问题错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return list;
	}

	/*
	 * 返回值：添加成功返回0,否则返回-1
	 */
	public int addQuestion(Question question)
	{
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		QuestionDAO questionDAO;
		try{
			questionDAO = session.getMapper(QuestionDAO.class);
			
			//敏感词过滤
			questionDAO.addQuestion(question);
			session.commit();
		}catch(Exception e)
		{
			logger.error("添加问题错误  "+e.getMessage());
			return -1;
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return 0;
	}
	
	
}
