package service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CommentDAO;
import dao.MessageDAO;
import dao.MybatisSqlSessionFactory;
import model.Message;

@Service
public class MessageService {
	
	@Autowired
	SensitiveWordsService sensitiveWordsService;
	
	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	
	public int addMessage(Message message)
	{
		message.setContent(sensitiveWordsService.filterWords(message.getContent()));
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		MessageDAO messageDAO;
		int result = -1;
		try{
			messageDAO = session.getMapper(MessageDAO.class);
			result = messageDAO.addMessage(message);
			session.commit();
		}catch(Exception e)
		{
			logger.error("添加消息错误 "+e.getMessage());
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
	 * 注：该函数取得的Message的ID表示的是该会话（conversation）的总消息数
	 */
	public List<Message> getLastReplyMessagesByUserId(int userId,int offset,int limit)
	{
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		MessageDAO messageDAO;
		List<Message> list = null;
		try{
			messageDAO = session.getMapper(MessageDAO.class);
			list = messageDAO.selectLastReplyMessagesByUserId(userId, offset, limit);
			session.commit();
		}catch(Exception e)
		{
			logger.error("获取最新消息错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return list;
	}
	
	public List<Message> getMessageByConversationId(String conversationId,int offset,int limit)
	{
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		MessageDAO messageDAO;
		List<Message> list = null;
		try{
			messageDAO = session.getMapper(MessageDAO.class);
			list = messageDAO.selectMessageByConversationId(conversationId, offset, limit);
			session.commit();
		}catch(Exception e)
		{
			logger.error("获取最新消息错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return list;
	}
}
