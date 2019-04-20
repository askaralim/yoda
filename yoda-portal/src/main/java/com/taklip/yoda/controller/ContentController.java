package com.taklip.yoda.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.elasticsearch.ContentIndexer;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Category;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.ContentBrand;
import com.taklip.yoda.model.ContentContributor;
import com.taklip.yoda.model.HomePage;
import com.taklip.yoda.model.Menu;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.HomePageService;
import com.taklip.yoda.service.MenuService;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.tool.StringPool;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.DateUtil;
import com.taklip.yoda.util.SiteUtil;
import com.taklip.yoda.validator.ContentEditValidator;
import com.taklip.yoda.vo.ContentSearchForm;

@Controller
public class ContentController {

	@Autowired
	UserService userService;

	@Autowired
	SiteService siteService;

	@Autowired
	ContentService contentService;

	@Autowired
	HomePageService homePageService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	MenuService menuService;

	@Autowired
	BrandService brandService;

	@RequestMapping(value="/controlpanel/content", method = RequestMethod.GET)
	public String showPanel(Map<String, Object> model, HttpServletRequest request) {

		User user = AuthenticatedUtil.getAuthenticatedUser();

		String offset = request.getParameter("offset");

		int offsetInt = 0;

		if (!StringUtils.isEmpty(offset)) {
			offsetInt = Integer.valueOf(offset) * 10;
		}

		Pagination<Content> page = contentService.getContents(user.getLastVisitSiteId(), new RowBounds(offsetInt, 10));

		model.put("page", page);
		model.put("searchForm", new ContentSearchForm());

		return "controlpanel/content/list";
	}

	@RequestMapping(value = "/controlpanel/content/add", method = RequestMethod.GET)
	public ModelAndView setupForm(Map<String, Object> model) {
		Content content = new Content();

		content.setPublished(true);
		content.setHomePage(false);
		content.setFeatureData(false);
		content.setPublishDate(new Date());
		content.setExpireDate(DateUtil.getHighDate());

		List<Category> categories = categoryService.getCategories();

		model.put("categories", categories);
		model.put("content", content);

		return new ModelAndView("controlpanel/content/edit", model);
	}

	@RequestMapping(value = "/controlpanel/content/add", method = RequestMethod.POST)
	public ModelAndView processSubmit(
			@ModelAttribute Content content,
			@RequestParam("categoryId") Integer categoryId,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {
		new ContentEditValidator().validate(content, result);

		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			model.put("errors", "errors");

			List<Category> categories = categoryService.getCategories();

			model.put("categories", categories);

			return new ModelAndView("controlpanel/content/edit", model);
		}

		Site site = SiteUtil.getDefaultSite();

		contentService.addContent(site.getSiteId(), content, categoryId);

		return new ModelAndView("redirect:/controlpanel/content/" + content.getContentId() + "/edit", model);
	}

	@RequestMapping(value = "/controlpanel/content/{contentId}/edit", method = RequestMethod.GET)
	public String initUpdateForm(
			@PathVariable("contentId") long contentId,
			Map<String, Object> model, HttpServletRequest request) {
		Site site = SiteUtil.getDefaultSite();

		Content content = contentService.getContent(contentId);

//		copyProperties(command, content);

//		createAdditionalInfo(user, content, command);

		content.setHomePage(false);

		if (getHomePage(site.getSiteId(), content.getContentId()) != null) {
			content.setHomePage(true);
		}

		List<Category> categories = categoryService.getCategories();

		model.put("categories", categories);
		model.put("content", content);

		return "controlpanel/content/edit";
	}

	@RequestMapping(value = "/controlpanel/content/{contentId}/edit", method = RequestMethod.POST)
	public ModelAndView updateContent(
			@ModelAttribute Content content,
			@RequestParam("categoryId") Integer categoryId,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {
		Site site = SiteUtil.getDefaultSite();

		new ContentEditValidator().validate(content, result);

		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/content/edit", model);
		}

		Content contentDb = contentService.updateContent(site.getSiteId(), content, categoryId);

		saveContentContributors(request, contentDb);

//		Indexer.getInstance(siteId).removeContent(content);
//		Indexer.getInstance(siteId).indexContent(content);

//		copyProperties(command, content);

//		createAdditionalInfo(user, content, content);

//		uploadImage(content.getContentId(), contentImage, request, response);

		if (getHomePage(site.getSiteId(), content.getContentId()) != null) {
			contentDb.setHomePage(true);
		}

		List<Category> categories = categoryService.getCategories();

		model.put("categories", categories);
		model.put("content", contentDb);
		model.put("success", "success");

		new ContentIndexer().updateIndex(content);

		return new ModelAndView("controlpanel/content/edit", model);
	}

