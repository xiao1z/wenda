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

import model.HostHolder;
import model.Message;
import model.User;
import model.ViewObject;
import service.MessageService;
import service.UserService;
import util.DateUtil;
import util.JSONUtil;

@Controller
public class MessageController {

	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/user/{userId}/messages/{conversationId}" ,method = RequestMethod.GET)
	public String getUserMessageDetail(Model model,@PathVariable("userId") int userId
			,@PathVariable("conversationId") String conversationId){
		
		if(hostHolder.getUser()==null||hostHolder.getUser().getId()!=userId)
			return "redirect:/reglogin?next="+"/user/"+userId+"/messages/"+conversationId;
		
		List<Message> messageList = messageService.getMessageByConversationId(conversationId, 0, 10);
		if(messageList!=null&&!messageList.isEmpty())
		{
			List<ViewObject> voList = new ArrayList<ViewObject>();
			for(Message message:messageList)
			{
				ViewObject vo = new ViewObject();
				int interlocutorId = message.getFromId() == userId ? message.getToId():message.getFromId();
				User interlocutor = userService.getUser(interlocutorId);
				if(interlocutorId==message.getFromId())
				{
					vo.set("action", "来自");
				}else
				{
					vo.set("action", "发往");
					vo.set("showMyHeadImg","我的头像" );
				}
				vo.set("message", message);
				vo.set("interlocutor", interlocutor);
				voList.add(vo);
			}
			model.addAttribute("voList",voList);
		}
		return "messageDetail";
	}
	
	
	@RequestMapping(value = "/user/{userId}/messages" ,method = RequestMethod.GET)
	public String getUserMessages(Model model,@PathVariable("userId") int userId)
	{
		if(hostHolder.getUser()==null||hostHolder.getUser().getId()!=userId)
			return "redirect:/reglogin?next="+"/user/"+userId+"/messages";
		
		List<Message> messageList = messageService.getLastReplyMessagesByUserId(userId, 0, 10);
		if(messageList!=null&&!messageList.isEmpty())
		{
			List<ViewObject> voList = new ArrayList<ViewObject>();
			for(Message message:messageList)
			{
				ViewObject vo = new ViewObject();
				int interlocutorId = message.getFromId() == userId ? message.getToId():message.getFromId();
				User interlocutor = userService.getUser(interlocutorId);
				if(interlocutorId==message.getFromId())
				{
					vo.set("action", "来自");
				}else
				{
					vo.set("action", "发往");
				}
				vo.set("message", message);
				vo.set("interlocutor", interlocutor);
				voList.add(vo);
			}
			model.addAttribute("voList",voList);
		}
		return "messages";
	}
	
	@RequestMapping(value = "/addMessage/user/{id}" ,method = RequestMethod.POST)
	@ResponseBody
	public String addMessage(@PathVariable("id") int id,@RequestParam("messageContent") String content)
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
			Message message = new Message();
			message.setContent(content);
			message.setCreateDate(DateUtil.getBeijinTime());
			message.setFromId(hostHolder.getUser().getId());
			message.setToId(id);
			message.setStatus(Message.NOT_READ);
			int res = messageService.addMessage(message);
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
