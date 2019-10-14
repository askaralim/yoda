package com.taklip.yoda.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchPhaseExecutionException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taklip.yoda.exception.BulkRequestException;
import com.taklip.yoda.model.Content;

public class ContentIndexer extends ElasticSearchIndexer<Content> {
	private final Logger logger = LoggerFactory.getLogger(ContentIndexer.class);

	private static final String TYPE = "contents";

	public void createBulkIndex(List<Content> contents) throws BulkRequestException {
		Map<String, XContentBuilder> builders = new HashMap<String, XContentBuilder>();

		try {
			XContentBuilder mappingBuilder = jsonBuilder()
				.startObject()
					.startObject(TYPE)
//							.field("dynamic", "strict")
//							.startObject("_id")
//								.field("path", "id")
//							.endObject()
//						.startObject("_all")
//							.field("indexAnalyzer", "ik")
//							.field("searchAnalyzer", "ik")
//							.field("term_vector", "no")
////							.field("enabled", "false")
//							.field("store", "false")
//						.endObject()
						.startObject("properties")
							.startObject("id")
								.field("type", "long")
								.field("store", "no")
								.field("index", "not_analyzed")
							.endObject()
							.startObject("title")
								.field("type", "string")
								.field("store", "no")
								.field("term_vector", "with_positions_offsets")
								.field("indexAnalyzer", "ik")
								.field("searchAnalyzer", "ik")
								.field("include_in_all", "true")
//									.field("boost", "8")
							.endObject()
							.startObject("description")
								.field("type", "string")
								.field("store", "no")
								.field("term_vector", "with_positions_offsets")
								.field("indexAnalyzer", "ik")
								.field("searchAnalyzer", "ik")
								.field("include_in_all", "true")
//									.field("boost", "8")
							.endObject()
						.endObject()
					.endObject()
				.endObject();

			updateTypeMapping(TYPE, mappingBuilder);

			for (Content content : contents) {
				XContentBuilder builder = jsonBuilder().startObject()
					.field("id", content.getId())
					.field("title", content.getTitle())
					.field("shortDescription", content.getShortDescription())
					.field("description", content.getDescription())
					.field("publishDate", content.getPublishDate())
					.field("published", content.isPublished())
//					.field("createBy", content.getCreateBy())
//					.field("createDate", content.getCreateDate())
//					.field("updateBy", content.getUpdateBy())
//					.field("updateDate", content.getUpdateDate())
					.endObject();

				builders.put(content.getId().toString(), builder);
			}

			createBulkIndex(builders, TYPE);
		}
		catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void createIndex(Content content) {
		try {
			XContentBuilder builder = jsonBuilder().startObject()
				.field("id", content.getId())
				.field("title", content.getTitle())
				.field("shortDescription", content.getShortDescription())
				.field("description", content.getDescription())
				.field("publishDate", content.getPublishDate())
				.field("published", content.isPublished())
//				.field("createBy", content.getCreateBy())
//				.field("createDate", content.getCreateDate())
//				.field("updateBy", content.getUpdateBy())
//				.field("updateDate", content.getUpdateDate())
				.endObject();

			createIndex(builder, TYPE, content.getId().toString());
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void deleteIndex(long id) {
		deleteIndex(TYPE, String.valueOf(id));
	}

	public void deleteBulkIndex(List<Content> contents) throws BulkRequestException {
		List<String> ids = new ArrayList<String>();

		for (Content content : contents) {
			ids.add(content.getId().toString());
		}

		deleteBulkIndex(TYPE, ids);
	}

	@Override
	public void updateIndex(Content content) {
		String responseSource = getIndexResponse(TYPE, content.getId().toString());

		if (StringUtils.isNoneBlank(responseSource)) {
			createIndex(content);
		}

		try {
			XContentBuilder builder = jsonBuilder().startObject()
				.field("title", content.getTitle())
				.field("shortDescription", content.getShortDescription())
				.field("description", content.getDescription())
				.field("publishDate", content.getPublishDate())
				.field("published", content.isPublished())
//				.field("updateBy", content.getUpdateBy())
//				.field("updateDate", content.getUpdateDate())
				.endObject();

			updateIndex(builder, TYPE, content.getId().toString());
		}
		catch (DocumentMissingException e) {
			logger.error(e.getMessage());
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public List<Content> search(String keyword) {
		List<Content> contents = new ArrayList<Content>();

		try {
			SearchResponse response = getClient()
				.prepareSearch(INDEX)
				.setTypes(TYPE)
				.setSearchType(SearchType.QUERY_THEN_FETCH)
//					.setQuery(QueryBuilders.termQuery("title", keyword))
				.setQuery(QueryBuilders.queryStringQuery(keyword).field("title").analyzer("ik"))
				.setQuery(QueryBuilders.queryStringQuery(keyword).field("description").analyzer("ik"))
				.setFrom(0).setSize(60).setExplain(true).execute().actionGet();

			SearchHits hits = response.getHits();

			for (SearchHit hit : hits.getHits()) {
				long id = (Integer)hit.getSource().get("id");
				String title = (String)hit.getSource().get("title");
				String shortDescription = (String)hit.getSource().get("shortDescription");

				Content content = new Content();

				content.setId(id);
				content.setTitle(title);
				content.setShortDescription(shortDescription);

				contents.add(content);
			}

			logger.debug("[INDEX SEARCH] Content - keyword : " + keyword + ", result counts : " + hits.getTotalHits());
		}
		catch (SearchPhaseExecutionException e) {
			logger.error("[INDEX SEARCH] Content - keyword : " + keyword + ", error : " + e.getMessage());
		}

		return contents;
	}
}