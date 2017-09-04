package service;


import org.springframework.stereotype.Service;

import model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServer;
//import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
//import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;

//import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
//import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;

@Service

public class SearchService
{
	
	private static final String SOLR_URL = "http://127.0.0.1:8983/solr/wenda";  //modify
	private HttpSolrClient client = new HttpSolrClient.Builder(SOLR_URL).build();
	private static final String QUESTION_TITLE_FIELD = "question_title";
	private static final String QUESTION_CONTENT_FIELD = "question_content";
	
	public List<Question> searchQuestion(String keyword, int offset, int count, String hlPre, String hlPos) throws Exception
	{
		List<Question> questionList = new ArrayList<>();
		SolrQuery query = new SolrQuery(keyword);
		query.setRows(count);
		query.setStart(offset);
		query.setHighlight(true);
		query.setHighlightSimplePre(hlPre);
		query.setHighlightSimplePost(hlPos);
		query.set("hl.fl", QUESTION_TITLE_FIELD + "," + QUESTION_CONTENT_FIELD);
		QueryResponse response = client.query(query);
		
		for(Entry<String, Map<String, List<String>>> entity : response.getHighlighting().entrySet())
		{
			Question q = new Question();
			q.setId(Integer.parseInt(entity.getKey()));
			if (entity.getValue().containsKey(QUESTION_CONTENT_FIELD))
			{
				List<String> contentList = entity.getValue().get(QUESTION_CONTENT_FIELD);
				if (contentList.size() > 0)
				{
					q.setContent(contentList.get(0));
				}
			}
			
			if (entity.getValue().containsKey(QUESTION_TITLE_FIELD))
			{
				List<String> titleList = entity.getValue().get(QUESTION_TITLE_FIELD);
				if (titleList.size() > 0)
				{
					q.setTitle(titleList.get(0));
				}
			}
			questionList.add(q);
		}
			
		return questionList;
	}
	
	//为了实现自动添加索引并搜索到新添加的问题 在QuestionController里添加问题成功后 调用该方法
	//qid:问题ID
	//title:问题标题
	//content:问题内容
	public boolean indexQuestion(int qid, String title, String content) throws Exception
	{
		SolrInputDocument doc =  new SolrInputDocument();
		doc.setField("id", qid);
		doc.setField(QUESTION_TITLE_FIELD, title);
		doc.setField(QUESTION_CONTENT_FIELD, content);
		UpdateResponse response = client.add(doc, 1000);
		return response != null && response.getStatus() == 0;
	}

	
}