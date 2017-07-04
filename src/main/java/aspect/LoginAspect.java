package aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component  
@Aspect
public class LoginAspect {
	/*
	private static final Logger logger = LoggerFactory.getLogger(LoginAspect.class);
	
	
	@Before(value="execution(* controller.LoginController.*(..))")
	public void beforeMethod(){
		logger.info("Before Method");
	}
	@After(value="execution(* controller.LoginController.*(..))")
	public void afterMethod(){
		logger.info("After Method");
	}
	*/
}
