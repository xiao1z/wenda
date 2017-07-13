package util;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import model.Question;

public class JSONUtil {
	public static final int UNLOGIN = 999;
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	public static final int EMPTY_CONTENT = 2;
	
	
	public static String getJSONString(int code,String msg)
	{
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		return json.toJSONString();
	}
	
	public static String getJSONString(int code)
	{
		JSONObject json = new JSONObject();
		json.put("code", code);
		return json.toJSONString();
	}
	
	public static String getJSONStringOfQuestions(List<Question> questions)
	{
		if(questions==null||questions.isEmpty())
		{
			//表示问题为空
			return JSONUtil.getJSONString(3);
		}
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		filter.getExcludes().add("content");
		JSONArray jarry =new JSONArray();
		JSONObject json = new JSONObject();
		JSONArray.defaultTimeZone = TimeZone.getTimeZone("GMT");
		json.put("code",0);
		json.put("count", questions.size());
		jarry.addAll(questions);
		json.put("questionArray", jarry);
		return JSON.toJSONString(json,filter);
		
	}

	public static void main(String args[])
	{
		List<Question> list = new ArrayList<Question>();
		for(int i=0;i<3;i++)
		{
			Question question = new Question();
			question.setContent("你好");
			question.setId(i);
			question.setCreatedDate(DateUtil.getBeijinTime());
			question.setCommentCount(0);
			question.setTitle("你好");
		//	list.add(question);
		}
		System.out.println(JSONUtil.getJSONStringOfQuestions(list));
	}
}
