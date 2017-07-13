package daoTest;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import configuration.WendaWebAppInitializer;
import dao.MessageDAO;
import dao.MybatisSqlSessionFactory;
import model.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WendaWebAppInitializer.class)
public class MessageDAOTest {
	
	@Test
	public void  testSelectLastReply()
	{
		SqlSession  session = MybatisSqlSessionFactory.getSqlSessionFactory().openSession();
		MessageDAO messageDAO = session.getMapper(MessageDAO.class);
		List<Message> list = messageDAO.selectLastReplyMessagesByUserId(5, 0, 10);
		for(Message message:list)
		{
			System.out.println(message.getConversationId()+" "+message.getContent());
		}
 	}
}
