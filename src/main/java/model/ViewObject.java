package model;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {
	private Map<String,Object> objh=new HashMap<String,Object>();
	
	public void set(String key,Object value){
		objh.put(key, value);
	}
	
	public Object get(String key){
		return objh.get(key);
	}
}
