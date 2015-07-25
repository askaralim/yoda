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

import com.yoda.brand.model.Brand;
import com.yoda.util.Validator;

public class BrandIndexer extends ElasticSearchIndexer<Brand> {
	Logger logger = Logger.getLogger(BrandIndexer.class);

	private static final String TYPE = "brand";

	@Override
	public void createIndex(Brand brand) {
		try {
			XContentBuilder builder = jsonBuilder().startObject()
				.field("name", brand.getName())
				.field("description", brand.getDescription())
				.field("kind", brand.getKind())
				.field("createBy", brand.getCreateBy())
				.field("createDate", brand.getCreateDate())
				.field("updateBy", brand.getUpdateBy())
				.field("updateDate", brand.getUpdateDate())
				.endObject();

			createIndex(builder, TYPE, brand.getBrandId().toString());
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void deleteIndex(long id) {
		deleteIndex(TYPE, String.valueOf(id));
	}

	@Override
	public void updateIndex(Brand brand) {
		String responseSource = getIndexResponse(TYPE, brand.getBrandId().toString());

		if (Validator.isNull(responseSource)) {
			createIndex(brand);
		}

		try {
			XContentBuilder builder = jsonBuilder().startObject()
				.field("name", brand.getName())
				.field("description", brand.getDescription())
				.field("kind", brand.getKind())
				.field("createBy", brand.getCreateBy())
				.field("createDate", brand.getCreateDate())
				.endObject();

			updateIndex(builder, TYPE, brand.getBrandId().toString());
		}
		catch (DocumentMissingException e) {
			logger.error(e.getMessage());
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public List<Brand> search(String keyword) {
		List<Brand> brands = new ArrayList<Brand>();

		SearchResponse response = getClient()
			.prepareSearch(INDEX)
			.setTypes(TYPE)
			.setSearchType(SearchType.QUERY_THEN_FETCH)
			.setQuery(QueryBuilders.termQuery("title", keyword))
			.setFrom(0).setSize(60).setExplain(true).execute().actionGet();

		SearchHits hits = response.getHits();

		for (SearchHit hit : hits.getHits()) {
			int brandId = (Integer)hit.getSource().get("brandId");
			String description = (String)hit.getSource().get("description");

			Brand brand = new Brand();

			brand.setBrandId(brandId);
			brand.setDescription(description);

			brands.add(brand);
		}

		logger.debug("[INDEX SEARCH] Brand - keyword : " + keyword + ", result counts : " + hits.getTotalHits());

		return brands;
	}
}