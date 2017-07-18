package util;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import async.Event;
import model.Question;
import model.User;

public class JSONUtil {
	public static final int UNLOGIN = 999;
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	public static final int EMPTY_CONTENT = 2;
	public static final int DUPLICATE_INSERT = 3;
	
	private static Set<String> questionFields = new HashSet<String>();
	private static Set<String> userFields = new HashSet<String>();
	
	static
	{
		Field[] questionfields = Question.class.getDeclaredFields();
		for(int i=0;i<questionfields.length;i++)
		{
			questionFields.add(questionfields[i].getName());
		}
		
		Field[] userfields = User.class.getDeclaredFields();
		for(int i=0;i<userfields.length;i++)
		{
			userFields.add(userfields[i].getName());
		}
	}
	
	public static String getJSONStringOfEvent(Event e)
	{
		return JSON.toJSONString(e);
	}
	
	
	public static String getJSONString(int code,String msg)
	{
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		return json.toJSONString();
	}
	
	public static String getQuestionCacheJSONString(String title,String content)
	{
		JSONObject json = new JSONObject();
		json.put("title", title);
		json.put("content", content);
		return json.toJSONString();
	}
	
	public static String getJSONString(int code)
	{
		JSONObject json = new JSONObject();
		json.put("code", code);
		return json.toJSONString();
	}
	
	public static String getJSONStringOfQuestions(List<Question> questions,List<String> omitFields)
	{
		if(questions==null||questions.isEmpty())
		{
			//表示问题为空
			return JSONUtil.getJSONString(EMPTY_CONTENT);
		}
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		if(omitFields!=null)
		{
			for(String omitField:omitFields)
			{
				if(questionFields.contains(omitField))
				{
					filter.getExcludes().add(omitField);
				}
			}
		}
		JSONArray jarry =new JSONArray();
		JSONObject json = new JSONObject();
		//JSONArray.defaultTimeZone = TimeZone.getTimeZone("GMT");
		json.put("code",0);
		json.put("count", questions.size());
		jarry.addAll(questions);
		json.put("questionArray", jarry);
		return JSON.toJSONString(json,filter);
		
	}
	
	public static String getJSONStringOfUsers(List<User> users,List<String> omitFields)
	{
		if(users==null||users.isEmpty())
		{
			//表示用户为空
			return JSONUtil.getJSONString(EMPTY_CONTENT);
		}
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		if(omitFields!=null)
		{
			for(String omitField:omitFields)
			{
				if(userFields.contains(omitField))
				{
					filter.getExcludes().add(omitField);
				}
			}
		}
		JSONArray jarry =new JSONArray();
		JSONObject json = new JSONObject();
		//JSONArray.defaultTimeZone = TimeZone.getTimeZone("GMT");
		json.put("code",0);
		json.put("count", users.size());
		jarry.addAll(users);
		json.put("userArray", jarry);
		return JSON.toJSONString(json,filter);
		
	}
	
	

}
