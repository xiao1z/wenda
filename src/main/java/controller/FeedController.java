package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Feed;
import model.HostHolder;
import service.CommentService;
import service.FeedService;
import util.IdResolver;

@Controller
public class FeedController {

	
	@Autowired
	FeedService feedService;
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	CommentService commentService;
	
	
	@RequestMapping(value = {"/timeline/{userId}"} ,method = RequestMethod.GET)
	public String getTimeline(Model model,
			@PathVariable(value = "userId") String userIdStr){
		int userId = IdResolver.resolveId(userIdStr);
		if(hostHolder.getUser()!=null&&hostHolder.getUser().getId()!=userId)
		{
			return "redirect:/";
		}
		
		List<Feed> feeds = feedService.getFeedsOfUser(userId, Integer.MAX_VALUE, 10);
		for(Feed feed:feeds)
		{
			if(feed.getType()==Feed.RAISE_COMMENT_TYPE)
			{
				feed.set("comment",commentService.getCommentById(
						Integer.parseInt((String) feed.get("commentId"))));
			}
		}
		if(feeds.size()>0)
			model.addAttribute("feeds",feeds);
		return "timeline";
	}
}
