package com.taklip.yoda.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taklip.yoda.model.Content;

public class ElasticSearchHandler {
	private Client client;

	ElasticSearchHandler() {
		this("127.0.0.1");
	}

	public ElasticSearchHandler(String ipAddress) {
		client = new TransportClient().addTransportAddress(
			new InetSocketTransportAddress(ipAddress, 9300));
	}

	public static void main(String[] args) throws IOException {
		ElasticSearchHandler handler = new ElasticSearchHandler();

		try {
//			handler.createIndex2();
			handler.createIndex3();
//			handler.createIndex4();
//			handler.getIndexResponse();

			String indexname = "yoda";
	        String type = "content";
			QueryBuilder queryBuilder = QueryBuilders.termQuery("content", "简介");//termQuery
			/*
			 * QueryBuilder queryBuilder = QueryBuilders.boolQuery()
			 * .must(QueryBuilders.termQuery("id", 1));
			 */
			List<Content> result = handler.searcher(queryBuilder, indexname, type);
			for (int i = 0; i < result.size(); i++) {
				Content content = result.get(i);
				System.out.println("(" + content.getContentId() + "):"
						+ content.getDescription());
			}
		} catch (ElasticsearchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createIndex() {
		String json = "{" + "\"user\":\"kimchy\","
				+ "\"postDate\":\"2013-01-30\","
				+ "\"message\":\"trying out Elasticsearch\"" + "}";
	}

	public void createIndex2() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("user","张三");
		json.put("postDate",new Date());
		json.put("message","trying out Elasticsearch");

		IndexResponse response = client
				.prepareIndex("user", "user", "3")
				.setSource(json).execute().actionGet();

		// Index name
		String _index = response.getIndex();
		// Type name
		String _type = response.getType();
		// Document ID (generated or not)
		String _id = response.getId();
		// Version (if it's the first time you index this document, you will get: 1)
		long _version = response.getVersion();
		// isCreated() is true if the document is a new one, false if it has been updated
		boolean created = response.isCreated();

		System.out.println("_index : " + _index + " _type : " + _type + " _id : " + _id + " _version :" + _version + "created : " + created);
	}

	public void createIndex3() throws IOException {
		XContentBuilder builder = jsonBuilder().startObject()
				.field("user", "kimchy").field("postDate", new Date())
				.field("message", "trying out Elasticsearch").endObject();

		String json = builder.string();

		System.out.println(json);

		IndexResponse response = client
				.prepareIndex("user", "user", "1")
				.setSource(builder).execute().actionGet();

		// Index name
		String _index = response.getIndex();
		// Type name
		String _type = response.getType();
		// Document ID (generated or not)
		String _id = response.getId();
		// Version (if it's the first time you index this document, you will get: 1)
		long _version = response.getVersion();
		// isCreated() is true if the document is a new one, false if it has been updated
		boolean created = response.isCreated();

		System.out.println("_index : " + _index + " _type : " + _type + " _id : " + _id + " _version :" + _version + "created : " + created);
	}

	public void createIndex4() throws JsonProcessingException {
		Content content = new Content();
		content.setContentId(2l);
		content.setDescription("简介");

		// instance a json mapper
		ObjectMapper mapper = new ObjectMapper(); // create once, reuse

		// generate json
		byte[] json = mapper.writeValueAsBytes(content);

		IndexResponse response = client
				.prepareIndex("content", "content", "2")
				.setSource(json).execute().actionGet();

		// Index name
		String _index = response.getIndex();
		// Type name
		String _type = response.getType();
		// Document ID (generated or not)
		String _id = response.getId();
		// Version (if it's the first time you index this document, you will get: 1)
		long _version = response.getVersion();
		// isCreated() is true if the document is a new one, false if it has been updated
		boolean created = response.isCreated();

		System.out.println("_index : " + _index + " _type : " + _type + " _id : " + _id + " _version :" + _version + "created : " + created);
	}

	public void createIndexResponse() throws ElasticsearchException, IOException {
		IndexResponse response = client
				.prepareIndex("twitter", "tweet", "1")
				.setSource(
						jsonBuilder().startObject().field("user", "kimchy")
								.field("postDate", new Date())
								.field("message", "trying out Elasticsearch")
								.endObject()).execute().actionGet();

		// Index name
		String _index = response.getIndex();
		// Type name
		String _type = response.getType();
		// Document ID (generated or not)
		String _id = response.getId();
		// Version (if it's the first time you index this document, you will get: 1)
		long _version = response.getVersion();
		// isCreated() is true if the document is a new one, false if it has been updated
		boolean created = response.isCreated();

		System.out.println("_index : " + _index + " _type : " + _type + " _id : " + _id + " _version :" + _version + "created : " + created);
	}

	public void createIndexResponse(String j) throws ElasticsearchException, IOException {
		String json = "{" + "\"user\":\"kimchy\","
				+ "\"postDate\":\"2013-01-30\","
				+ "\"message\":\"trying out Elasticsearch\"" + "}";

		IndexResponse response = client.prepareIndex("twitter", "tweet")
				.setSource(json).execute().actionGet();
	}

	public void getIndexResponse() {
		GetResponse response = client.prepareGet("content", "content", "1")
				.execute().actionGet();

		System.out.println("id : " + response.getId() + " index : " + response.getIndex() + " source : " + response.getSourceAsString() + " _version :" + response.getVersion());
	}

	public XContentBuilder jsonBuilder() throws IOException {
		return XContentFactory.jsonBuilder();
	}

	public List<Content> searcher(QueryBuilder queryBuilder, String indexname,
			String type) {
		List<Content> list = new ArrayList<Content>();
		SearchResponse searchResponse = client.prepareSearch(indexname)
				.setTypes(type).setQuery(queryBuilder).execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		System.out.println("查询到记录数=" + hits.getTotalHits());
		SearchHit[] searchHists = hits.getHits();
		if (searchHists.length > 0) {
			for (SearchHit hit : searchHists) {
				Long contentId = (Long) hit.getSource().get("contentId");
				String description = (String) hit.getSource().get("description");
				Content content = new Content();
				content.setContentId(contentId);
				content.setDescription(description);
				list.add(content);
			}
		}
		return list;
	}

	public void shutdown() {
		client.close();
	}
}