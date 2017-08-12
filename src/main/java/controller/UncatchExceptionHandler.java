package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@ControllerAdvice
@Controller
public class UncatchExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(UncatchExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public String commonHandler(Exception e){
		logger.error("exception :"+e.getMessage());
		return "error";
	}
	
	@RequestMapping(value = "/error" ,method = RequestMethod.GET)
	public String error()
	{
		return "error";
	}
}
