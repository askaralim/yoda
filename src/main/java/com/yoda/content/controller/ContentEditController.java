package com.yoda.content.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
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

import com.yoda.category.model.Category;
import com.yoda.category.service.CategoryService;
import com.yoda.content.ContentEditValidator;
import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.homepage.model.HomePage;
import com.yoda.homepage.service.HomePageService;
import com.yoda.kernal.elasticsearch.ContentIndexer;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.menu.model.Menu;
import com.yoda.menu.service.MenuService;
import com.yoda.section.model.Section;
import com.yoda.section.service.SectionService;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.Constants;
import com.yoda.util.Format;
import com.yoda.util.Validator;

@Controller
@RequestMapping("/controlpanel/content/{contentId}/edit")
public class ContentEditController {
//	@Autowired
//	ContentImageService contentImageService;

	@Autowired
	UserService userService;

	@Autowired
	MenuService menuService;

	@Autowired
	SectionService sectionService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ContentService contentService;

	@Autowired
	SiteService siteService;

	@Autowired
	HomePageService homePageService;

	@RequestMapping(method = RequestMethod.GET)
	public String initUpdateForm(
			@PathVariable("contentId") long contentId,
			Map<String, Object> model, HttpServletRequest request) {
		Site site = PortalUtil.getSiteFromSession(request);

		Content content = contentService.getContent(site.getSiteId(), contentId);

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

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView updateContent(
			@ModelAttribute Content content,
			@RequestParam("categoryId") Integer categoryId,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {
		Site site = PortalUtil.getSiteFromSession(request);

		new ContentEditValidator().validate(content, result);

		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/content/edit", model);
		}

		Content contentDb = contentService.updateContent(site.getSiteId(), content, categoryId);

//		Indexer.getInstance(siteId).removeContent(content);
//		Indexer.getInstance(siteId).indexContent(content);

//		copyProperties(command, content);

//		createAdditionalInfo(user, content, content);

//		uploadImage(content.getContentId(), contentImage, request, response);

		if (getHomePage(site.getSiteId(), content.getContentId()) != null) {
			content.setHomePage(true);
		}

		List<Category> categories = categoryService.getCategories();

		model.put("categories", categories);
		model.put("content", contentDb);
		model.put("success", "success");

		new ContentIndexer().updateIndex(content);

		return new ModelAndView("controlpanel/content/edit", model);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String deleteContent(
			@ModelAttribute Content content,
			HttpServletRequest request) {
		User user = PortalUtil.getAuthenticatedUser();

		int siteId = user.getLastVisitSiteId();

		long contentId = content.getContentId();

		Content contentDb = contentService.getContent(siteId, contentId);

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
		User user = PortalUtil.getAuthenticatedUser();

		Content content = contentService.getContent(user.getLastVisitSiteId(), contentId);

		content.setHitCounter(0);
		content.setUpdateBy(user.getUserId());
		content.setUpdateDate(new Date());

		contentService.updateContent(content);

		JSONObject jsonResult = new JSONObject();

		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);
		jsonResult.put("updateBy", content.getUpdateBy());
		jsonResult.put("updateDate", Format.getFullDatetime(content.getUpdateDate()));

		String jsonString = jsonResult.toString();

		response.setContentType("text/html");
		response.setContentLength(jsonString.length());

		OutputStream outputStream = response.getOutputStream();

		outputStream.write(jsonString.getBytes());
		outputStream.flush();
	}

	@RequestMapping("/addSection")
	public void addSection(
			@PathVariable("contentId") long contentId,
			@RequestParam("sectionId") int sectionId,
			HttpServletRequest request,
			HttpServletResponse response) throws Throwable {

		User user = PortalUtil.getAuthenticatedUser();

		Content content = contentService.getContent(user.getLastVisitSiteId(), contentId);

		Section section = null;

		if (Validator.isNotNull(sectionId)) {
			section = sectionService.getSectionBySiteId_SectionId(user.getLastVisitSiteId(), sectionId);

			content.setSection(section);
			content.setUpdateBy(user.getUserId());
			content.setUpdateDate(new Date());

			contentService.updateContent(content);
		}

		JSONObject jsonResult = new JSONObject();

		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);
		jsonResult.put("selectedSection", sectionService.formatSectionName(user.getLastVisitSiteId(), section.getSectionId()));
		jsonResult.put("updateBy", content.getUpdateBy());
		jsonResult.put("updateDate", Format.getFullDatetime(content.getUpdateDate()));

		String jsonString = jsonResult.toString();

		response.setContentType("text/html");
		response.setContentLength(jsonString.length());

		OutputStream outputStream = response.getOutputStream();

		outputStream.write(jsonString.getBytes());
		outputStream.flush();
	}