	@RequestMapping(value = "/controlpanel/content/{contentId}/brand/add", method = RequestMethod.GET)
	public ModelAndView initCreationForm(
			@PathVariable("contentId") long contentId, Map<String, Object> model) {
		ContentBrand contentBrand = new ContentBrand();

		contentBrand.setContentId(contentId);

		List<Brand> brands = brandService.getBrands();

		model.put("contentBrand", contentBrand);
		model.put("brands", brands);

		return new ModelAndView("controlpanel/content/editContentBrand", model);
	}

	@RequestMapping(value = "/controlpanel/content/{contentId}/brand/add", method = RequestMethod.POST)
	public ModelAndView processCreationForm(
			@ModelAttribute("contentBrand") ContentBrand contentBrand,
			@RequestParam("brandId") Integer brandId, BindingResult result,
			SessionStatus status, HttpServletRequest request,
			HttpServletResponse response) {
		ModelMap model = new ModelMap();

		String brandName = StringPool.BLANK;
		String brandLogo = StringPool.BLANK;

		Brand brand = null;

		if (null != brandId) {
			brand = brandService.getBrand(brandId);
			brandName = brand.getName();
			brandLogo = brand.getImagePath();
		}

		contentBrand.setBrandName(brandName);
		contentBrand.setBrandLogo(brandLogo);

		contentService.addContentBrand(contentBrand);

		return new ModelAndView("redirect:/controlpanel/content/" + contentBrand.getContentId() + "/brand/" + contentBrand.getContentBrandId() + "/edit", model);
	}

	@RequestMapping(value = "/controlpanel/content/{contentId}/brand/{contentBrandId}/edit", method = RequestMethod.GET)
	public String initUpdateForm(@PathVariable("contentBrandId") int contentBrandId, Map<String, Object> model) {
		ContentBrand contentBrand = contentService.getContentBrand(contentBrandId);

		List<Brand> brands = brandService.getBrands();

		model.put("contentBrand", contentBrand);
		model.put("brands", brands);

		return "controlpanel/content/editContentBrand";
	}

	@RequestMapping(value = "/controlpanel/content/{contentId}/brand/{contentBrandId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
	public ModelAndView processUpdateForm(
			@ModelAttribute("contentBrand") ContentBrand contentBrand,
			@RequestParam("brandId") Integer brandId,
			BindingResult result, SessionStatus status) {
		ModelMap model = new ModelMap();

		String brandName = StringPool.BLANK;
		String brandLogo = StringPool.BLANK;
		Brand brand = null;

		if (null != brandId) {
			brand = brandService.getBrand(brandId);
			brandName = brand.getName();
			brandLogo = brand.getImagePath();
		}

		contentBrand.setBrandName(brandName);
		contentBrand.setBrandLogo(brandLogo);

		contentService.updateContentBrand(contentBrand);

		List<Brand> brands = brandService.getBrands();

		model.put("contentBrand", contentBrand);
		model.put("brands", brands);
		model.put("success", "success");

		status.setComplete();

		return new ModelAndView("controlpanel/content/editContentBrand", model);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String deleteContent(
			@ModelAttribute Content content,
			HttpServletRequest request) {
		long contentId = content.getContentId();

		Content contentDb = contentService.getContent(contentId);

//		ContentImage contentImage = content.getImage();
//
//		if (contentImage != null) {
//			contentImageService.deleteContentImage(contentImage);
//		}

//		Iterator iterator = content.getImages().iterator();

//		while (iterator.hasNext()) {
//			contentImage = (ContentImage)iterator.next();
//
//			contentImageService.deleteContentImage(contentImage);
//		}

		Iterator iterator = (Iterator)contentDb.getMenus().iterator();

		while (iterator.hasNext()) {
			Menu menu = (Menu)iterator.next();
			menu.setContent(null);
		}

		contentService.deleteContent(contentDb);

//		Indexer.getInstance(siteId).removeContent(content);

		return "redirect:/controlpanel/content/list";
	}

	@RequestMapping(value = "/resetCounter")
	public void resetCounter(
			@PathVariable("contentId") Long contentId,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = AuthenticatedUtil.getAuthenticatedUser();

		Content content = contentService.getContent(contentId);

		content.setHitCounter(0);
		content.setUpdateBy(user);
		content.setUpdateDate(new Date());

		contentService.updateContent(content);

		JSONObject jsonResult = new JSONObject();

		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);
		jsonResult.put("updateBy", content.getUpdateBy());
		jsonResult.put("updateDate", DateUtil.getFullDatetime(content.getUpdateDate()));

		String jsonString = jsonResult.toString();

		response.setContentType("text/html");
		response.setContentLength(jsonString.length());

		OutputStream outputStream = response.getOutputStream();

		outputStream.write(jsonString.getBytes());
		outputStream.flush();
	}

	public JSONObject createJsonSelectedMenus(int siteId, Content content)
		throws Exception {
		JSONObject jsonResult = new JSONObject();

		Iterator iterator = content.getMenus().iterator();

		Vector<JSONObject> menus = new Vector<JSONObject>();

		while (iterator.hasNext()) {
			Menu menu = (Menu) iterator.next();

			JSONObject menuObject = new JSONObject();

			menuObject.put("menuId", menu.getMenuId());
			menuObject.put("menuLongDesc", menuService.formatMenuName(siteId, menu.getMenuId()));
			menuObject.put("menuWindowMode", menu.getMenuWindowMode());
			menuObject.put("menuWindowTarget", menu.getMenuWindowTarget());
			menus.add(menuObject);
		}

		jsonResult.put("menus", menus);
		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);

		return jsonResult;
	}

