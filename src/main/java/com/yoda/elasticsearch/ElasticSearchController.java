package com.yoda.elasticsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yoda.brand.model.Brand;
import com.yoda.brand.service.BrandService;
import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.kernal.elasticsearch.BrandIndexer;
import com.yoda.kernal.elasticsearch.ContentIndexer;
import com.yoda.kernal.util.PortalUtil;

@Controller
@RequestMapping(value="/controlpanel/elasticsearch")
public class ElasticSearchController {
	@Autowired
	ContentService contentService;

	@Autowired
	BrandService brandService;

	@RequestMapping(method = RequestMethod.GET)
	public String showPanel(Map<String, Object> model) {
		List<String> types = new ArrayList<String>();

		types.add("content");
		types.add("brand");

		model.put("types", types);

		return "controlpanel/indices";
	}

	@RequestMapping(value = "/reindex/{type}", method = RequestMethod.GET)
	public String reindex(
			@PathVariable("type") String type,Map<String, Object> model,
			HttpServletRequest request) {
		if (type.equals("content")) {
			List<Content> contents = getContents(request);

			ContentIndexer indexer = new ContentIndexer();

			for (Content content : contents) {
				indexer.deleteIndex(content.getContentId());

				indexer.createIndex(content);
			}
		}
		else if (type.equals("brand")) {
			List<Brand> brands = getBrands();

			BrandIndexer indexer = new BrandIndexer();

			for (Brand brand : brands) {
				indexer.deleteIndex(brand.getBrandId());

				indexer.createIndex(brand);
			}
		}

		model.put("success", "success");

		return "controlpanel/indices";
	}

	private List<Brand> getBrands() {
		return brandService.getBrands();
	}

	private List<Content> getContents(HttpServletRequest request) {
		return contentService.getContents(PortalUtil.getSiteId(request));
	}
}