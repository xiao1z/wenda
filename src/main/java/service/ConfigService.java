package service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
	
	@Value("${index.QUESTION_COUNT_EVERY_PAGE}")
	private int index_QUESTION_COUNT_EVERY_PAGE;

	@Value("${Feed.MAX_CACHE_SZIE}")
	private int Feed_MAX_CACHE_SZIE;
	
	@Value("${Feed.MAX_CACHE_SECONDS}")
	private int Feed_MAX_CACHE_SECONDS;
	
	@Value("${LoginTicket.EXPIRED_TIME}")
	private int LoginTicket_EXPIRED_TIME;
	
	@Value("${LoginTicket.EXPIRED_TIME_IF_NOTREMEBERME}")
	private int LoginTicket_EXPIRED_TIME_IF_NOTREMEBERME;
	
	@Value("${User.DEFAULT_HEAD_URL}")
	private String User_DEFAULT_HEAD_URL;
	
	@Value("${Redis.KEY_SERVICE_POOL_LOC}")
	private String Redis_KEY_SERVICE_POOL_LOC;
	
	@Value("${Redis.NORMAL_SERVICE_POOL_LOC}")
	private String Redis_NORMAL_SERVICE_POOL_LOC;
	
	@Value("${cache.USER_CACHE_SECONDS}")
	private int cache_USER_CACHE_SECONDS;
	
	@Value("${cache.MAX_USER_CACHE_SIZE}")
	private int cache_MAX_USER_CACHE_SIZE;
	
	@Value("${User.ROOT_HEAD_URL}")
	private String User_ROOT_HEAD_URL;
	
	public int getIndex_QUESTION_COUNT_EVERY_PAGE() {
		return index_QUESTION_COUNT_EVERY_PAGE;
	}

	public int getFeed_MAX_CACHE_SZIE() {
		return Feed_MAX_CACHE_SZIE;
	}

	public int getFeed_MAX_CACHE_SECONDS() {
		return Feed_MAX_CACHE_SECONDS;
	}

	public int getLoginTicket_EXPIRED_TIME() {
		return LoginTicket_EXPIRED_TIME;
	}

	public int getLoginTicket_EXPIRED_TIME_IF_NOTREMEBERME() {
		return LoginTicket_EXPIRED_TIME_IF_NOTREMEBERME;
	}

	public String getUser_DEFAULT_HEAD_URL() {
		return User_DEFAULT_HEAD_URL;
	}

	public String getRedis_KEY_SERVICE_POOL_LOC() {
		return Redis_KEY_SERVICE_POOL_LOC;
	}

	public String getRedis_NORMAL_SERVICE_POOL_LOC() {
		return Redis_NORMAL_SERVICE_POOL_LOC;
	}

	public int getCache_USER_CACHE_SECONDS() {
		return cache_USER_CACHE_SECONDS;
	}

	public int getCache_MAX_USER_CACHE_SIZE() {
		return cache_MAX_USER_CACHE_SIZE;
	}

	public String getUser_ROOT_HEAD_URL() {
		return User_ROOT_HEAD_URL;
	}

	
}
