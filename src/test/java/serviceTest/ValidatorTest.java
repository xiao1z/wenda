package serviceTest;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import configuration.WendaWebAppInitializer;
import model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WendaWebAppInitializer.class)
public class ValidatorTest {
	@Test
	public void test()
	{
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator v = vf.getValidator();
		User user = new User();
		Set<ConstraintViolation<User>> constraintViolations = v.validate(user);
		for(ConstraintViolation<User> c:constraintViolations)
		{
			System.out.println(c.getMessage()+" "+c.getMessageTemplate());
		}
	}
}
