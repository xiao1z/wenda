package util;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil {
	
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
}
