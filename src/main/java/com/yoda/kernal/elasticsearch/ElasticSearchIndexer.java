package com.yoda.kernal.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoda.util.Validator;

public abstract class ElasticSearchIndexer<T> {
	Logger logger = Logger.getLogger(BrandIndexer.class);

	protected static final String INDEX = "yoda";

	private static Client client;

	abstract void createIndex(T type);

	abstract void updateIndex(T type);

	abstract List<T> search(String keyword);

	public List<Map<String, Object>> search() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		SearchResponse response = getClient().prepareSearch().execute().actionGet();

		SearchHits hits = response.getHits();

		for (SearchHit hit : hits.getHits()) {
			Map<String, Object> map =  hit.sourceAsMap();

			list.add(map);
		}

		return list;
	}

//	public ElasticSearchIndexer() {
//		this("127.0.0.1");
//	}
//
//	public ElasticSearchIndexer(String ipAddress) {
//		client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(ipAddress, 9300));
//	}

	public XContentBuilder jsonBuilder() throws IOException {
		return XContentFactory.jsonBuilder();
	}

//	private static ClusterHealthStatus clusterState() throws Exception {
//		return ElasticsearchClient.getInstance().admin().cluster().clusterStats(new ClusterStatsRequest()).get().getStatus();
//	}

	public String getIndexResponse(String type, String id) {
		GetResponse response = getClient().prepareGet(INDEX, type, id)
			.execute().actionGet();

		return response.getSourceAsString();
	}

	public void createIndex(Object obj, String type, String id) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			byte[] json = mapper.writeValueAsBytes(obj);

			IndexRequestBuilder requestBuilder = getClient()
				.prepareIndex(INDEX, type , id)
				.setSource(json);

			createIndex(requestBuilder);
		}
		catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
	}

	public void createIndex(XContentBuilder builder, String type, String id) {
		IndexRequestBuilder requestBuilder = getClient()
			.prepareIndex(INDEX, type, id)
			.setSource(builder);

		createIndex(requestBuilder);
	}

	private void createIndex(IndexRequestBuilder requestBuilder) {
		IndexResponse response = requestBuilder.execute().actionGet();

		logger.debug("[INDEX CREAT] created : " + response.isCreated()
			+ " index : " + response.getIndex()
			+ " type : " + response.getType()
			+ " id : " + response.getId()
			+ " version :" + response.getVersion());
	}

	public void deleteIndex(String type, String id) {
		DeleteResponse response = getClient().prepareDelete(INDEX, type, id)
			.execute().actionGet();

		logger.debug("[INDEX DELETE]"
			+ " index : " + response.getIndex()
			+ " type : " + response.getType()
			+ " id : " + response.getId()
			+ " version :" + response.getVersion());
	}

	public void updateIndex(XContentBuilder builder, String type, String id) {
		UpdateResponse response = getClient().prepareUpdate(INDEX, type, id)
			.setDoc(builder).get();

		logger.debug("[INDEX UPDATE] created : " + response.isCreated()
			+ " index : " + response.getIndex()
			+ " type : " + response.getType()
			+ " id : " + response.getId()
			+ " version :" + response.getVersion());
	}

	protected Client getClient() {
		if (Validator.isNotNull(client)) {
			return client;
		}
		else {
			setClient();

			return client;
		}
	}

	private void setClient() {
		setClient("127.0.0.1");
	}

	private void setClient(String ipAddress) {
		client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(ipAddress, 9300));
	}

	public void shutdown() {
		getClient().close();
	}
}