package aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoginAspect {
	private static final Logger logger = LoggerFactory.getLogger(LoginAspect.class);
	
	@Before("execution(** controller.*.*(..))")
	public void beforeMethod(){
		logger.info("Before Method");
	}
	@After("execution(** controller.*.*(..))")
	public void afterMethod(){
		logger.info("After Method");
	}
}
