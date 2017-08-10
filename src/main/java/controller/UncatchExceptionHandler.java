package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@ControllerAdvice
@Controller
public class UncatchExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public String commonHandler(Exception e){
		return "error";
	}
	
	@RequestMapping(value = "/error" ,method = RequestMethod.GET)
	public String error()
	{
		return "error";
	}
}
