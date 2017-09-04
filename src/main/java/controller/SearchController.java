package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import model.Question;
import model.User;
import model.ViewObject;
import service.ConfigService;
import service.QuestionService;
import service.SearchService;
import service.UserService;



@Controller
public class SearchController 
{
	
	//private static final Logger logger = LoggerFactory.getLogger(SearchController.class); 
	
	@Autowired
	SearchService searchService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	ConfigService configService;

	
	@RequestMapping(path = "/search" ,method = RequestMethod.GET)
	public String search(Model model, @RequestParam("keyword") String keyword,
										@RequestParam(value = "offset", defaultValue = "0") int offset,
										@RequestParam(value = "count", defaultValue = "10") int count)
	{
		
		try
		{
			List<Question> questionList = searchService.searchQuestion(keyword, offset, count, "<span style=\"color:red\">", "</span>");			
			List<ViewObject> voList=new ArrayList<ViewObject>();
			if(questionList!=null)
			{
				for(Question question: questionList)
				{
					Question q = questionService.getQuestion(question.getId());
					ViewObject vo=new ViewObject();
					if(question.getContent() != null)
					{
						q.setContent(question.getContent());
					}
					if(question.getTitle() != null)
					{
						q.setTitle(question.getTitle());
					}
					vo.set("question", q);
					User user = userService.getUser(q.getUserId());
					vo.set("user",user);
					voList.add(vo);
				}
			}	
			model.addAttribute("voList", voList);
			model.addAttribute("keyword", keyword);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "search_result";
	}

}