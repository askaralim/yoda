package com.yoda.homepage.controller;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.content.model.Content;
import com.yoda.homepage.HomePageDisplayCommand;
import com.yoda.homepage.HomePageEditCommand;
import com.yoda.homepage.model.HomePage;
import com.yoda.homepage.service.HomePageService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.section.service.SectionService;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.Format;

@Controller
public class HomePageController {

	@Autowired
	UserService userService;

	@Autowired
	SectionService sectionService;

	@Autowired
	SiteService siteService;

	@Autowired
	HomePageService homePageService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	@RequestMapping(value="/controlpanel/homepage", method = RequestMethod.GET)
	public ModelAndView setupForm(
		HttpServletRequest request, HttpServletResponse response) {

		HomePageEditCommand command = new HomePageEditCommand();

		User user = PortalUtil.getAuthenticatedUser();

//		Site site = siteService.getSite(user.getLastVisitSiteId());

//		String pageTitle = Utility.getParam(site, Constants.HOME_TITLE);
//
//		command.setPageTitle(pageTitle);

		try {
			initListInfo(command, user.getLastVisitSiteId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("controlpanel/homepage/edit", "homePageEditCommand", command);
	}

	@RequestMapping(value = "/controlpanel/homepage/save", method = RequestMethod.POST)
	public String processSubmit(
			@ModelAttribute HomePageEditCommand command,
			HttpServletRequest request)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		Site site = siteService.getSite(user.getLastVisitSiteId());

//		Iterator iterator = site.getSiteParams().iterator();
//
//		while (iterator.hasNext()) {
//			SiteParam siteParam = (SiteParam) iterator.next();
//
//			if (!siteParam.getSiteParamName().startsWith(Constants.HOME_TITLE)) {
//				continue;
//			}
//
//			siteParamService.deleteSiteParam(siteParam);
//		}

//		SiteParam siteParam = siteParamService.addSiteParam(
//			Constants.HOME_TITLE, command.getPageTitle(), admin.getUserId());

//		site.getSiteParams().add(siteParam);

		siteService.updateSite(site);

//		SiteCache.removeSite(site.getSiteId());

		initListInfo(command, site.getSiteId());

		return "controlpanel/homepage/edit";
	}

	@RequestMapping(value = "/controlpanel/homepage/resequence", method = RequestMethod.POST)
	public String resequence(
			@ModelAttribute HomePageEditCommand command,
			HttpServletRequest request)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		int siteId = user.getLastVisitSiteId();

		String seqNums[] = command.getSeqNums();
		long ids[] = command.getIds();

		for (int i = 0; i < seqNums.length; i++) {
			int seqNum = Format.getInt(seqNums[i]);

			long id = ids[i];

			HomePage homePage = new HomePage();

			homePage = homePageService.getHomePage(siteId, id);

			homePage.setSeqNum(seqNum);

			homePageService.updateHomePage(homePage);
		}

		initListInfo(command, siteId);

		return "controlpanel/homepage/edit";
	}

	@RequestMapping(value = "/controlpanel/homepage/makefeature", method = RequestMethod.POST)
	public String makeFeature(
			@ModelAttribute HomePageEditCommand command,
			HttpServletRequest request)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		int siteId = user.getLastVisitSiteId();

		String featureData = command.getFeatureData();

		if (featureData != null) {
			HomePage homePage = homePageService.getHomePage(siteId, Format.getLong(featureData));

			List<HomePage> featureHomePages = homePageService.getHomePagesBySiteIdAndFeatureData(siteId);

			Iterator iterator = featureHomePages.iterator();

			HomePage featureHomePage = null;

			if (iterator.hasNext()) {
				featureHomePage = (HomePage) iterator.next();
			}

			if (featureHomePage != null) {
				if (featureHomePage.getId() != homePage.getId()) {
					featureHomePage.setFeatureData(false);

					homePageService.updateHomePage(featureHomePage);
				}
			}

			homePage.setFeatureData(true);

			homePageService.updateHomePage(homePage);
		}

		initListInfo(command, siteId);

		return "controlpanel/homepage/edit";
	}

	private void initListInfo(HomePageEditCommand command, int siteId)
			throws Exception {

		List<HomePage> homePages = homePageService.getHomePages(siteId, "seqNum");

		Vector<HomePageDisplayCommand> vector = new Vector<HomePageDisplayCommand>();

		for (HomePage homePage : homePages) {

			HomePageDisplayCommand homePageDisplayCommand = new HomePageDisplayCommand();

			homePageDisplayCommand.setHomePageId(homePage.getId());
			homePageDisplayCommand.setSeqNum(Format.getInt(homePage.getSeqNum()));

			if (homePage.getFeatureData()) {
				homePageDisplayCommand.setFeatureData(homePage.getId());
			}

			if (homePage.getContent() != null) {
				Content content = homePage.getContent();

				homePageDisplayCommand.setDataType("Content");
				homePageDisplayCommand.setDescription(content.getTitle());
				homePageDisplayCommand.setSectionName("");

				if (content.getSection() != null) {
					homePageDisplayCommand.setSectionName(
						sectionService.formatSectionName(siteId, content.getSection().getSectionId()));
				}

				homePageDisplayCommand.setPublished(content.isPublished());
				homePageDisplayCommand.setDataPublishOn(Format.getFullDate(content.getPublishDate()));
				homePageDisplayCommand.setDataExpireOn(Format.getFullDate(content.getExpireDate()));
			}

//			if (homePage.getItem() != null) {
//				Item item = homePage.getItem();
//
//				homePageDisplayCommand.setDataType("Item");
//				homePageDisplayCommand.setDescription(item.getItemShortDesc() + " " + item.getItemShortDesc1());
//				homePageDisplayCommand.setSectionName("");
//
//				if (item.getSection() != null) {
//					homePageDisplayCommand.setSectionName(
//						sectionService.formatSectionName(siteId, item.getSection().getSectionId()));
//				}
//
//				homePageDisplayCommand.setPublished(String.valueOf(item.getPublished()));
//				homePageDisplayCommand.setDataPublishOn(Format.getFullDate(item.getItemPublishOn()));
//				homePageDisplayCommand.setDataExpireOn(Format.getFullDate(item.getItemExpireOn()));
//			}

			vector.add(homePageDisplayCommand);
		}

		command.setHomePages(vector);
	}
}