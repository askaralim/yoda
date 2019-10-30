package com.taklip.yoda.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.Solution;
import com.taklip.yoda.model.Term;
import com.taklip.yoda.service.SolutionService;
import com.taklip.yoda.service.TermService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/solution")
public class PortalSolutionController extends PortalBaseController {
	private final Logger logger = LoggerFactory.getLogger(PortalSolutionController.class);

	@Autowired
	SolutionService solutionService;

	@GetMapping
	public ModelAndView showSolutions(HttpServletRequest request) {
		ModelMap model = new ModelMap();

		Site site = getSite();

		String offsetStr = request.getParameter("offset");
		int offset = 0;

		if (null != offsetStr) {
			offset = Integer.valueOf(offsetStr);
		}

		Pagination<Solution> page = solutionService.getSolutions(new RowBounds(offset, 5));

		model.put("site", site);
		model.put("page", page);

		return new ModelAndView("portal/solution/solutions", model);
	}

	@ResponseBody
	@GetMapping("/page")
	public String showPagination(
			@RequestParam(value="offset", defaultValue="0") Integer offset) {
		Pagination<Solution> page = solutionService.getSolutions(new RowBounds(offset, 5));

		JSONArray array = new JSONArray();

		try {
			for (Solution solution : page.getData()) {
				JSONObject jsonObject = new JSONObject();

				jsonObject.put("id", solution.getId());
				jsonObject.put("title", solution.getTitle());
				jsonObject.put("description", solution.getDescription());
				jsonObject.put("imagePath", solution.getImagePath());

				array.add(jsonObject);
			}
		}
		catch (JSONException e) {
			logger.error(e.getMessage());
		}

		return array.toString();
	}

	@GetMapping("/{id}")
	public ModelAndView showSolution(
			@PathVariable("id") Long id, HttpServletRequest request) {
		Site site = getSite();

		Solution solution = solutionService.getSolution(id);

		ModelMap model = new ModelMap();

		model.put("site", site);
		model.put("solution", solution);

		model.put("pageTitle", "【" + solution.getTitle() + "】 - " + site.getSiteName());
		model.put("keywords", solution.getTitle());
		model.put("description", solution.getDescription());
		model.put("url", request.getRequestURL().toString());
		model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");

		return new ModelAndView("portal/solution/solution", model);
	}
}