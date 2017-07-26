package handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import async.Event;
import async.EventHandler;
import async.EventType;
import async.StandardEvent;
import model.EntityType;
import model.Feed;
import model.Question;
import model.User;
import service.FeedService;
import service.QuestionService;
import service.RedisAdapter;
import service.UserService;
import util.DateUtil;
import util.RedisKeyUtil;

@Component
public class FeedHandler implements EventHandler{

	private static final Logger logger = LoggerFactory.getLogger(FeedHandler.class);

	@Autowired
	UserService userService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	FeedService feedService;
	
	@Autowired
	RedisAdapter redisAdapter;
	
	@Override
	public void doHandle(Event event) {
		StandardEvent sevent = (StandardEvent) event;
		switch(sevent.getType())
		{
		case RAISE_QUESTION:{
			/* 未完成
			SimpleDateFormat s = new SimpleDateFormat();
			Feed feed = new Feed();
			feed.setActorId(sevent.getActorId());
			feed.setActorType(EntityType.USER);
			feed.setType(Feed.RAISE_QUESTION_TYPE);
			feed.set("discribe", "发表了新的问题");
			try {
				feed.setCreateDate(s.parse(sevent.getInformation("createDate")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				feed.setCreateDate(DateUtil.now());
			}
			*/
			break;
		}
		case RAISE_COMMENT:{
			//说明是一个问题的回答
			if(sevent.getInformation("commentId")!=null)
			{
				SimpleDateFormat s = new SimpleDateFormat();
				Feed feed = new Feed();
				feed.setActorId(sevent.getActorId());
				feed.setActorType(EntityType.USER);
				feed.setType(Feed.RAISE_COMMENT_TYPE);
				User actor = userService.getUser(sevent.getActorId());
				Question question = questionService.getQuestion(sevent.getEntityId());
				feed.set("actorName", actor.getUsername());
				feed.set("actorHeadUrl", actor.getHeadUrl());
				feed.set("questionTitle", question.getTitle());
				feed.set("commentId", sevent.getInformation("commentId"));
				try {
					if(sevent.getInformation("createDate")==null)
						feed.setCreateDate(DateUtil.now());
					else feed.setCreateDate(s.parse(sevent.getInformation("createDate")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					logger.error("回复事件没有设置createDate信息 "+e.getMessage());
					feed.setCreateDate(DateUtil.now());
				}finally{
					feedService.addFeed(feed);
				}
			}
			break;
		}
		case CANCEL_FOLLOW:{
			handleCancelFollow(sevent);
			break;
		}
		default:
			break;
		}
		
		
	}
	
	private void handleCancelFollow(StandardEvent sevent)
	{
		//当用户取消关注、取消收藏某个问题时，简单的删除redis中该用户的feedFlow缓存
		redisAdapter.del(RedisKeyUtil.getFeedFlowKey(sevent.getActorId()));
	}
	
	@Override
	public List<EventType> getInterestedEventType() {
		// TODO Auto-generated method stub
		return Arrays.asList(EventType.LIKE,EventType.RAISE_COMMENT,
				EventType.FOLLOW,EventType.NEW_ANSWER,EventType.RAISE_QUESTION,
				EventType.CANCEL_FOLLOW);
	}

}
