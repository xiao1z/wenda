package daoTest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import dao.MybatisSqlSessionFactory;
import dao.UserDAO;
import model.User;



//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = WendaWebAppInitializer.class)
public class UserDAOTest {
	
	
	
	
	//@Test
	public void selectUserById()
	{
		
		SqlSessionFactory sqlSessionFactory = MybatisSqlSessionFactory.getSqlSessionFactory();
		SqlSession session=sqlSessionFactory.openSession();
		UserDAO mapper = session.getMapper(UserDAO.class);
		User user=mapper.selectUserById(5);
		User newuser=new User();
		newuser.setHeadUrl("/");
		newuser.setPassword("zzz");
		newuser.setSalt("zzz");
		newuser.setUsername("zzz");
		mapper.addUser(newuser);
		session.commit();
		System.out.println(user.getUsername());
		session.close();
		
		
	}
}
