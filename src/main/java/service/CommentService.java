package service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CommentDAO;
import dao.MybatisSqlSessionFactory;
import model.Comment;

@Service
public class CommentService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
	
	@Autowired
	SensitiveWordsService sensitiveWordsService;
	
	public int addComment(Comment comment)
	{
		comment.setContent(sensitiveWordsService.filterWords(comment.getContent()));
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		CommentDAO commentDAO;
		int result = -1;
		try{
			commentDAO = session.getMapper(CommentDAO.class);
			result = commentDAO.addComment(comment);
			session.commit();
		}catch(Exception e)
		{
			logger.error("添加评论错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	public List<Comment> getCommentsOfQusetion(int questionId)
	{
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		CommentDAO commentDAO;
		List<Comment> commentList = null;
		try{
			commentDAO = session.getMapper(CommentDAO.class);
			commentList = commentDAO.getCommentsByEntity(questionId,Comment.QUESTION_COMMENT_TYPE);
		}catch(Exception e)
		{
			logger.error("根据问题获取评论错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return commentList;
	}

	
	public List<Comment> getCommentsOfComment(int CommentId)
	{
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		CommentDAO commentDAO;
		List<Comment> commentList = null;
		try{
			commentDAO = session.getMapper(CommentDAO.class);
			commentList = commentDAO.getCommentsByEntity(CommentId,Comment.COMMENT_COMMENT_TYPE);
		}catch(Exception e)
		{
			logger.error("根据评论获取评论错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return commentList;
	}
}