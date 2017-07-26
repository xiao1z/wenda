package daoTest;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import configuration.WendaWebAppInitializer;
import dao.CommentDAO;
import dao.MybatisSqlSessionFactory;
import model.Comment;
import util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WendaWebAppInitializer.class)
public class CommentDAOTest {
	
	@Test
	public void addComment()
	{
		Comment comment = new Comment();
		comment.setContent("test content");
		comment.setCreateDate(new Date());
		comment.setEntityId(0);
		comment.setEntityType(1);
		comment.setStatus(0);
		comment.setUserId(1);
		SqlSessionFactory sqlSessionFactory = MybatisSqlSessionFactory.getSqlSessionFactory();
		SqlSession session=sqlSessionFactory.openSession();
		CommentDAO commentDAO = session.getMapper(CommentDAO.class);
		commentDAO.addComment(comment);
		System.out.println(comment.getId());
		session.commit();
	}
	
	@Test
	public void getComment()
	{
		SqlSessionFactory sqlSessionFactory = MybatisSqlSessionFactory.getSqlSessionFactory();
		SqlSession session=sqlSessionFactory.openSession();
		CommentDAO commentDAO = session.getMapper(CommentDAO.class);
	}

}
