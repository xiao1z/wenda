package util;

public class IdResolver {
	public static int resolveId(String id)
	{
		String[] ids = id.split(",");
		StringBuilder newId = new StringBuilder();
		for(int i=0;i<ids.length;i++){
			newId.append(ids[i]);
		}
		return Integer.parseInt(newId.toString());
	}
	
}
