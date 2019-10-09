package com.taklip.yoda.controller;

import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.model.*;
import com.taklip.yoda.service.*;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.tool.StringPool;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.DateUtil;
import com.taklip.yoda.util.SiteUtil;
import com.taklip.yoda.validator.ContentEditValidator;
import com.taklip.yoda.vo.ContentSearchForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping(value = "/controlpanel/content")
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

	@GetMapping
	public String showPanel(
			Map<String, Object> model,
			@RequestParam(name = "offset", required = false) String offset) {
//		String offset = request.getParameter("offset");

		int offsetInt = 0;

		if (!StringUtils.isEmpty(offset)) {
			offsetInt = Integer.valueOf(offset) * 10;
		}

		Pagination<Content> page = contentService.getContents(
				SiteUtil.getDefaultSite().getSiteId(), new RowBounds(offsetInt, 10));

		model.put("page", page);
		model.put("searchForm", new ContentSearchForm());

		return "controlpanel/content/list";
	}

//	@RequestMapping(value = "/controlpanel/content/add", method = RequestMethod.GET)
	@GetMapping("/add")
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

	@PostMapping(value = "/save")
	public ModelAndView save(
			@ModelAttribute Content content, @RequestParam("categoryId") Long categoryId,
			BindingResult result, HttpServletRequest request) throws Throwable {
		new ContentEditValidator().validate(content, result);

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			List<Category> categories = categoryService.getCategories();

			model.put("categories", categories);
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/content/edit", model);
		}

		contentService.saveContent(SiteUtil.getDefaultSite().getSiteId(), content, categoryId);

		saveContentContributors(request, content);

		model.put("content", content);
		model.put("success", "success");

		return new ModelAndView("redirect:/controlpanel/content/" + content.getContentId() + "/edit", model);
	}

//	@RequestMapping(value = "/controlpanel/content/add", method = RequestMethod.POST)
//	public ModelAndView processSubmit(
//			@ModelAttribute Content content,
//			@RequestParam("categoryId") Integer categoryId,
//			BindingResult result, SessionStatus status,
//			HttpServletRequest request)
//		throws Throwable {
//		new ContentEditValidator().validate(content, result);
//
//		ModelMap model = new ModelMap();
//
//		if(result.hasErrors()) {
//			model.put("errors", "errors");
//
//			List<Category> categories = categoryService.getCategories();
//
//			model.put("categories", categories);
//
//			return new ModelAndView("controlpanel/content/edit", model);
//		}
//
//		contentService.addContent(SiteUtil.getDefaultSite().getSiteId(), content, categoryId);
//
//		return new ModelAndView("redirect:/controlpanel/content/" + content.getContentId() + "/edit", model);
//	}
//
//	@RequestMapping(value = "/controlpanel/content/{contentId}/edit", method = RequestMethod.POST)
//	public ModelAndView updateContent(
//			@ModelAttribute Content content,
//			@RequestParam("categoryId") Integer categoryId,
//			BindingResult result, SessionStatus status,
//			HttpServletRequest request)
//		throws Throwable {
//		new ContentEditValidator().validate(content, result);
//
//		ModelMap model = new ModelMap();
//
//		if(result.hasErrors()) {
//			model.put("errors", "errors");
//
//			return new ModelAndView("controlpanel/content/edit", model);
//		}
//
//		Content contentDb = contentService.updateContent(SiteUtil.getDefaultSite().getSiteId(), content, categoryId);
//
//		saveContentContributors(request, contentDb);
//
//		if (getHomePage(SiteUtil.getDefaultSite().getSiteId(), content.getContentId()) != null) {
//			contentDb.setHomePage(true);
//		}
//
//		List<Category> categories = categoryService.getCategories();
//
//		model.put("categories", categories);
//		model.put("content", contentDb);
//		model.put("success", "success");
//
//		new ContentIndexer().updateIndex(content);
//
//		return new ModelAndView("controlpanel/content/edit", model);
//	}

	@GetMapping("/{contentId}/edit")
	public String initUpdateForm(
			@PathVariable("contentId") Long contentId,
			Map<String, Object> model) {
		Content content = contentService.getContent(contentId);

		List<Category> categories = categoryService.getCategories();

		model.put("categories", categories);
		model.put("content", content);

		return "controlpanel/content/edit";
	}

	@GetMapping("/{contentId}/brand/add")
	public ModelAndView initCreationForm(
			@PathVariable("contentId") Long contentId, Map<String, Object> model) {
		ContentBrand contentBrand = new ContentBrand();

		contentBrand.setContentId(contentId);

		List<Brand> brands = brandService.getBrands();

		model.put("contentBrand", contentBrand);
		model.put("brands", brands);

		return new ModelAndView("controlpanel/content/editContentBrand", model);
	}

	@RequestMapping(value = "/{contentId}/brand/{contentBrandId}/edit", method = RequestMethod.GET)
	public String initContentBrandUpdateForm(@PathVariable("contentBrandId") Long contentBrandId, Map<String, Object> model) {
		ContentBrand contentBrand = contentService.getContentBrand(contentBrandId);

		List<Brand> brands = brandService.getBrands();

		model.put("contentBrand", contentBrand);
		model.put("brands", brands);

		return "controlpanel/content/editContentBrand";
	}

	@PostMapping(value = "/{contentId}/brand/save")
	public ModelAndView processCreationForm(
			@ModelAttribute("contentBrand") ContentBrand contentBrand) {
		ModelMap model = new ModelMap();

		String brandName = StringPool.BLANK;
		String brandLogo = StringPool.BLANK;

		Brand brand = null;

		if (null != contentBrand.getBrandId()) {
			brand = brandService.getBrand(contentBrand.getBrandId());
			brandName = brand.getName();
			brandLogo = brand.getImagePath();
		}

		contentBrand.setBrandName(brandName);
		contentBrand.setBrandLogo(brandLogo);

//		contentService.addContentBrand(contentBrand);

		contentService.saveContentBrand(contentBrand);

		List<Brand> brands = brandService.getBrands();

		model.put("contentBrand", contentBrand);
		model.put("brands", brands);
		model.put("success", "success");

		return new ModelAndView("controlpanel/content/editContentBrand", model);

//		return new ModelAndView("redirect:/controlpanel/content/" + contentBrand.getContentId() + "/brand/" + contentBrand.getContentBrandId() + "/edit", model);
	}

