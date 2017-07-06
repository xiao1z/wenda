package util;

public class URIUtil {
	
	//删除uri的应用上下文(本程序为/wenda)
	public static String deleteURIContext(String URI)
	{
		if(URI==null||URI.length()==0)
			return URI;
		int loc = -1;
		for(int i=1;i<URI.length();i++)
		{
			if(URI.charAt(i)=='/')
			{
				loc = i;
				break;
			}
		}
		
		if(loc==-1)
			return URI;
		else
			return URI.substring(loc);
	}
}