	@RequestMapping("/removeMenus")
	public void removeMenus(
			@PathVariable("contentId") Long contentId,
			@RequestParam("removeMenus") int[] menuIds,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = AuthenticatedUtil.getAuthenticatedUser();

		Content content = contentService.getContent(contentId);

		if (menuIds != null) {
			for (int i = 0; i < menuIds.length; i++) {
				Menu menu = menuService.getMenu(menuIds[i]);

				menu.setContent(null);
				menu.setMenuUrl("");
				menu.setMenuType("");

				menuService.updateMenu(menu);
			}
		}

		content.setUpdateBy(user);
		content.setUpdateDate(new Date());

		contentService.updateContent(content);

		JSONObject jsonResult = createJsonSelectedMenus(user.getLastVisitSiteId(), content);

		jsonResult.put("updateBy", content.getUpdateBy());
		jsonResult.put("updateDate", DateUtil.getFullDatetime(content.getUpdateDate()));

		String jsonString = jsonResult.toString();

		response.setContentType("text/html");
		response.setContentLength(jsonString.length());

		OutputStream outputStream = response.getOutputStream();

		outputStream.write(jsonString.getBytes());
		outputStream.flush();
	}

	@RequestMapping("/addMenus")
	public void addMenus(
			@PathVariable("contentId") Long contentId,
			@RequestParam("menuWindowTarget") String menuWindowTarget,
			@RequestParam("menuWindowMode") String menuWindowMode,
			@RequestParam("addMenus") int[] addMenus,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = AuthenticatedUtil.getAuthenticatedUser();

		Content content = contentService.getContent(contentId);

		if (addMenus != null) {
			for (int i = 0; i < addMenus.length; i++) {
				menuService.updateMenu(
					user.getLastVisitSiteId(), addMenus[i], content, null, "",
					menuWindowMode, menuWindowTarget, Constants.MENU_CONTENT);
			}
		}

		content.setUpdateBy(user);
		content.setUpdateDate(new Date());

		contentService.updateContent(content);

		JSONObject jsonResult = createJsonSelectedMenus(user.getLastVisitSiteId(), content);

		jsonResult.put("updateBy", content.getUpdateBy());
		jsonResult.put("updateDate", DateUtil.getFullDatetime(content.getUpdateDate()));

		String jsonString = jsonResult.toString();

		response.setContentType("text/html");
		response.setContentLength(jsonString.length());

		OutputStream outputStream = response.getOutputStream();

		outputStream.write(jsonString.getBytes());
		outputStream.flush();
	}

	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public String uploadImage(
			@RequestParam("file") MultipartFile file,
			@PathVariable("contentId") long contentId,
			HttpServletRequest request)
		throws Throwable {
//		User user = PortalUtil.getAuthenticatedUser();

		Site site = SiteUtil.getDefaultSite();

		if (file.getBytes().length <= 0) {
			return "redirect:/controlpanel/content/" + contentId + "/edit";
		}

		if (StringUtils.isEmpty(file.getName())) {
			return "redirect:/controlpanel/content/" + contentId + "/edit";
		}

		contentService.updateContentImage(
			site.getSiteId(), contentId, file);

//		ImageScaler scaler = null;
//
//		try {
//			scaler = new ImageScaler(fileData, file.getContentType());
//			scaler.resize(600);
//		}
//		catch (Exception e) {
//			jsonResult.put("status", Constants.WEBSERVICE_STATUS_FAILED);
//			jsonResult.put("message", "error.image.invalid");
//
//			String jsonString = jsonResult.toString();
//
//			response.setContentType("text/html");
//			response.setContentLength(jsonString.length());
//
//			OutputStream outputStream = response.getOutputStream();
//
//			outputStream.write(jsonString.getBytes());
//			outputStream.flush();
//
//			return;
//		}

//		ContentImage contentImage = contentImageService.addContentImage(
//			user.getLastVisitSiteId(), user.getUserId(), file.getOriginalFilename(),
//			file.getContentType(), scaler.getBytes(), scaler.getHeight(),
//			scaler.getWidth());

//		jsonResult = createJsonImages(admin.getSiteId(), content);
//		jsonResult.put("recUpdateBy", content.getRecUpdateBy());
//		jsonResult.put("recUpdateDatetime",Format.getFullDatetime(content.getRecUpdateDatetime()));
//
//		String jsonString = jsonResult.toString();
//
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//
//		OutputStream outputStream = response.getOutputStream();
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();

		return "redirect:/controlpanel/content/" + contentId + "/edit";
	}

