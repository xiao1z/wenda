package controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.HostHolder;
import model.Question;
import model.User;
import model.ViewObject;
import service.QuestionService;
import service.UserService;

@Controller
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	private static final int QUESTION_COUNT_EVERY_PAGE = 5;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	UserService userService;
	

	
	@RequestMapping(path = {"/user/{userId}"},method = RequestMethod.GET)
	public String userIndex(Model model,@PathVariable("userId") int userId)
	{
		model.addAttribute("voList", getQuestions(userId,0,QUESTION_COUNT_EVERY_PAGE));
		return "index";
	}
	
	@RequestMapping(path = {"/","/index"},method = RequestMethod.GET)
	public String index(Model model)
	{
		model.addAttribute("voList", getQuestions(0,0,QUESTION_COUNT_EVERY_PAGE));
		return "index";
	}
	
	private List<ViewObject> getQuestions(int userId,int offset,int limit)
	{
		List<Question> questionList=questionService.getLatestQuestion(userId, 0, QUESTION_COUNT_EVERY_PAGE);
		List<ViewObject> voList=new ArrayList<ViewObject>();
		for(Question question: questionList)
		{
			ViewObject vo=new ViewObject();
			vo.set("question", question);
			User user = userService.getUser(question.getUserId());
			vo.set("user",user);
			assert(vo.get("user")!=null);
			voList.add(vo);
		}
		return voList;
	}
	
	@RequestMapping(path = {"/test/header/{id}"},method = RequestMethod.GET)
	public String testHeader(@PathVariable("id") int id)
	{
		return "headerTest"+id;
	}
	
	@RequestMapping(path = {"/test/body/{id}"},method = RequestMethod.GET)
	public String testBody(@PathVariable("id") int id)
	{
		return "bodyTest"+id;
	}
}