//	@RequestMapping(value = "/{contentId}/brand/{contentBrandId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
//	public ModelAndView processUpdateForm(
//			@ModelAttribute("contentBrand") ContentBrand contentBrand,
//			@RequestParam("brandId") Long brandId,
//			BindingResult result, SessionStatus status) {
//		ModelMap model = new ModelMap();
//
//		String brandName = StringPool.BLANK;
//		String brandLogo = StringPool.BLANK;
//		Brand brand = null;
//
//		if (null != brandId) {
//			brand = brandService.getBrand(brandId);
//			brandName = brand.getName();
//			brandLogo = brand.getImagePath();
//		}
//
//		contentBrand.setBrandName(brandName);
//		contentBrand.setBrandLogo(brandLogo);
//
//		contentService.updateContentBrand(contentBrand);
//
//		List<Brand> brands = brandService.getBrands();
//
//		model.put("contentBrand", contentBrand);
//		model.put("brands", brands);
//		model.put("success", "success");
//
//		status.setComplete();
//
//		return new ModelAndView("controlpanel/content/editContentBrand", model);
//	}

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

		Iterator iterator = (Iterator) contentDb.getMenus().iterator();

		while (iterator.hasNext()) {
			Menu menu = (Menu) iterator.next();
			menu.setContent(null);
		}

		contentService.deleteContent(contentDb);

//		Indexer.getInstance(siteId).removeContent(content);

		return "redirect:/controlpanel/content/list";
	}

	@RequestMapping(value = "/controlpanel/content/resetCounter")
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

		JSONObject jsonResult = createJsonSelectedMenus(SiteUtil.getDefaultSite().getSiteId(), content);

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
						SiteUtil.getDefaultSite().getSiteId(), addMenus[i], content, null, "",
						menuWindowMode, menuWindowTarget, Constants.MENU_CONTENT);
			}
		}

		content.setUpdateBy(user);
		content.setUpdateDate(new Date());

		contentService.updateContent(content);

		JSONObject jsonResult = createJsonSelectedMenus(SiteUtil.getDefaultSite().getSiteId(), content);

		jsonResult.put("updateBy", content.getUpdateBy());
		jsonResult.put("updateDate", DateUtil.getFullDatetime(content.getUpdateDate()));

		String jsonString = jsonResult.toString();

		response.setContentType("text/html");
		response.setContentLength(jsonString.length());

		OutputStream outputStream = response.getOutputStream();

		outputStream.write(jsonString.getBytes());
		outputStream.flush();
	}

	@RequestMapping(value = "/controlpanel/content/{contentId}/uploadImage", method = RequestMethod.POST)
	public String uploadImage(
			@RequestParam("file") MultipartFile file,
			@PathVariable("contentId") long contentId,
			HttpServletRequest request)
			throws Throwable {
//		User user = PortalUtil.getAuthenticatedUser();


		if (file.getBytes().length <= 0) {
			return "redirect:/controlpanel/content/" + contentId + "/edit";
		}

		if (StringUtils.isEmpty(file.getName())) {
			return "redirect:/controlpanel/content/" + contentId + "/edit";
		}

		contentService.updateContentImage(
				SiteUtil.getDefaultSite().getSiteId(), contentId, file);

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

	@RequestMapping(value = "/controlpanel/content/remove")
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

	@RequestMapping(value = "/controlpanel/content/search", method = RequestMethod.POST)
	public String search(
			@ModelAttribute ContentSearchForm form, Map<String, Object> model)
			throws Throwable {
		List<Content> contents = contentService.search(
				SiteUtil.getDefaultSite().getSiteId(), form.getTitle(), form.getPublished(),
				null, null, form.getPublishDateStart(), form.getPublishDateEnd(),
				form.getExpireDateStart(), form.getExpireDateEnd());

		model.put("contents", contents);
		model.put("searchForm", form);

		return "controlpanel/content/list";
	}
}