	public JSONObject createJsonImages(int siteId, Content content)
			throws Exception {
		JSONObject jsonResult = new JSONObject();

//		ContentImage defaultImage = content.getImage();
//
//		if (defaultImage != null) {
//			JSONObject jsonDeFaultImage = new JSONObject();
//
//			jsonDeFaultImage.put("imageId", defaultImage.getImageId());
//			jsonDeFaultImage.put("imageName", defaultImage.getImageName());
//			jsonResult.put("defaultImage", jsonDeFaultImage);
//		}

//		Iterator iterator = content.getImages().iterator();

//		Vector<JSONObject> vector = new Vector<JSONObject>();
//
//		while (iterator.hasNext()) {
//			ContentImage image = (ContentImage) iterator.next();
//
//			JSONObject jsonImage = new JSONObject();
//
//			jsonImage.put("imageId", image.getImageId());
//			jsonImage.put("imageName", image.getImageName());
//			vector.add(jsonImage);
//		}
//
//		jsonResult.put("images", vector);
		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);

		return jsonResult;
	}

//	public void removeImages(
//			@PathVariable("contentId") Long contentId,
//			@RequestParam("removeImage") Long imageId,
//			HttpServletRequest request, HttpServletResponse response)
//		throws Throwable {
//		User user = PortalUtil.getAuthenticatedUser();
//
//		Content content = contentService.deleteContentImage(
//			user.getLastVisitSiteId(), user.getUserId(), contentId, imageId);
//
//		JSONObject jsonResult = createJsonImages(user.getLastVisitSiteId(), content);
//
//		jsonResult.put("updateBy", content.getUpdateBy());
//		jsonResult.put("updateDate", Format.getFullDatetime(content.getUpdateDate()));
//
//		String jsonString = jsonResult.toString();
//
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//
//		OutputStream outputStream = response.getOutputStream();
//
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
//	}

//	@RequestMapping("/defaultImage")
//	public void defaultImage(
//			@PathVariable("contentId") Long contentId,
//			@RequestParam("cefaultImageId") Long defaultImageId,
//			HttpServletRequest request, HttpServletResponse response)
//		throws Throwable {
//		Admin admin = getAdminBean(request);
//
//		Content content = contentService.updateDefaultContentImage(admin.getSiteId(), admin.getUserId(), contentId, defaultImageId);
//
//		JSONObject jsonResult = createJsonImages(admin.getSiteId(), content);
//
//		jsonResult.put("updateBy", content.getUpdateBy());
//		jsonResult.put("updateDate", Format.getFullDatetime(content.getUpdateDate()));
//
//		String jsonString = jsonResult.toString();
//
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//
//		OutputStream outputStream = response.getOutputStream();
//
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
//	}

