package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import model.Comment;
import model.HostHolder;
import model.Question;
import model.User;
import model.ViewObject;
import service.CommentService;
import service.FollowService;
import service.QuestionService;
import service.UserService;
import util.JSONUtil;

@Controller
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	private static final int QUESTION_COUNT_EVERY_PAGE = 5;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	FollowService followService;

	@RequestMapping(path = {"/user/{userId}/questions"},method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getUserQuestions(@PathVariable("userId") int userId)
	{
		List<Question> questionList = questionService.getLatestQuestion(userId, 0, 10);
		return JSONUtil.getJSONStringOfQuestions(questionList,Arrays.asList("content"));
	}
	
	@RequestMapping(path = {"/user/{userId}/answers","/user/{userId}"},method = RequestMethod.GET)
	public String userIndex(Model model,@PathVariable("userId") int userId)
	{
		if(hostHolder.getUser()==null||hostHolder.getUser().getId()!=userId)
		{
			model.addAttribute("canSendMessage","true");
			model.addAttribute("discribe","他的");
			if(hostHolder.getUser()!=null)
			{
				String followTableId = followService.isFollowUser(hostHolder.getUser().getId(), userId);
				if(followTableId!=null)
				{
					model.addAttribute("followTableId",followTableId);
				}
			}
		}
		else
		{
			model.addAttribute("discribe","我的");
		}
		User owner = userService.getUser(userId);
		model.addAttribute("owner", owner);
		List<Comment> commentList = commentService.getQuestionCommentsOfUserId(userId);
		if(commentList!=null&&!commentList.isEmpty())
		{
			List<ViewObject> voList = new ArrayList<ViewObject>();
			for(Comment comment:commentList)
			{
				ViewObject vo = new ViewObject();

				Question question = questionService.getQuestion(comment.getEntityId());
		
				vo.set("question", question);
				vo.set("comment", comment);
				voList.add(vo);
			}
			model.addAttribute("voList", voList);
		}
		return "userIndex";
	}
	
	@RequestMapping(path = {"/","/index"},method = RequestMethod.GET)
	public String index(Model model)
	{
		List<ViewObject> list = getQuestions(0,0,QUESTION_COUNT_EVERY_PAGE);
		if(list!=null)
		{
			model.addAttribute("voList", list);
		}
		return "index";
	}
	
	private List<ViewObject> getQuestions(int userId,int offset,int limit)
	{
		List<Question> questionList=questionService.getLatestQuestion(userId, 0, QUESTION_COUNT_EVERY_PAGE);
		List<ViewObject> voList=new ArrayList<ViewObject>();
		if(questionList!=null)
		{
			for(Question question: questionList)
			{
				ViewObject vo=new ViewObject();
				vo.set("question", question);
				User user = userService.getUser(question.getUserId());
				vo.set("user",user);
				assert(vo.get("user")!=null);
				voList.add(vo);
			}
		}
		return voList;
	}
	
	@RequestMapping(path = {"/test/header/{id}"},method = RequestMethod.GET)
	public String testHeader(@PathVariable("id") int id,HttpServletResponse response)
	{
		response.setContentType("text/html;charset=ISO8859-1");
		return "headerTest"+id;
	}
	
	@RequestMapping(path = {"/test/body/{id}"},method = RequestMethod.GET)
	public String testBody(@PathVariable("id") int id,HttpServletResponse response)
	{
		response.setContentType("text/html;charset=UTF-8");
		return "headerTest"+id;
	}
}
