package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.cj.core.util.StringUtils;

import model.Comment;
import model.HostHolder;
import model.Question;
import model.User;
import model.UserInfo;
import model.UserInfo.SEX;
import model.ViewObject;
import service.CommentService;
import service.ConfigService;
import service.FollowService;
import service.QuestionService;
import service.UserInfoService;
import service.UserService;
import util.IdResolver;
import util.JSONUtil;

@Controller
public class UserIndexController {
	
	
	//private static final Logger logger = LoggerFactory.getLogger(UserIndexController.class); 
	@Autowired
	QuestionService questionService;
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	FollowService followService;
	
	@Autowired
	UserInfoService userInfoService;
	
	@RequestMapping(path = {"/user/{userId}/headImg"},method = RequestMethod.POST)
	@ResponseBody
	public String alterUserHeadImg(Model model,@PathVariable("userId") String userIdStr,
							@RequestParam(value="headImg") MultipartFile headImg){
		
		int userId = IdResolver.resolveId(userIdStr);
		if(hostHolder.getUser()==null||hostHolder.getUser().getId()!=userId)
		{
			return JSONUtil.getJSONString(JSONUtil.UNLOGIN);
		}
		if(headImg!=null)
		{
			String headUrl = userService.updateHeadUrl(userId,headImg);
			if(headUrl!=null)
				return JSONUtil.getJSONString("headUrl", headUrl);
			else
				return JSONUtil.getJSONString("error", "上传头像错误");
		}
		return JSONUtil.getJSONString("error", "上传头像错误");
	}
	
	@RequestMapping(path = {"/user/{userId}/info"},method = RequestMethod.GET,produces="application/json; charset=utf-8")
	@ResponseBody
	public String getUserInfo(Model model,@PathVariable("userId") String userIdStr)
	{
		int userId = IdResolver.resolveId(userIdStr);
		return JSONUtil.getJSONStringOfUserInfo(userInfoService.getUserInfo(userId));
	}
	
	
	@RequestMapping(path = {"/user/{userId}/info"},method = RequestMethod.POST)
	@ResponseBody
	public String alterUserInfo(Model model,@PathVariable("userId") String userIdStr,
							@RequestParam(value="nickname",required=false) String nickname,
							@RequestParam(value="description",required=false) String description,
							@RequestParam(value="location",required=false) String location,
							@RequestParam(value="sex",required=false) String sex,
							@RequestParam(value="briefIntroduction",required=false) String briefIntroduction){
		int userId = IdResolver.resolveId(userIdStr);
		if(hostHolder.getUser()==null||hostHolder.getUser().getId()!=userId)
		{
			return JSONUtil.getJSONString(JSONUtil.UNLOGIN);
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		if(!StringUtils.isEmptyOrWhitespaceOnly(nickname))
		{
			/*
			 * 这个属性要修改user表
			 */
			//System.out.println(nickname);
		}
		userService.updateBriefIntroduction(userId, briefIntroduction);
		userInfo.setDescription(description);
		userInfo.setLocation(location);
		
		if(!StringUtils.isEmptyOrWhitespaceOnly(sex))
		{
			if(sex.equals("male"))
			{
				userInfo.setSex(SEX.MALE);
			}else if(sex.equals("female"))
			{
				userInfo.setSex(SEX.FEMALE);
			}else
			{
				userInfo.setSex(SEX.SECRECY);
			}
		}
		userInfoService.updateUserInfo(userInfo);
		return JSONUtil.getJSONString(JSONUtil.SUCCESS);
	}
	
	@RequestMapping(path = {"/user/{userId}/answers","/user/{userId}"},method = RequestMethod.GET)
	public String userIndex(Model model,@PathVariable("userId") String userIdStr)
	{
		int userId = IdResolver.resolveId(userIdStr);
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
}
