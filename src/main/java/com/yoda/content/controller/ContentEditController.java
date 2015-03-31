package com.yoda.content.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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

import com.yoda.content.ContentEditCommand;
import com.yoda.content.ContentEditValidator;
import com.yoda.content.ContentMenuDisplayCommand;
import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.homepage.model.HomePage;
import com.yoda.homepage.service.HomePageService;
import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.menu.model.Menu;
import com.yoda.menu.service.MenuService;
import com.yoda.section.model.Section;
import com.yoda.section.service.SectionService;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.Constants;
import com.yoda.util.Format;
import com.yoda.util.StringPool;
import com.yoda.util.Utility;
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
	ContentService contentService;

	@Autowired
	SiteService siteService;

	@Autowired
	HomePageService homePageService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			@PathVariable("contentId") long contentId,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		ContentEditCommand command = new ContentEditCommand();

		Content content = contentService.getContent(user.getLastVisitSiteId(), contentId);

		copyProperties(command, content);

		createAdditionalInfo(user, content, command);

		return new ModelAndView("controlpanel/content/edit", "contentEditCommand", command);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView updateContent(
			@ModelAttribute ContentEditCommand command, BindingResult result,
			SessionStatus status,HttpServletRequest request,
			HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		int siteId = user.getLastVisitSiteId();

		new ContentEditValidator().validate(command, result);

		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/content/edit", model);
		}

		Content content = contentService.updateContent(
			command.getContentId(), siteId,
			Utility.encode(command.getTitle()),
			command.getTitle(), command.getShortDescription(),
			command.getDescription(), command.getPageTitle(),
			command.getPublishDate(), command.getExpireDate(),
			user.getUserId(), command.isPublished());

		HomePage homePage = getHomePage(user, content.getContentId());

		if (command.isHomePage()) {
			if (homePage == null) {
				homePageService.addHomePage(
					user.getLastVisitSiteId(), user.getUserId(), true, content);
			}
		}
		else {
			if (homePage != null) {
				homePageService.deleteHomePage(homePage);
			}
		}

//		Indexer.getInstance(siteId).removeContent(content);
//		Indexer.getInstance(siteId).indexContent(content);

		copyProperties(command, content);
		createAdditionalInfo(user, content, command);

//		form.setMode("U");
//		FormUtils.setFormDisplayMode(request, command, FormUtils.EDIT_MODE);