//	@Deprecated
//	public void createAdditionalInfo(
//			User user, Content content, ContentEditCommand command)
//		throws Exception {
//		int siteId = user.getLastVisitSiteId();
//
//		Iterator iterator = content.getMenus().iterator();
//
//		Vector<ContentMenuDisplayCommand> selectedMenuVector = new Vector<ContentMenuDisplayCommand>();
//
//		while (iterator.hasNext()) {
//			Menu menu = (Menu) iterator.next();
//
//			ContentMenuDisplayCommand menuDisplayForm = new ContentMenuDisplayCommand();
//
//			menuDisplayForm.setMenuId(menu.getMenuId());
//			menuDisplayForm.setMenuLongDesc(menuService.formatMenuName(siteId, menu.getMenuId()));
//			menuDisplayForm.setMenuWindowMode(menu.getMenuWindowMode());
//			menuDisplayForm.setMenuWindowTarget(menu.getMenuWindowTarget());
//
//			selectedMenuVector.add(menuDisplayForm);
//		}
//
//		ContentMenuDisplayCommand selectedMenuList[] = new ContentMenuDisplayCommand[selectedMenuVector.size()];
//
//		selectedMenuVector.copyInto(selectedMenuList);
//
//		command.setSelectedMenus(selectedMenuList);
//		command.setSelectedMenusCount(selectedMenuList.length);
//
//		Section section = content.getSection();
//
//		if (section != null) {
//			command.setSelectedSection(sectionService.formatSectionName(siteId, section.getSectionId()));
//		}
//
//		command.setMenuList(menuService.makeMenuTreeList(siteId));
//		command.setSectionTree(sectionService.makeSectionTree(siteId));
//
////		iterator = content.getImages().iterator();
////
////		List<ContentImage> images = new ArrayList<ContentImage>(); 
////
////		while (iterator.hasNext()) {
////			ContentImage image = (ContentImage) iterator.next();
////
////			images.add(image);
////		}
////
////		command.setImages(images);
//
//		command.setHomePage(false);
//
//		if (getHomePage(siteId, content.getContentId()) != null) {
//			command.setHomePage(true);
//		}
//	}

	public void saveContentContributors(HttpServletRequest request, Content content) {
		Enumeration<String> names = request.getParameterNames();

		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();

			if (name.startsWith("contributorId")) {
				String userId = request.getParameter(name);

				if (StringUtils.isEmpty(userId)) {
					continue;
				}

				User user = userService.getUser(Long.valueOf(userId));

				if (null == user) {
					continue;
				}

				ContentContributor contributor = new ContentContributor();

				contributor.setApproved(true);
				contributor.setContentId(content.getContentId());
				contributor.setProfilePhotoSmall(user.getProfilePhotoSmall());
				contributor.setUserId(user.getUserId());
				contributor.setUsername(user.getUsername());
				contributor.setVersion("1.0");

				List<ContentContributor> results = contentService.getContentContributor(content.getContentId(), user.getUserId());

				if (results.isEmpty()) {
					contentService.addContentContributor(contributor);
				}
			}
		}
	}

	private HomePage getHomePage(int siteId, Long contentId) {
		List<HomePage> homePages = homePageService.getHomePages(siteId);

		for (HomePage homePage : homePages) {
			Content homePageContent = homePage.getContent();
			if (homePageContent != null) {
				if (homePageContent.getContentId().longValue() == contentId.longValue()) {
					return homePage;
				}
			}
		}

		return null;
	}

	@RequestMapping(value="/controlpanel/content/remove")
	public String removeContents(
			@RequestParam("contentIds") String contentIds,
			HttpServletRequest request) {
		String[] arrIds = contentIds.split(",");

		Content content = new Content();

		for (int i = 0; i < arrIds.length; i++) {
			content = contentService.getContent(Long.valueOf(arrIds[i]));

			contentService.deleteContent(content);
		}

		return "redirect:/controlpanel/content";
	}

	@RequestMapping(value="/controlpanel/content/search", method = RequestMethod.POST)
	public String search(
			@ModelAttribute ContentSearchForm form, Map<String, Object> model)
		throws Throwable {
		User user = AuthenticatedUtil.getAuthenticatedUser();

		int siteId = user.getLastVisitSiteId();

		List<Content> contents = contentService.search(
			siteId, form.getTitle(), form.getPublished(),
			null, null, form.getPublishDateStart(), form.getPublishDateEnd(),
			form.getExpireDateStart(), form.getExpireDateEnd());

		model.put("contents", contents);
		model.put("searchForm", form);

		return "controlpanel/content/list";
	}
}