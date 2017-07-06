package controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import model.HostHolder;
import model.Question;
import service.QuestionService;
import util.DateUtil;
import util.JSONUtil;

@Controller
public class QuestionController {
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	HostHolder hostHolder;
	
	@RequestMapping(value = "/question/add" ,method = RequestMethod.POST)
	@ResponseBody
	public String addQuestion(@RequestParam("title") String title,@RequestParam("content") String content)
	{
		if(hostHolder.getUser()==null)
		{
			return JSONUtil.getJSONString(999);
		}else
		{
			Question question = new Question();
			question.setCommentCount(0);
			question.setContent(content);
			question.setTitle(title);
			question.setCreatedDate(DateUtil.getBeijinTime());
			question.setUserId(hostHolder.getUser().getId());
			int res = questionService.addQuestion(question);
			//失败
			if(res<0)
			{
				return JSONUtil.getJSONString(1, "添加失败");
			}else
			{
				return JSONUtil.getJSONString(0);
			}
		}
	}
}
