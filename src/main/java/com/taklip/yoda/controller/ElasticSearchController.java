// package com.taklip.yoda.controller;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;

// import com.taklip.yoda.exception.BulkRequestException;
// import com.taklip.yoda.model.Brand;
// import com.taklip.yoda.model.Content;
// import com.taklip.yoda.service.BrandService;
// import com.taklip.yoda.service.ContentService;

// @Controller
// @RequestMapping(value = "/controlpanel/elasticsearch")
// public class ElasticSearchController {
// 	@Autowired
// 	ContentService contentService;

// 	@Autowired
// 	BrandService brandService;

// 	@RequestMapping(method = RequestMethod.GET)
// 	public String showPanel(Map<String, Object> model) {
// 		List<String> types = new ArrayList<String>();

// 		types.add("content");
// 		types.add("brand");

// 		model.put("types", types);

// 		return "controlpanel/indices";
// 	}

// 	// @RequestMapping(value = "/prepareindex", method = RequestMethod.GET)
// 	// public String prepareIndex(Map<String, Object> model) {
// 	// 	ContentIndexer indexer = new ContentIndexer();

// 	// 	indexer.prepareIndice();

// 	// 	List<String> types = new ArrayList<String>();

// 	// 	types.add("content");
// 	// 	types.add("brand");

// 	// 	model.put("types", types);
// 	// 	model.put("success", "success");

// 	// 	return "controlpanel/indices";
// 	// }

// 	// @RequestMapping(value = "deleteindex", method = RequestMethod.GET)
// 	// public String deleteIndex(Map<String, Object> model) {
// 	// 	ContentIndexer indexer = new ContentIndexer();

// 	// 	indexer.deleteIndice();

// 	// 	List<String> types = new ArrayList<String>();

// 	// 	types.add("content");
// 	// 	types.add("brand");

// 	// 	model.put("types", types);
// 	// 	model.put("success", "success");

// 	// 	return "controlpanel/indices";
// 	// }

// 	// @RequestMapping(value = "/reindex/{type}", method = RequestMethod.GET)
// 	// public String reindex(
// 	// 		@PathVariable("type") String type, Map<String, Object> model) {
// 	// 	try {
// 	// 		if (type.equals("content")) {
// 	// 			List<Content> contents = getContents();

// 	// 			ContentIndexer indexer = new ContentIndexer();

// 	// 			indexer.deleteBulkIndex(contents);
// 	// 			indexer.createBulkIndex(contents);
// 	// 		} else if (type.equals("brand")) {
// 	// 			List<Brand> brands = getBrands();

// 	// 			BrandIndexer indexer = new BrandIndexer();

// 	// 			indexer.deleteBulkIndex(brands);
// 	// 			indexer.createBulkIndex(brands);
// 	// 		}

// 	// 		model.put("success", "success");
// 	// 	} catch (BulkRequestException e) {
// 	// 		model.put("error", e.getMessage());
// 	// 	} finally {
// 	// 		List<String> types = new ArrayList<String>();

// 	// 		types.add("content");
// 	// 		types.add("brand");

// 	// 		model.put("types", types);
// 	// 	}

// 	// 	return "controlpanel/indices";
// 	// }

// 	private List<Brand> getBrands() {
// 		return brandService.getBrands();
// 	}

// 	private List<Content> getContents() {
// 		return contentService.getContents();
// 	}
// }