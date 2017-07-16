package util;

import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	
	/** 
	 * 获取更改时区后的日期 
	 * @param date 日期 
	 * @param oldZone 旧时区对象 
	 * @param newZone 新时区对象 
	 * @return 日期 
	 */  
	public static Date changeTimeZone(Date date, TimeZone oldZone, TimeZone newZone) {  
	    Date dateTmp = null;  
	    if (date != null) {  
	        int timeOffset = oldZone.getRawOffset() - newZone.getRawOffset();  
	        dateTmp = new Date(date.getTime() - timeOffset);  
	    }  
	    return dateTmp;  
	}
	
	public static Date now()
	{
		return new Date();
	}
}
