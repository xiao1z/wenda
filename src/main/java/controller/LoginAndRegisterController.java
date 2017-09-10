package controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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

import service.ConfigService;
import service.UserService;
import util.URIUtil;

import sdk.GeetestLib;

@Controller
public class LoginAndRegisterController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginAndRegisterController.class);

	
	@Autowired
	UserService userService;
	
	@Autowired
	ConfigService configService;

	@RequestMapping(path = {"/reg/"}, method = RequestMethod.POST)
	public String register(Model model
			,@RequestParam("username") String username
			,@RequestParam("password") String password
			,@RequestParam(value = "rememberMe",defaultValue = "false") boolean rememberMe
			,@RequestParam(value = "next",required = false) String next
			,@RequestParam(value = "briefIntroduction",required = false) String briefIntroduction
			,HttpServletResponse response,HttpServletRequest request){
		
		try
		{
			GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());

			String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
			String validate = request.getParameter(GeetestLib.fn_geetest_validate);
			String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
		
			int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
			String userid = (String)request.getSession().getAttribute("userid");
			int gtResult = 0;

			if (gt_server_status_code == 1) {
				gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);	
				System.out.println(gtResult);
			} else {
				System.out.println("failback:use your own server captcha validate");
				gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
				System.out.println(gtResult);
			}

			if (gtResult == 1) {//验证成功
				Map<String,String> map = userService.register(username, password,briefIntroduction,rememberMe);
				if(map.containsKey("ticket"))
				{
					Cookie cookie = new Cookie("ticket", map.get("ticket"));
					cookie.setPath("/");
					if(rememberMe)
					{
						cookie.setMaxAge(configService.getLoginTicket_EXPIRED_TIME());
					}
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
			}
			else {
				logger.error("注册验证错误 ");
				return "login";
			}
		}
		catch(Exception e)
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
			,HttpServletResponse response,HttpServletRequest request){
		
		try
		{
			GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());

			String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
			String validate = request.getParameter(GeetestLib.fn_geetest_validate);
			String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
		
			int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
			String userid = (String)request.getSession().getAttribute("userid");
			int gtResult = 0;

			if (gt_server_status_code == 1) {
				gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);	
				System.out.println(gtResult);
			} else {
				System.out.println("failback:use your own server captcha validate");
				gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
				System.out.println(gtResult);
			}

			if (gtResult == 1) {//验证成功
				Map<String,String> map = userService.login(username, password,rememberMe);
				if(map.containsKey("ticket"))
				{
					Cookie cookie = new Cookie("ticket",map.get("ticket"));
					cookie.setPath("/");
					if(rememberMe)
					{
						cookie.setMaxAge(configService.getLoginTicket_EXPIRED_TIME());
					}
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
				}
				else {
				logger.error("登录验证错误 ");
				return "login";
			}
		}
		catch(Exception e)
		{
			logger.error("登录错误 "+e.getMessage());
			return "login";
		}
	}
	
}
