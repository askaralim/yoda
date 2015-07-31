package com.yoda.kernal.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthStatus;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoda.exception.BulkRequestException;
import com.yoda.util.Validator;

public abstract class ElasticSearchIndexer<T> {
	Logger logger = Logger.getLogger(ElasticSearchIndexer.class);

	protected static final String INDEX = "yoda";

	private static Client client;

	abstract void createIndex(T type);

	abstract void updateIndex(T type);

	abstract List<T> search(String keyword);

	protected List<Map<String, Object>> search() {
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

	protected XContentBuilder jsonBuilder() throws IOException {
		return XContentFactory.jsonBuilder();
	}

	private ClusterHealthStatus clusterState() {
		ClusterHealthStatus status = null;
		try {
			status = getClient().admin().cluster().clusterStats(new ClusterStatsRequest()).get().getStatus();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (ExecutionException e) {
			e.printStackTrace();
		}

		return status;
	}

	protected String getIndexResponse(String type, String id) {
		GetResponse response = getClient().prepareGet(INDEX, type, id)
			.execute().actionGet();

		return response.getSourceAsString();
	}

	protected void createIndex(Object obj, String type, String id) {
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

	protected void createIndex(XContentBuilder builder, String type, String id) {
		IndexRequestBuilder requestBuilder = getClient()
			.prepareIndex(INDEX, type, id)
			.setSource(builder);

		createIndex(requestBuilder);
	}

	private void createIndex(IndexRequestBuilder requestBuilder) {
		IndexResponse response = requestBuilder.execute().actionGet();

		if (logger.isDebugEnabled()) {
			logger.debug("[INDEX CREAT] created : " + response.isCreated()
				+ " index : " + response.getIndex()
				+ " type : " + response.getType()
				+ " id : " + response.getId()
				+ " version :" + response.getVersion());
		}
	}

	protected void createBulkIndex(
			Map<String, XContentBuilder> builders, String type)
		throws BulkRequestException {
		BulkRequestBuilder bulkRequest = getClient().prepareBulk();

		for (String id : builders.keySet()) {
			bulkRequest.add(getClient().prepareIndex(INDEX, type, id).setSource(builders.get(id)));
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();

		if (bulkResponse.hasFailures()) {
			throw new BulkRequestException(bulkResponse.buildFailureMessage());
		}
	}

	protected void deleteIndex(String type, String id) {
		DeleteResponse response = getClient().prepareDelete(INDEX, type, id)
			.execute().actionGet();

		logger.debug("[INDEX DELETE]"
			+ " index : " + response.getIndex()
			+ " type : " + response.getType()
			+ " id : " + response.getId()
			+ " version :" + response.getVersion());
	}

	protected void deleteBulkIndex(String type, List<String> ids)
		throws BulkRequestException {
		BulkRequestBuilder bulkRequest = getClient().prepareBulk();

		for (String id : ids) {
			bulkRequest.add(getClient().prepareDelete(INDEX, type, id));
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();

		if (bulkResponse.hasFailures()) {
			throw new BulkRequestException(bulkResponse.buildFailureMessage());
		}
	}

	protected void updateIndex(XContentBuilder builder, String type, String id) {
		UpdateResponse response = getClient().prepareUpdate(INDEX, type, id)
			.setDoc(builder).get();

		logger.debug("[INDEX UPDATE] created : " + response.isCreated()
			+ " index : " + response.getIndex()
			+ " type : " + response.getType()
			+ " id : " + response.getId()
			+ " version :" + response.getVersion());
	}

	public void updateTypeMapping(String type, XContentBuilder builder) {
		getClient().admin().indices().preparePutMapping(INDEX).setType(type).setSource(builder).get();
	}

	public boolean isTypeExists(String type) {
		boolean typeExists = getClient().admin().indices().prepareTypesExists(INDEX).setTypes(type).execute().actionGet().isExists();

		return typeExists;
	}

	public boolean isIndiceExists(String indice) {
		boolean indiceExists = getClient().admin().indices().prepareExists(indice).execute().actionGet().isExists();

		return indiceExists;
	}

	public void deleteIndice() {
		if (isIndiceExists(INDEX)) {
			getClient().admin().indices().prepareDelete(INDEX).execute().actionGet();
		}
	}

	public void prepareIndice() {
		try {
			getClient()
			.admin()
			.indices()
			.prepareCreate(INDEX)
//			.setSettings(
//				ImmutableSettings.settingsBuilder()
//				.loadFromSource(
//					jsonBuilder()
//						.startObject()
//							.startObject("analysis")
//								.startObject("analyzer")
//									.startObject("ik")
////										.field("type", "org.elasticsearch.index.analysis.IkAnalyzerProvider")
////										.field("tokenizer", "standard")
//									.endObject()
//								.endObject()
//							.endObject()
//						.endObject()
//					.string()))
			.execute().actionGet();
		}
		catch (ElasticsearchException e) {
			logger.error(e.getMessage());
		}
//		catch (IOException e) {
//			logger.error(e.getMessage());
//		}
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