//		uploadImage(content.getContentId(), contentImage, request, response);

		model.put("success", "success");

		return new ModelAndView("controlpanel/content/edit", model);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String deleteContent(
			@ModelAttribute ContentEditCommand command,
			HttpServletRequest request) {
		User user = PortalUtil.getAuthenticatedUser();

		int siteId = user.getLastVisitSiteId();

		long contentId = command.getContentId();

		Content content = contentService.getContent(siteId, contentId);

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

		Iterator iterator = (Iterator)content.getMenus().iterator();

		while (iterator.hasNext()) {
			Menu menu = (Menu)iterator.next();
			menu.setContent(null);
		}

		contentService.deleteContent(content);

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
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

//		JSONObject jsonResult = new JSONObject();

		byte[] fileData = file.getBytes();

		if (fileData.length <= 0) {
			return "redirect:/controlpanel/content/" + contentId + "/edit";
		}

		if (Format.isNullOrEmpty(file.getName())) {
			return "redirect:/controlpanel/content/" + contentId + "/edit";
		}

		String savedPath = saveImage(request, file);

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

		Content content = contentService.updateContentImage(
			user.getLastVisitSiteId(), user.getUserId(), contentId, savedPath);

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

	private String saveImage(HttpServletRequest request, MultipartFile file) {
		String newName = "";

		String fileNameLong = file.getOriginalFilename();

		fileNameLong = fileNameLong.replace('\\', '/');

		String[] pathParts = fileNameLong.split("/");

		String fileName = pathParts[pathParts.length - 1];

		String nameWithoutExt = fileName.substring(0, fileName.lastIndexOf("."));

		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

		String currentDirPath = StringPool.BLANK;

		try {
			currentDirPath = getBaseDir(request);

			File pathToSave = new File(currentDirPath, fileName);

			int counter = 1;

			while (pathToSave.exists()) {
				newName = nameWithoutExt + "(" + counter + ")" + "." + ext;

				pathToSave = new File(currentDirPath, newName);

				fileName = newName;
				counter++;
			}

			file.transferTo(pathToSave);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return getUrlPrefix(request) + fileName;
	}

	private String getBaseDir(HttpServletRequest request) {
		User user = PortalUtil.getAuthenticatedUser();

		String prefix = ServletContextUtil.getServletContext().getRealPath("/uploads/");

		if (Validator.isNotNull(user)) {
			prefix = prefix.concat("/" + user.getUserId());
		}

		File baseFile = new File(prefix);

		if (!baseFile.exists())
			baseFile.mkdirs();

		return prefix;
	}

	private String getUrlPrefix(HttpServletRequest request) {
		User user = PortalUtil.getAuthenticatedUser();

		String urlPrefix = ServletContextUtil.getContextPath() + "/uploads/";

		if (Validator.isNotNull(user)) {
			urlPrefix = urlPrefix.concat(user.getUserId() + "/");
		}

		return urlPrefix;
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

	public void createAdditionalInfo(
			User user, Content content, ContentEditCommand command)
		throws Exception {
		int siteId = user.getLastVisitSiteId();

		Iterator iterator = content.getMenus().iterator();

		Vector<ContentMenuDisplayCommand> selectedMenuVector = new Vector<ContentMenuDisplayCommand>();

		while (iterator.hasNext()) {
			Menu menu = (Menu) iterator.next();

			ContentMenuDisplayCommand menuDisplayForm = new ContentMenuDisplayCommand();

			menuDisplayForm.setMenuId(menu.getMenuId());
			menuDisplayForm.setMenuLongDesc(menuService.formatMenuName(siteId, menu.getMenuId()));
			menuDisplayForm.setMenuWindowMode(menu.getMenuWindowMode());
			menuDisplayForm.setMenuWindowTarget(menu.getMenuWindowTarget());

			selectedMenuVector.add(menuDisplayForm);
		}

		ContentMenuDisplayCommand selectedMenuList[] = new ContentMenuDisplayCommand[selectedMenuVector.size()];

		selectedMenuVector.copyInto(selectedMenuList);

		command.setSelectedMenus(selectedMenuList);
		command.setSelectedMenusCount(selectedMenuList.length);

		Section section = content.getSection();

		if (section != null) {
			command.setSelectedSection(sectionService.formatSectionName(siteId, section.getSectionId()));
		}

		command.setMenuList(menuService.makeMenuTreeList(siteId));
		command.setSectionTree(sectionService.makeSectionTree(siteId));

//		iterator = content.getImages().iterator();
//
//		List<ContentImage> images = new ArrayList<ContentImage>(); 
//
//		while (iterator.hasNext()) {
//			ContentImage image = (ContentImage) iterator.next();
//
//			images.add(image);
//		}
//
//		command.setImages(images);

		command.setHomePage(false);

		if (getHomePage(user, content.getContentId()) != null) {
			command.setHomePage(true);
		}
	}

	private HomePage getHomePage(User user, Long contentId)
			throws Exception {
		List<HomePage> homePages = homePageService.getHomePages(user.getLastVisitSiteId());

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

	private void copyProperties(ContentEditCommand command, Content content) {
		command.setContentId(content.getContentId());
		command.setTitle(content.getTitle());
		command.setShortDescription(content.getShortDescription());
		command.setDescription(content.getDescription());
		command.setPageTitle(content.getPageTitle());
		command.setHitCounter(String.valueOf(content.getHitCounter()));
		command.setPublished(content.isPublished());
		command.setPublishDate(Format.getDate(content.getPublishDate()));
		command.setExpireDate(Format.getDate(content.getExpireDate()));
		command.setRemoveImages(null);
		command.setRemoveMenus(null);
		command.setMenuWindowMode("");
		command.setImagePath(content.getFeaturedImage());
		command.setItems(content.getItems());

//		ContentImage contentImage = content.getImage();

//		if (contentImage != null) {
//			command.setDefaultImage(contentImage);
//
////			List<ContentImage> contentImages = new ArrayList<ContentImage>(); 
////
////			contentImages.add(contentImage);
////
////			command.setImages(contentImages);
//		} else {
//			command.setDefaultImage(null);
//		}

		command.setUpdateBy(content.getUpdateBy());
		command.setUpdateDate(Format.getFullDatetime(content.getUpdateDate()));
		command.setCreateBy(content.getCreateBy());
		command.setCreateDate(Format.getFullDatetime(content.getCreateDate()));
	}
}