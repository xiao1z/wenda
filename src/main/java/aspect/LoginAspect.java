package aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component  
@Aspect
public class LoginAspect {
	private static final Logger logger = LoggerFactory.getLogger(LoginAspect.class);
	
	@Before(value="execution(* controller.*.*(..))")
	public void beforeMethod(){
		logger.info("Before Method");
	}
	@After(value="execution(* controller.*.*(..))")
	public void afterMethod(){
		logger.info("After Method");
	}
}