	@RequestMapping("/removeSection")
	public void removeSection(
			@PathVariable("contentId") Long contentId,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		Content content = new Content();

		content = contentService.getContent(user.getLastVisitSiteId(), contentId);

		content.setSection(null);
		content.setUpdateBy(user.getUserId());
		content.setUpdateDate(new Date());

		contentService.updateContent(content);

		JSONObject jsonResult = new JSONObject();

		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);
		jsonResult.put("updateBy", content.getUpdateBy());
		jsonResult.put("updateDate", Format.getFullDatetime(content.getUpdateDate()));

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
		User user = PortalUtil.getAuthenticatedUser();

		Content content = contentService.getContent(user.getLastVisitSiteId(), contentId);

		if (menuIds != null) {
			for (int i = 0; i < menuIds.length; i++) {
				Menu menu = menuService.getMenu(user.getLastVisitSiteId(), menuIds[i]);

				menu.setContent(null);
				menu.setMenuUrl("");
				menu.setMenuType("");

				menuService.updateMenu(menu);
			}
		}

		content.setUpdateBy(user.getUserId());
		content.setUpdateDate(new Date());

		contentService.updateContent(content);

		JSONObject jsonResult = createJsonSelectedMenus(user.getLastVisitSiteId(), content);

		jsonResult.put("updateBy", content.getUpdateBy());
		jsonResult.put("updateDate", Format.getFullDatetime(content.getUpdateDate()));

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
		User user = PortalUtil.getAuthenticatedUser();

		Content content = contentService.getContent(user.getLastVisitSiteId(), contentId);

		if (addMenus != null) {
			for (int i = 0; i < addMenus.length; i++) {
				menuService.updateMenu(
					user.getLastVisitSiteId(), addMenus[i], content, null, null, "",
					menuWindowMode, menuWindowTarget, Constants.MENU_CONTENT);
			}
		}

		content.setUpdateBy(user.getUserId());
		content.setUpdateDate(new Date());

		contentService.updateContent(content);

		JSONObject jsonResult = createJsonSelectedMenus(user.getLastVisitSiteId(), content);

		jsonResult.put("updateBy", content.getUpdateBy());
		jsonResult.put("updateDate", Format.getFullDatetime(content.getUpdateDate()));

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
		User user = PortalUtil.getAuthenticatedUser();

		Site site = PortalUtil.getSiteFromSession(request);

		if (file.getBytes().length <= 0) {
			return "redirect:/controlpanel/content/" + contentId + "/edit";
		}

		if (Format.isNullOrEmpty(file.getName())) {
			return "redirect:/controlpanel/content/" + contentId + "/edit";
		}

		contentService.updateContentImage(
			site.getSiteId(), user.getUserId(), contentId, file);

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

	private HomePage getHomePage(int siteId, Long contentId) {
		List<HomePage> homePages = homePageService.getHomePages(siteId);

		for (HomePage homePage : homePages) {
			Content homePageContent = homePage.getContent();
			if (homePageContent != null) {
				if (homePageContent.getContentId() == contentId) {
					return homePage;
				}
			}
		}

		return null;
	}
}