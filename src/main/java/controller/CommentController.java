package controller;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import async.EventProducer;
import async.EventType;
import async.StandardEvent;
import model.Comment;
import model.EntityType;
import model.HostHolder;
import service.CommentService;
import service.QuestionService;
import util.DateUtil;
import util.JSONUtil;

@Controller
public class CommentController {
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	EventProducer eventProducer;
	
	@Autowired
	QuestionService questionService;
	
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
			comment.setCreateDate(DateUtil.now());
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
				int questionOwnerId = questionService.getQuestion(id).getUserId();
				if(questionOwnerId==hostHolder.getUser().getId());
				else
				{
					SimpleDateFormat s = new SimpleDateFormat();
					eventProducer.fireEvent(new StandardEvent()
							.setActorId(hostHolder.getUser().getId())
							.setEntityId(id)
							.setEntityOwnerId(questionOwnerId)
							.setEntityType(EntityType.QUESTION)
							.setType(EventType.RAISE_COMMENT)
							.setInfomation("commentId", String.valueOf(res))
							.setInfomation("createDate", s.format(DateUtil.now())));
				}
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
			comment.setCreateDate(DateUtil.now());
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
				int commentOwnerId = commentService.getCommentById(id).getUserId();
				if(commentOwnerId==hostHolder.getUser().getId());
				else
				{
					eventProducer.fireEvent(new StandardEvent()
							.setActorId(hostHolder.getUser().getId())
							.setEntityId(id)
							.setEntityOwnerId(commentOwnerId)
							.setEntityType(EntityType.COMMENT)
							.setType(EventType.RAISE_COMMENT));
				}
				return JSONUtil.getJSONString(JSONUtil.SUCCESS);
			}
		}
	}
}
