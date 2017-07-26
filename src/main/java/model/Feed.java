package model;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

import exception.IllegalTyepException;

public class Feed {	
	private int id;
	private int type = 0; //0表示未设置type
	private int actorId;
	private int actorType = -1;//-1表示未设置actorType
	private Date createDate;	
	//一个JSON串
	private String data;
	
	//辅助的json对象，不会存到数据库
	private JSONObject json = null;
	
	//最大缓存数量
	public static final int MAX_CACHE_SZIE = 10;
	public static final int MAX_CACHE_SECONDS = 60*60*24*7;
	
	//这些Type只能由人发出
	
	public static final int LIKE_TYPE = 1;
	public static final int FOLLOW_TYPE = 2;
	public static final int RAISE_COMMENT_TYPE = 3;  //发表评论
	public static final int RAISE_QUESTION_TYPE = 4; //发布提问
	
	
	//这些Type只能由问题发出
	public static final int NEW_ANSWER_TYPE = -1; //问题有新的回答
	
	public int getId() {
		return id;
	}
	public Feed setId(int id) {
		this.id = id;
		return this;
	}
	public int getType() {
		return type;
	}
	public Feed setType(int type){
		this.type = type;
		
		if(this.actorType == EntityType.USER && this.type <0 
				||this.actorType == EntityType.QUESTION && this.type>0){
			throw new IllegalTyepException("feed type和actorType不匹配");
		}
		return this;
	}
	public int getActorId() {
		return actorId;
	}
	public Feed setActorId(int actorId) {
		this.actorId = actorId;
		return this;
	}
	public int getActorType() {
		return actorType;
	}
	public Feed setActorType(int actorType){
		this.actorType = actorType;
		
		if(this.actorType!=EntityType.USER && this.actorType!= EntityType.QUESTION)
		{
			throw new IllegalTyepException("feed actorType非法");
		}
		
		if(this.actorType == EntityType.USER && this.type <0 
				||this.actorType == EntityType.QUESTION && this.type>0){
			throw new IllegalTyepException("feed type和actorType不匹配");
		}
		return this;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Feed setCreateDate(Date createDate) {
		this.createDate = createDate;
		return this;
	}
	public String getData() {
		if(json!=null)
			return json.toJSONString();
		
		
		//保证存入数据库的是JSON串
		return null;
	}
	
	/*
	 * 不要使用这个方法，如果要设置额外数据，使用set方法
	 */
	public Feed setData(String data) {
		this.data = data;
		return this;
	}
	
	public void set(String key,Object value)
	{
		if(json == null)
		{
			if(this.data==null)
			{
				json = new JSONObject();
			}else
			{
				json = JSONObject.parseObject(data);
			}
		}
		
		json.put(key, value);
	}
	
	public Object get(String key)
	{
		if(data == null)
			return null;
		if(json == null)
		{
			json = JSONObject.parseObject(data);
		}
		return json.get(key);
	}
	
}
