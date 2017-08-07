package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import dao.CommentDAO;
import dao.MybatisSqlSessionFactory;
import model.Comment;
import model.EntityType;
import model.Img;

@Service
public class CommentService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
	
	@Autowired
	private SensitiveWordsService sensitiveWordsService;
	
	@Autowired
	private ConfigService configService;

	@Autowired
	private ImgService imgService;
	
	
	public int addComment(Comment comment)
	{
		comment.setContent(sensitiveWordsService.filterWords(comment.getContent()));
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		CommentDAO commentDAO;
		int result = -1;
		try{
			commentDAO = session.getMapper(CommentDAO.class);
			commentDAO.addComment(comment);
			session.commit();
			result = comment.getId();
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
	
	
	public int getCommentsCountOfQuestion(int questionId)
	{
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		CommentDAO commentDAO;
		int result = 0;
		try{
			commentDAO = session.getMapper(CommentDAO.class);
			result = commentDAO.getCommentsCountByEntity(questionId, EntityType.QUESTION);
		}catch(Exception e)
		{
			logger.error("添加评论错误 "+e.getMessage());
			return 0;
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	public String uploadQuestionCommetImg(int questionId,MultipartFile commentImg,int commentId,int offset)
	{
		File questionDir = new File(configService.getQuestion_ROOT_IMG_URL()+questionId);
		if(!questionDir.exists())
			questionDir.mkdirs();
		try {
			byte  [] bytes = commentImg.getBytes();
			StringBuilder imgUrl = new StringBuilder(questionDir.getPath());
			imgUrl.append("/").append(commentId).append("-").append(offset).append("-").append(commentImg.getOriginalFilename());
			FileOutputStream os = new FileOutputStream(imgUrl.toString());
			os.write(bytes);
			os.flush();
			os.close();

			StringBuilder netUrl = new StringBuilder("/wenda/img/")
					.append(questionId).append("/").append(commentId).append("-").append(offset).append("-").append(commentImg.getOriginalFilename());
			Img img = new Img();
			img.setEntityId(commentId);
			img.setEntityType(EntityType.COMMENT);
			img.setOffset(offset);
			img.setUrl(netUrl.toString());
			imgService.addImg(img);
			return netUrl.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("添加评论图片错误 "+e.getMessage());
			return null;
		}
	}
	
	public List<Comment> getCommentsOfQusetion(int questionId)
	{
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		CommentDAO commentDAO;
		List<Comment> commentList = null;
		try{
			commentDAO = session.getMapper(CommentDAO.class);
			commentList = commentDAO.getCommentsByEntity(questionId,EntityType.QUESTION );
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
			commentList = commentDAO.getCommentsByEntity(CommentId,EntityType.COMMENT );
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
	
	public List<Comment> getQuestionCommentsOfUserId(int userId)
	{
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		CommentDAO commentDAO;
		List<Comment> commentList = null;
		try{
			commentDAO = session.getMapper(CommentDAO.class);
			commentList = commentDAO.getCommentsByUserIdAndEntityType(userId,EntityType.QUESTION);
		}catch(Exception e)
		{
			logger.error("根据用户名获取评论错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}	
		return commentList;
	}
	
	public Comment getCommentById(int commentId)
	{
		SqlSession session=MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		CommentDAO commentDAO;
		Comment comment = null;
		try{
			commentDAO = session.getMapper(CommentDAO.class);
			comment = commentDAO.getCommentById(commentId);
		}catch(Exception e)
		{
			logger.error("根据id获取评论错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return comment;
	}
}
