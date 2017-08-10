package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import model.HostHolder;
import model.Question;
import model.User;
import model.ViewObject;
import service.CommentService;
import service.ConfigService;
import service.FollowService;
import service.QuestionService;
import service.UserService;

@Controller
public class IndexController {
	
	//private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	
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

	@Autowired
	ConfigService configService;

	
	
	@RequestMapping(path = {"/","/index"},method = RequestMethod.GET)
	public String index(Model model,@RequestParam(value="page",defaultValue="1") int page)
	{
		List<ViewObject> list = getQuestions(0,(page-1)*configService.getIndex_QUESTION_COUNT_EVERY_PAGE(),configService.getIndex_QUESTION_COUNT_EVERY_PAGE());
		List<ViewObject> next = getQuestions(0,(page)*configService.getIndex_QUESTION_COUNT_EVERY_PAGE(),1);
		if(list.size()!=0)
		{
			model.addAttribute("voList", list);	
		}
		if(next.size()==0)
		{
			model.addAttribute("hasMore", "-1");
		}
		else
		{
			model.addAttribute("hasMore", "0");
		}
		model.addAttribute("page", page);
		
		return "index";
	}
	
	private List<ViewObject> getQuestions(int userId,int offset,int limit)
	{
		List<Question> questionList=questionService.getLatestQuestion(userId, offset, limit);
		List<ViewObject> voList=new ArrayList<ViewObject>();
		if(questionList!=null)
		{
			for(Question question: questionList)
			{
				ViewObject vo=new ViewObject();
				vo.set("question", question);
				User user = userService.getUser(question.getUserId());
				vo.set("user",user);
				voList.add(vo);
			}
		}
		return voList;
	}
	
}
