package com.yoda.kernal.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.yoda.content.model.Content;
import com.yoda.util.Validator;

public class ContentIndexer extends ElasticSearchIndexer<Content> {
	Logger logger = Logger.getLogger(ContentIndexer.class);

	private static final String TYPE = "contents";

	@Override
	public void createIndex(Content content) {
		try {
			XContentBuilder builder = jsonBuilder().startObject()
				.field("title", content.getTitle())
				.field("shortDescription", content.getShortDescription())
				.field("description", content.getDescription())
				.field("publishDate", content.getPublishDate())
				.field("published", content.isPublished())
				.field("createBy", content.getCreateBy())
				.field("createDate", content.getCreateDate())
				.field("updateBy", content.getUpdateBy())
				.field("updateDate", content.getUpdateDate())
				.endObject();

			createIndex(builder, TYPE, content.getContentId().toString());
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void deleteIndex(long id) {
		deleteIndex(TYPE, String.valueOf(id));
	}

	@Override
	public void updateIndex(Content content) {
		String responseSource = getIndexResponse(TYPE, content.getContentId().toString());

		if (Validator.isNull(responseSource)) {
			createIndex(content);
		}

		try {
			XContentBuilder builder = jsonBuilder().startObject()
				.field("title", content.getTitle())
				.field("shortDescription", content.getShortDescription())
				.field("description", content.getDescription())
				.field("publishDate", content.getPublishDate())
				.field("published", content.isPublished())
				.field("updateBy", content.getUpdateBy())
				.field("updateDate", content.getUpdateDate())
				.endObject();

			updateIndex(builder, TYPE, content.getContentId().toString());
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

		SearchResponse response = getClient()
			.prepareSearch(INDEX)
			.setTypes(TYPE)
			.setSearchType(SearchType.QUERY_THEN_FETCH)
			.setQuery(QueryBuilders.termQuery("title", keyword))
			.setFrom(0).setSize(60).setExplain(true).execute().actionGet();

		SearchHits hits = response.getHits();

		for (SearchHit hit : hits.getHits()) {
			long contentId = (Integer)hit.getSource().get("contentId");
			String description = (String)hit.getSource().get("description");

			Content content = new Content();

			content.setContentId(contentId);
			content.setDescription(description);

			contents.add(content);
		}

		logger.debug("[INDEX SEARCH] Content - keyword : " + keyword + ", result counts : " + hits.getTotalHits());

		return contents;
	}
}