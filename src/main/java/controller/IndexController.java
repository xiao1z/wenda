package controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Question;
import service.QuestionService;
import service.UserService;

@Controller
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(path = {"/","/index"},method = RequestMethod.GET)
	public String index(Model model)
	{
		List<Question> questionList=questionService.getLatestQuestion(0, 0, 10);
		model.addAttribute("questionList",questionList);
		return "index";
	}
	
}
