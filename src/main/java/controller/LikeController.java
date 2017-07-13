package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import model.EntityType;
import model.HostHolder;
import service.LikeService;
import service.RedisAdapter;
import util.JSONUtil;
import util.RedisKeyUtil;

@Controller
public class LikeController {
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	LikeService likeService;
	
	
	
	@RequestMapping(path = {"/like/comment/{commentId}"},method = RequestMethod.POST)
	@ResponseBody
	public String likeQuestionComment(@PathVariable("commentId") int commentId)
	{
		if(hostHolder.getUser()==null)
		{
			return JSONUtil.getJSONString(JSONUtil.UNLOGIN);
		}else
		{
			if(likeService.likeComment(hostHolder.getUser().getId(), commentId)>0)
			{
				return JSONUtil.getJSONString(JSONUtil.SUCCESS
						,String.valueOf(likeService.getCommentLikeCount(commentId)));
			}else
			{
				return JSONUtil.getJSONString(JSONUtil.FAIL);
			}
		}
	}
	
	@RequestMapping(path = {"/dislike/comment/{commentId}"},method = RequestMethod.POST)
	@ResponseBody
	public String dislikeQuestionComment(@PathVariable("commentId") int commentId)
	{
		if(hostHolder.getUser()==null)
		{
			return JSONUtil.getJSONString(JSONUtil.UNLOGIN);
		}else
		{
			if(likeService.dislikeComment(hostHolder.getUser().getId(), commentId)>0)
			{
				return JSONUtil.getJSONString(JSONUtil.SUCCESS
						,String.valueOf(likeService.getCommentLikeCount(commentId)));
			}else
			{
				return JSONUtil.getJSONString(JSONUtil.FAIL);
			}
		}
	}
}
