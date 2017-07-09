package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class SensitiveWordsService implements InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(SensitiveWordsService.class);
	
	public static void main(String args[])
	{
		SensitiveWordsService sensitiveWordsService = new SensitiveWordsService();
		sensitiveWordsService.loadSensitiveTable();
		System.out.println(sensitiveWordsService.filterWords("是tmdsas&11231s+色情+"));
	}

	private class TreeNode
	{
		Map<Character,TreeNode> subNodes = new HashMap<Character,TreeNode>();
		boolean ifend = false;
		
		void addSubNodes(Character key,TreeNode subNode)
		{
			subNodes.put(key, subNode);
		}
		TreeNode(boolean ifend)
		{
			this.ifend = ifend;
		}
		
	}
	
	private TreeNode root = new TreeNode(false);
	
	private void loadSensitiveTable()
	{
		InputStream is = this.getClass().getResourceAsStream("sensitiveWords.txt");
		assert(is!=null) :"inputStream is null";
		InputStreamReader ipr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(ipr);
		String lineTxt;
		try {
			while((lineTxt = reader.readLine())!=null)
			{
				TreeNode current = root;
				Character c;
				for(int i=0;i<lineTxt.length();i++)
				{
					c = lineTxt.charAt(i);
					if(this.isNotCommonlyUsedWord(c))
						continue;
					if(current.subNodes.containsKey(c))
					{
						current = current.subNodes.get(c);
					}else
					{
						TreeNode newNode = new TreeNode(false);
						current.addSubNodes(lineTxt.charAt(i),newNode);
						current = newNode;
					}
				}
				current.ifend = true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("加载敏感词表错误 "+e.getMessage());
		}
	}

	private boolean isNotCommonlyUsedWord(char c)
	{
		int ic = (int) c;
		return !CharUtils.isAsciiAlphanumeric(c)&&(ic<0x2E80||ic>0x9FFF);
	}
	
	public String filterWords(String origin)
	{
		if(origin==null||origin.length()==0)
			return origin;
		int startIndex = 0;
		int currentIndex = 0;
		TreeNode currentNode = root;
		StringBuilder sb = new StringBuilder();
		while(startIndex<origin.length())
		{
			boolean hit = false;
			currentIndex = startIndex;
			currentNode = root;
		
			while(currentIndex<origin.length())
			{
				if(this.isNotCommonlyUsedWord(origin.charAt(currentIndex)))
				{
					if(currentIndex == startIndex)
					{
						sb.append(origin.charAt(currentIndex));
						startIndex++;
						hit=true;
						break;
					}
					else{
						currentIndex++;
						continue;
					}
				}else
				{
					if(!currentNode.subNodes.containsKey(origin.charAt(currentIndex)))
						break;
				}
				currentNode=currentNode.subNodes.get(origin.charAt(currentIndex));
				if(currentNode.ifend)
				{
					hit=true;
					int count = currentIndex -startIndex +1;
					for(int i=0;i<count;i++)
					{
						sb.append("*");
					}
					startIndex = currentIndex+1;
					break;
				}else
				{
					currentIndex++;
				}
			}
			if(!hit)
			{
				sb.append(origin.charAt(startIndex));
				startIndex++;
			}
		}
		
		return sb.toString();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		this.loadSensitiveTable();
		
	}

}
