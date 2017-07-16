package daoTest;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import configuration.WendaWebAppInitializer;
import dao.MybatisSqlSessionFactory;
import dao.QuestionDAO;
import model.Question;
import util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WendaWebAppInitializer.class)
public class QuestionDAOTest {

	
	
	@Test
	public void addQuestionTest()
	{
		SqlSessionFactory sqlSessionFactory = MybatisSqlSessionFactory.getSqlSessionFactory();
		SqlSession session=sqlSessionFactory.openSession();
		QuestionDAO mapper = session.getMapper(QuestionDAO.class);
		Question question=new Question();
		question.setCommentCount(1);
		question.setContent("test content");
		question.setCreatedDate(DateUtil.now());
		question.setTitle("test title");
		question.setUserId(111);
		question.setId(8);
		int id = mapper.addQuestion(question);
		session.commit();
		//session.flushStatements();
		System.out.println(id);
		session.close();
	}
	
	
	
	//@Test
	public void selectLatestQuestionsTest()
	{
		SqlSessionFactory sqlSessionFactory = MybatisSqlSessionFactory.getSqlSessionFactory();
		SqlSession session=sqlSessionFactory.openSession();
		QuestionDAO mapper = session.getMapper(QuestionDAO.class);
		List<Question> list=mapper.selectLatestQuestions(0, 0, 2);
		for(Question q:list)
		{
			System.out.println(q.getCreatedDate());
		}
	}
}
