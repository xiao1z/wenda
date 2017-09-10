package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sdk.GeetestLib;
@Controller
public class VerifyController{

	@RequestMapping(path = {"/verify"},method = RequestMethod.GET)
	@ResponseBody
	public void Verify(
			HttpServletRequest request
			,HttpServletResponse response) throws ServletException, IOException {

		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), 
				GeetestConfig.isnewfailback());

		String resStr = "{}";
		
		String userid = "username";

		int gtServerStatus = gtSdk.preProcess(userid);

		request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);

		request.getSession().setAttribute("userid", userid);
		
		resStr = gtSdk.getResponseStr();

		PrintWriter out = response.getWriter();
		out.println(resStr);

	}
}