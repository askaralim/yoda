package com.yoda.content.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.content.ContentEditCommand;
import com.yoda.content.ContentEditValidator;
import com.yoda.content.ContentMenuDisplayCommand;
import com.yoda.content.model.Content;
import com.yoda.content.model.ContentImage;
import com.yoda.content.service.ContentService;
import com.yoda.homepage.model.HomePage;
import com.yoda.homepage.service.HomePageService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.menu.model.Menu;
import com.yoda.menu.service.MenuService;
import com.yoda.section.model.Section;
import com.yoda.section.service.SectionService;
import com.yoda.user.model.User;
import com.yoda.util.Format;

@Controller
@RequestMapping("/controlpanel/content/add")
public class ContentAddController {
	@Autowired
	MenuService menuService;

	@Autowired
	SectionService sectionService;

	@Autowired
	ContentService contentService;

	@Autowired
	HomePageService homePageService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
		HttpServletRequest request, HttpServletResponse response) {
		ContentEditCommand command = new ContentEditCommand();

		Content content = new Content();

		User user = PortalUtil.getAuthenticatedUser();

		try {
			createAdditionalInfo(user, content, command);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		command.setPublished(true);
		command.setPublishDate(Format.getDate(new Date()));
		command.setExpireDate(Format.getDate(Format.getHighDate()));

		return new ModelAndView("controlpanel/content/edit", "contentEditCommand", command);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(
			@ModelAttribute ContentEditCommand command,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {

		User user = PortalUtil.getAuthenticatedUser();

		int siteId = user.getLastVisitSiteId();

		Content content = new Content();

		new ContentEditValidator().validate(command, result);

		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			createAdditionalInfo(user, content, command);

			model.put("errors", "errors");

			return new ModelAndView("controlpanel/content/edit", model);
		}

		content = contentService.addContent(
			siteId, user.getUserId(), command.getTitle(),
			command.getTitle(), command.getShortDescription(),
			command.getDescription(), command.getPageTitle(),
			command.getPublishDate(), command.getExpireDate(),
			user.getUserId(), command.isPublished());

		HomePage homePage = getHomePage(user, content.getContentId());

		if (command.isHomePage()) {
			if (homePage == null) {
				homePageService.addHomePage(
					user.getLastVisitSiteId(), user.getUserId(), false, content);
			}
		}
		else {
			if (homePage != null) {
				homePageService.deleteHomePage(homePage);
			}
		}

//		Indexer.getInstance(siteId).removeContent(content);
//		Indexer.getInstance(siteId).indexContent(content);

//		copyProperties(command, content);
//		createAdditionalInfo(user, content, command);

//		model.put("success", "success");

		return new ModelAndView("redirect:/controlpanel/content/" + content.getContentId() + "/edit", model);
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

//		ContentImage contentImage = content.getImage();
//
//		if (contentImage != null) {
//			command.setDefaultImage(contentImage);
//		} else {
//			command.setDefaultImage(null);
//		}

		command.setUpdateBy(content.getUpdateBy());
		command.setUpdateDate(Format.getFullDatetime(content.getUpdateDate()));
		command.setCreateBy(content.getCreateBy());
		command.setCreateDate(Format.getFullDatetime(content.getCreateDate()));
	}

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

//		List<ContentImage> images = new ArrayList<ContentImage>(); 

//		while (iterator.hasNext()) {
//			ContentImage image = (ContentImage) iterator.next();

//			images.add(image);
//		}

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
}