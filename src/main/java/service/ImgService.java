package service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dao.ImgDAO;
import dao.MybatisSqlSessionFactory;
import model.EntityType;
import model.Img;

@Service
public class ImgService {
	private static final Logger logger = LoggerFactory.getLogger(ImgService.class);
	
	public int addImg(Img img)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		ImgDAO imgDAO;
		int result = -1;
		try{
			imgDAO = session.getMapper(ImgDAO.class);
			result= imgDAO.addImg(img);
			session.commit();
		}catch(Exception e)
		{
			logger.error("添加图片错误 "+e.getMessage());
		}finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return result;
	}
	
	public List<Img> getImgOfQuestionComment(int commentId)
	{
		SqlSession session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		ImgDAO imgDAO;
		List<Img> result = null;
		try{
			imgDAO = session.getMapper(ImgDAO.class);
			result= imgDAO.selectImgsByEntity(commentId, EntityType.COMMENT);
		}catch(Exception e)
		{
			logger.error("获取评论图片错误 "+e.getMessage());
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
