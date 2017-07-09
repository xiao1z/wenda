package serviceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import configuration.WendaWebAppInitializer;
import service.SensitiveWordsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WendaWebAppInitializer.class)
public class SensitiveWordsTest {
	
	
	
	@Test
	public void test()
	{
		SensitiveWordsService sensitiveWordsService = new SensitiveWordsService();
		sensitiveWordsService.filterWords("你是色情");
	}
}
