package controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import model.EntityType;
import model.Follow;
import model.HostHolder;
import model.Question;
import model.User;
import service.FollowService;
import util.JSONUtil;

@Controller
public class FollowController {
	
	@Autowired
	FollowService followService;
	
	@Autowired
	HostHolder hostHolder;
	
	@RequestMapping(value = "/follow/questions/{userId}" ,method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getFollowQuestion(@PathVariable("userId") int userId)
	{
		List<Question> questionList = followService.getFollowQuestionByUserId(userId);
		return JSONUtil.getJSONStringOfQuestions(questionList,Arrays.asList("content"));
	}
	
	@RequestMapping(value = "/follow/users/{userId}" ,method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getFollowUsers(@PathVariable("userId") int userId)
	{
		List<User> userList = followService.getFollowUserByUserId(userId);
		return JSONUtil.getJSONStringOfUsers(userList, null);
	}
	
	@RequestMapping(value = "/follow/followers/{userId}" ,method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getFollowers(@PathVariable("userId") int userId)
	{
		List<User> userList = followService.getFollowerOfUserByUserId(userId);
		return JSONUtil.getJSONStringOfUsers(userList, null);
		
	}
	
	@RequestMapping(value = "/follow/cancel/{followId}" ,method = RequestMethod.POST)
	@ResponseBody
	public String cancelFollow(@PathVariable("followId") int followId)
	{
		if(hostHolder.getUser()==null)
		{
			return JSONUtil.getJSONString(JSONUtil.UNLOGIN);
		}else
		{
			followService.cancelFollow(followId);
			return JSONUtil.getJSONString(JSONUtil.SUCCESS);
		}

	}
	
	
	
	@RequestMapping(value = "/follow/question/{questionId}" ,method = RequestMethod.POST)
	@ResponseBody
	public String followQuestion(@PathVariable("questionId") int questionId)
	{
		if(hostHolder.getUser()==null)
		{
			return JSONUtil.getJSONString(JSONUtil.UNLOGIN);
		}else
		{
			Follow follow = new Follow();
			follow.setCreateDate(new Date());
			follow.setEntityId(hostHolder.getUser().getId());
			follow.setEntityType(EntityType.USER);
			follow.setFollowId(questionId);
			follow.setFollowType(EntityType.QUESTION);
			//防止重复关注
			if(followService.isFollowQuestion(hostHolder.getUser().getId(), questionId)!=null)
				return JSONUtil.getJSONString(JSONUtil.DUPLICATE_INSERT);
			else 
			{
				int followTableId = followService.addFollowAndGetId(follow);
				return JSONUtil.getJSONString(JSONUtil.SUCCESS,String.valueOf(followTableId));
			}
		}

	}
	
	
	@RequestMapping(value = "/follow/user/{userId}" ,method = RequestMethod.POST)
	@ResponseBody
	public String followUser(@PathVariable("userId") int userId)
	{
		if(hostHolder.getUser()==null)
		{
			return JSONUtil.getJSONString(JSONUtil.UNLOGIN);
		}else
		{
			Follow follow = new Follow();
			follow.setCreateDate(new Date());
			follow.setEntityId(hostHolder.getUser().getId());
			follow.setEntityType(EntityType.USER);
			follow.setFollowId(userId);
			follow.setFollowType(EntityType.USER);
			//防止重复关注
			if(followService.isFollowQuestion(hostHolder.getUser().getId(), userId)!=null)
				return JSONUtil.getJSONString(JSONUtil.DUPLICATE_INSERT);
			else 
			{
				int followTableId = followService.addFollowAndGetId(follow);
				return JSONUtil.getJSONString(JSONUtil.SUCCESS,String.valueOf(followTableId));
			}
		}

	}
}
