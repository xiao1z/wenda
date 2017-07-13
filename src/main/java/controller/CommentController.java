package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import model.Comment;
import model.EntityType;
import model.HostHolder;
import service.CommentService;
import util.DateUtil;
import util.JSONUtil;

@Controller
public class CommentController {
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	CommentService commentService;
	
	@RequestMapping(value = "/comment/question/{id}" ,method = RequestMethod.POST)
	@ResponseBody
	public String addQuestionComment(@PathVariable("id") int id,@RequestParam("content") String content)
	{
		if(hostHolder.getUser()==null)
		{
			return JSONUtil.getJSONString(JSONUtil.UNLOGIN);
		}else if(StringUtils.isEmpty(content))
		{
			return JSONUtil.getJSONString(JSONUtil.EMPTY_CONTENT,"内容为空");
		}
		else
		{
			Comment comment = new Comment();
			comment.setContent(content);
			comment.setCreateDate(DateUtil.getBeijinTime());
			comment.setEntityId(id);
			comment.setEntityType(EntityType.QUESTION);
			comment.setStatus(Comment.NORMAL_STATUS);
			comment.setUserId(hostHolder.getUser().getId());
			
			int res = commentService.addComment(comment);
			//失败
			if(res<0)
			{
				return JSONUtil.getJSONString(JSONUtil.FAIL, "添加失败");
			}else
			{
				return JSONUtil.getJSONString(JSONUtil.SUCCESS);
			}
		}
	}
	
	@RequestMapping(value = "/comment/comment/{id}" ,method = RequestMethod.POST)
	@ResponseBody
	public String addCommentComment(@PathVariable("id") int id,@RequestParam("content") String content)
	{
		if(hostHolder.getUser()==null)
		{
			return JSONUtil.getJSONString(JSONUtil.UNLOGIN);
		}else if(StringUtils.isEmpty(content))
		{
			return JSONUtil.getJSONString(JSONUtil.EMPTY_CONTENT,"内容为空");
		}
		else
		{
			Comment comment = new Comment();
			comment.setContent(content);
			comment.setCreateDate(DateUtil.getBeijinTime());
			comment.setEntityId(id);
			comment.setEntityType(EntityType.COMMENT);
			comment.setStatus(Comment.NORMAL_STATUS);
			comment.setUserId(hostHolder.getUser().getId());
			
			int res = commentService.addComment(comment);
			//失败
			if(res<0)
			{
				return JSONUtil.getJSONString(JSONUtil.FAIL, "添加失败");
			}else
			{
				return JSONUtil.getJSONString(JSONUtil.SUCCESS);
			}
		}
	}
}
