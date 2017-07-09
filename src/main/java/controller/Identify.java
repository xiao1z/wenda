package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import model.HostHolder;
import util.JSONUtil;

@Controller
public class Identify {

	@Autowired
	HostHolder hostHolder;
	
	@RequestMapping(value = "/identify/login" ,method = RequestMethod.POST)
	@ResponseBody
	public String alreadyLogin()
	{
		if(hostHolder.getUser()==null)
		{
			return JSONUtil.getJSONString(999);
		}else
		{
			return JSONUtil.getJSONString(0);
		}
			
	}
}
