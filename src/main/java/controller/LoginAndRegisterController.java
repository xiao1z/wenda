package controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import service.UserService;
import util.URIUtil;



@Controller
public class LoginAndRegisterController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginAndRegisterController.class);

	
	@Autowired
	UserService userService;
	
	@RequestMapping(path = {"/reg/"}, method = RequestMethod.POST)
	public String register(Model model
			,@RequestParam("username") String username
			,@RequestParam("password") String password
			,@RequestParam(value = "rememberMe",defaultValue = "false") boolean rememberMe
			,@RequestParam(value = "next",required = false) String next
			,HttpServletResponse response){
		
		try
		{
			Map<String,String> map = userService.register(username, password);
			if(map.containsKey("ticket"))
			{
				Cookie cookie = new Cookie("ticket",map.get("ticket"));
				cookie.setPath("/");
				response.addCookie(cookie);
				if(next!=null)
					return "redirect:"+next;
				else
					return "redirect:/";
			}
			else if(map.containsKey("error"))
			{
				model.addAttribute("error", map.get("error"));
				return "login";
			}
			else 
				return "redirect:/";
		}catch(Exception e)
		{
			logger.error("注册错误 "+e.getMessage());
			return "login";
		}
	}
	
	@RequestMapping(path = {"/reglogin"}, method = RequestMethod.GET)
	public String reglogin(Model model,@RequestParam(value = "next",required = false) String next)
	{
		model.addAttribute("next",URIUtil.deleteURIContext(next));
		return "login";
	}
	
	@RequestMapping(path = {"/logout"}, method = RequestMethod.GET)
	public String logout(@CookieValue("ticket") String ticket)
	{
		userService.logout(ticket);
		return "redirect:/";
	}
	
	@RequestMapping(path = {"/login/"}, method = RequestMethod.POST)
	public String login(Model model
			,@RequestParam("username") String username
			,@RequestParam("password") String password
			,@RequestParam(value = "rememberMe",defaultValue = "false") boolean rememberMe
			,@RequestParam(value = "next",required = false) String next
			,HttpServletResponse response){
		
		try
		{
			Map<String,String> map = userService.login(username, password);
			if(map.containsKey("ticket"))
			{
				Cookie cookie = new Cookie("ticket",map.get("ticket"));
				cookie.setPath("/");
				response.addCookie(cookie);
				if(next!=null)
					return "redirect:"+next;
				else
					return "redirect:/";
				
			}
			else if(map.containsKey("error"))
			{
				model.addAttribute("error", map.get("error"));
				return "login";
			}
			return "redirect:/";
		}catch(Exception e)
		{
			logger.error("登录错误 "+e.getMessage());
			return "login";
		}
	}
	
}
