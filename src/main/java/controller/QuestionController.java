package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import model.Comment;
import model.HostHolder;
import model.Question;
import model.User;
import model.ViewObject;
import service.CommentService;
import service.FollowService;
import service.LikeService;
import service.QuestionService;
import service.RedisDBForKeyService;
import service.UserService;
import util.DateUtil;
import util.JSONUtil;
import util.RedisKeyUtil;

@Controller
public class QuestionController {
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	LikeService likeService;
	
	@Autowired
	FollowService followService;
	
	@Autowired
	RedisDBForKeyService redisDBForKeyService;
	
	@RequestMapping(value = "/question/add" ,method = RequestMethod.POST)
	@ResponseBody
	public String addQuestion(@RequestParam("title") String title,@RequestParam("content") String content)
	{
		if(hostHolder.getUser()==null)
		{
			return JSONUtil.getJSONString(JSONUtil.UNLOGIN);
		}else
		{
			if(StringUtils.isEmpty(title))
			{
				return JSONUtil.getJSONString(JSONUtil.EMPTY_CONTENT, "标题不能为空");
			}
			Question question = new Question();
			question.setCommentCount(0);
			question.setContent(content);
			question.setTitle(title);
			question.setCreatedDate(DateUtil.now());
			question.setUserId(hostHolder.getUser().getId());
			int res = questionService.addQuestion(question);
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
	
	@RequestMapping(value = "/question/addCache/{userId}" ,method = RequestMethod.POST)
	@ResponseBody
	public String addQuestionCache(@RequestParam("title") String title
			,@RequestParam("content") String content
			,@PathVariable("userId") int userId){
		String key = RedisKeyUtil.getQusetionAddCacheKey(userId);
		redisDBForKeyService.set(key, JSONUtil.getQuestionCacheJSONString(title, content));
		return JSONUtil.getJSONString(JSONUtil.SUCCESS);
	}
	
	
	@RequestMapping(value = "/question/{id}" ,method = RequestMethod.GET)
	public String getQuestionDetails(Model model,@PathVariable("id") int id)
	{
		Question question = questionService.getQuestion(id);
		if(hostHolder.getUser()!=null)
		{
			String followTableId = null;
			if((followTableId=followService.isFollowQuestion(hostHolder.getUser().getId(),id))!=null)
			{
				model.addAttribute("followTableId",followTableId);
			}
		}
		User asker = userService.getUser(question.getUserId());
		List<Comment> commentList = commentService.getCommentsOfQusetion(question.getId());
		//System.out.println(commentList.size()+"　"+question.getId());
		if(commentList!=null && !commentList.isEmpty())
		{
			List<ViewObject> voList=new ArrayList<ViewObject>();
			for(Comment comment:commentList)
			{
				ViewObject vo = new ViewObject();
				User user = userService.getUser(comment.getUserId());
				comment.setCreateDate(comment.getCreateDate());
				if(hostHolder.getUser()!=null)
				{
					if(likeService.isLikeComment(hostHolder.getUser().getId(), comment.getId()))
					{
						vo.set("isLike", "yes");
					}else if(likeService.isDislikeComment(hostHolder.getUser().getId(), comment.getId()))
					{
						vo.set("isLike", "no");
					}else
					{
						vo.set("isLike", "neither");
					}
				}else
				{
					vo.set("isLike", "neither");
				}
				vo.set("likeCount", likeService.getCommentLikeCount(comment.getId()));
				vo.set("user", user);
				vo.set("comment", comment);
				List<Comment> subComments = commentService.getCommentsOfComment(comment.getId());
				if(subComments!=null && !subComments.isEmpty())
				{
					
					List<ViewObject> subVoList=new ArrayList<ViewObject>();
					for(Comment subComment:subComments)
					{
						subComment.setCreateDate(subComment.getCreateDate());
						ViewObject subVo = new ViewObject();
						User subUser = userService.getUser(subComment.getUserId());
						subVo.set("subUser", subUser);
						subVo.set("subComment", subComment);
						subVoList.add(subVo);
					}
					vo.set("subVoList", subVoList);
				}
				
				voList.add(vo);
			}
			model.addAttribute("voList", voList);
		}
		model.addAttribute("question", question);
		model.addAttribute("asker", asker);
		return "questionDetail";
	}
}
