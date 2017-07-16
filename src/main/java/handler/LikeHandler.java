package handler;

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
import model.Comment;
import model.EntityType;
import model.Message;
import model.User;
import service.CommentService;
import service.MessageService;
import service.QuestionService;
import util.DateUtil;

@Component
public class LikeHandler implements EventHandler{

	private static final Logger logger = LoggerFactory.getLogger(LikeHandler.class);

	@Autowired
	CommentService commentService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	MessageService messageService;
	
	@Override
	public void doHandle(Event event) {
		// TODO Auto-generated method stub
		StandardEvent sevent = (StandardEvent) event;
		Message message = new Message();
		message.setCreateDate(DateUtil.now());
		message.setFromId(User.SYSTEM_USER_ID);
		message.setStatus(Message.NOT_READ_STATUS);
		message.setToId(sevent.getEntityOwnerId());
		if(sevent.getEntityType()==EntityType.COMMENT)
		{
			Comment comment = commentService.getCommentById(sevent.getEntityId());
			
			message.setContent("您的回答: <a href=\"/wenda/question/"
				+comment.getEntityId()+"\">"+"<strong>"
				+comment.getContent()+" </strong></a> 有了新的赞!");
		}else
		{
			logger.error("无法处理的实体类型（entityType）");
			return;
		}
		messageService.addMessage(message,false);
	}

	@Override
	public List<EventType> getInterestedEventType() {
		// TODO Auto-generated method stub
		return Arrays.asList(EventType.LIKE);
	}

}
