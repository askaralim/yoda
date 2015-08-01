package com.yoda.portal.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yoda.brand.model.Brand;
import com.yoda.content.model.Content;
import com.yoda.kernal.elasticsearch.BrandIndexer;
import com.yoda.kernal.elasticsearch.ContentIndexer;
import com.yoda.portal.content.data.DefaultTemplateEngine;
import com.yoda.portal.content.data.PageInfo;
import com.yoda.portal.content.data.SearchInfo;
import com.yoda.portal.content.data.SiteInfo;
import com.yoda.site.model.Site;
import com.yoda.util.Format;
import com.yoda.util.StringPool;

@Controller
@RequestMapping("/search")
public class FrontendSearchController extends BaseFrontendController {
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(
			Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) {
		Site site = getSite(request);

		String q = StringPool.BLANK;

		try {
			q = new String(request.getParameter("q").getBytes("iso-8859-1"), "utf-8");
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		SiteInfo siteInfo = getSite(site);

		String horizontalMenu = getHorizontalMenu(request, response);

		model.put("horizontalMenu", horizontalMenu);
		model.put("siteInfo", siteInfo);

		ContentIndexer indexer = new ContentIndexer();
		BrandIndexer brandIndexer = new BrandIndexer();

		List<Content> contents = indexer.search(q);
		List<Brand> brands = brandIndexer.search(q);

		model.put("contentsTotal", contents.size());
		model.put("contents", contents);
		model.put("brandsTotal", brands.size());
		model.put("brands", brands);
		model.put("q", q);

		return "portal/search/search";
	}

	public PageInfo getSearch(
			HttpServletRequest request, HttpServletResponse response) {
		Site site = getSite(request);

		PageInfo pageInfo = new PageInfo();

		String value = (String) request.getParameter("pageNum");

		if (value == null) {
			value = "1";
		}

		int pageNum = Format.getInt(value);

		String query = (String) request.getParameter("keyword");

//		SearchInfo searchInfo = getSearch(templateEngine.site.getSiteId(), query, templateEngine.pageSize, Constants.PAGE_NAV_COUNT, pageNum);
		SearchInfo searchInfo = new SearchInfo();

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("searchInfo", searchInfo);

		String text = DefaultTemplateEngine.getTemplate(request, response, "search/search.vm", model);

		pageInfo.setPageBody(text);

		pageInfo.setPageTitle(site + " - " + "Search");

		return pageInfo;
	}

	public SearchInfo getSearch(
			long siteId, String query, int pageSize, int pageNavCount,
			int pageNum)
		throws Exception {
//		Indexer indexer = Indexer.getInstance(siteId);
//		QueryResult queryResult = indexer.search(query, pageNum, pageSize);
//
//		SearchInfo info = new SearchInfo();
//
//		info.setQuery(query);
//		info.setHitsCount(queryResult.getHitCount());
//
//		int recordCount = queryResult.getHitCount();
//		int pageTotal = recordCount / pageSize;
//
//		if (recordCount % pageSize > 0) {
//			pageTotal++;
//		}
//
//		info.setPageTotal(pageTotal);
//		info.setPageNum(pageNum);
//
//		int pageStart = pageNum - pageNavCount / 2;
//
//		if (pageStart < 1) {
//			pageStart = 1;
//		}
//
//		int pageEnd = pageNum + (pageNavCount + 1) / 2;
//
//		if (pageEnd > pageTotal) {
//			pageEnd = pageTotal;
//		}
//
//		info.setPageStart(pageStart);
//		info.setPageEnd(pageEnd);
//
//		Vector<DataInfo> vector = new Vector<DataInfo>();
//
//		QueryHit queryHits[] = queryResult.getQueryHits();
//
//		for (int i = 0; i < queryHits.length; i++) {
//			QueryHit queryHit = queryHits[i];
//			String type = queryHit.getType();
//			String id = queryHit.getId();
//
//			if (type.equals(Constants.DATA_TYPE_CONTENT)) {
//				ContentInfo contentInfo = this.getContent(siteId, Format.getLong(id), true, false);
//
//				vector.add(contentInfo);
//			}
////			else {
////				ItemInfo itemInfo = getItem(Format.getLong(id), true, false);
////
////				vector.add(itemInfo);
////			}
//		}
//
//		Object searchDatas[] = new Object[vector.size()];
//
//		vector.copyInto(searchDatas);
//
//		info.setSearchDatas(searchDatas);
//
//		return info;
		return null;
	}
}