package daoTest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import configuration.WendaWebAppInitializer;
import dao.MybatisSqlSessionFactory;
import dao.UserDAO;
import model.User;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WendaWebAppInitializer.class)
public class UserDAOTest {
	
	
	
	
	@Test
	public void selectUserById()
	{
		
		SqlSessionFactory sqlSessionFactory = MybatisSqlSessionFactory.getSqlSessionFactory();
		SqlSession session=sqlSessionFactory.openSession();
		UserDAO mapper = session.getMapper(UserDAO.class);
		User user=mapper.selectUserById(1);
		//session.commit();
		System.out.println(user.getUsername());
		session.close();
		
	}
}
