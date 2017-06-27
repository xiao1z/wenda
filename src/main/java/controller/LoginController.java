package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String hello(Model model)
	{
		model.addAttribute("user", "hello user");
		return "login";
	}
	
	
}
