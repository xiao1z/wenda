package daoTest;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import configuration.WendaWebAppInitializer;
import dao.FollowDAO;
import dao.MybatisSqlSessionFactory;
import model.Follow;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WendaWebAppInitializer.class)
public class FollowDAOTest {
	
	@Test
	public void test()
	{
		SqlSession  session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		FollowDAO followDAO = session.getMapper(FollowDAO.class);
		Follow follow = new Follow();
		follow.setCreateDate(new Date());
		follow.setEntityId(0);
		follow.setEntityType(0);
		follow.setFollowId(0);
		follow.setFollowType(0);
		followDAO.addFollowAndGetId(follow);
		System.out.println(follow.getId());
		//session.commit();
		
	}
}
