package daoTest;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
		User user=mapper.selectUserById(5);
		
		session.commit();
		System.out.println(user.getUsername());
		session.close();
		
		
	}
}
