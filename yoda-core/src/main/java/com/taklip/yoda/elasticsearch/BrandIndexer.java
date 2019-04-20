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
import com.taklip.yoda.model.Brand;

public class BrandIndexer extends ElasticSearchIndexer<Brand> {
	private final Logger logger = LoggerFactory.getLogger(BrandIndexer.class);

	private static final String TYPE = "brand";

	public void createBulkIndex(List<Brand> brands) throws BulkRequestException {
		Map<String, XContentBuilder> builders = new HashMap<String, XContentBuilder>();

		try {
			XContentBuilder mappingBuilder = jsonBuilder()
				.startObject()
					.startObject(TYPE)
//						.field("dynamic", "strict")
//						.startObject("_id")
//							.field("path", "id")
//						.endObject()
//						.startObject("_all")
//							.field("indexAnalyzer", "ik")
//							.field("searchAnalyzer", "ik")
//							.field("term_vector", "no")
////							.field("enabled", "false")
//							.field("store", "false")
//						.endObject()
						.startObject("properties")
							.startObject("brandId")
								.field("type", "integer")
								.field("store", "no")
								.field("index", "not_analyzed")
							.endObject()
							.startObject("name")
								.field("type", "string")
								.field("store", "no")
								.field("term_vector", "with_positions_offsets")
								.field("indexAnalyzer", "ik")
								.field("searchAnalyzer", "ik")
								.field("include_in_all", "true")
//								.field("boost", "8")
							.endObject()
							.startObject("description")
								.field("type", "string")
								.field("store", "no")
								.field("term_vector", "with_positions_offsets")
								.field("indexAnalyzer", "ik")
								.field("searchAnalyzer", "ik")
								.field("include_in_all", "true")
//								.field("boost", "8")
							.endObject()
						.endObject()
					.endObject()
				.endObject();

			updateTypeMapping(TYPE, mappingBuilder);

			for (Brand brand : brands) {
				XContentBuilder builder = jsonBuilder().startObject()
					.field("brandId", brand.getBrandId())
					.field("name", brand.getName())
					.field("description", brand.getDescription())
					.field("imagePath", brand.getImagePath())
					.field("kind", brand.getKind())
					.field("createBy", brand.getCreateBy())
					.field("createDate", brand.getCreateDate())
					.field("updateBy", brand.getUpdateBy())
					.field("updateDate", brand.getUpdateDate())
					.endObject();

				builders.put(brand.getBrandId().toString(), builder);
			}

			createBulkIndex(builders, TYPE);
		}
		catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void createIndex(Brand brand) {
		try {
			XContentBuilder builder = jsonBuilder().startObject()
				.field("brandId", brand.getBrandId())
				.field("name", brand.getName())
				.field("description", brand.getDescription())
				.field("imagePath", brand.getImagePath())
				.field("kind", brand.getKind())
//				.field("createBy", brand.getCreateBy())
//				.field("createDate", brand.getCreateDate())
//				.field("updateBy", brand.getUpdateBy())
//				.field("updateDate", brand.getUpdateDate())
				.endObject();

			createIndex(builder, TYPE, brand.getBrandId().toString());
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void deleteBulkIndex(List<Brand> brands) throws BulkRequestException {
		List<String> ids = new ArrayList<String>();

		for (Brand brand : brands) {
			ids.add(brand.getBrandId().toString());
		}

		deleteBulkIndex(TYPE, ids);
	}

	public void deleteIndex(long id) {
		deleteIndex(TYPE, String.valueOf(id));
	}

	@Override
	public void updateIndex(Brand brand) {
		String responseSource = getIndexResponse(TYPE, brand.getBrandId().toString());

		if (StringUtils.isNoneBlank(responseSource)) {
			createIndex(brand);
		}

		try {
			XContentBuilder builder = jsonBuilder().startObject()
				.field("name", brand.getName())
				.field("description", brand.getDescription())
				.field("imagePath", brand.getImagePath())
				.field("kind", brand.getKind())
//				.field("updateBy", brand.getUpdateBy())
//				.field("updateDate", brand.getUpdateDate())
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

		try {
			SearchResponse response = getClient()
				.prepareSearch(INDEX)
				.setTypes(TYPE)
				.setSearchType(SearchType.QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.termQuery("name", keyword))
				.setFrom(0).setSize(60).setExplain(true).execute().actionGet();

			SearchHits hits = response.getHits();

			for (SearchHit hit : hits.getHits()) {
				int brandId = (Integer)hit.getSource().get("brandId");
				String name = (String)hit.getSource().get("name");
				String description = (String)hit.getSource().get("description");
				String imagePath = (String)hit.getSource().get("imagePath");

				Brand brand = new Brand();

				brand.setBrandId(brandId);
				brand.setName(name);
				brand.setDescription(description);
				brand.setImagePath(imagePath);

				brands.add(brand);

				logger.debug("[INDEX SEARCH] Brand - keyword : " + keyword + ", result counts : " + hits.getTotalHits());
			}
		}
		catch (SearchPhaseExecutionException e) {
			logger.error("[INDEX SEARCH] Content - keyword : " + keyword + ", error : " + e.getMessage());
		}

		return brands;
	}
}