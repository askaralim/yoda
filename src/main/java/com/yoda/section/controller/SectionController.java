package com.yoda.section.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.section.SectionDisplayCommand;
import com.yoda.section.SectionEditCommand;
import com.yoda.section.SectionResequenceValidator;
import com.yoda.section.SectionSaveValidator;
import com.yoda.section.exception.SectionShortTitleException;
import com.yoda.section.model.Section;
import com.yoda.section.service.SectionService;
import com.yoda.user.model.User;
import com.yoda.util.Constants;
import com.yoda.util.Format;
import com.yoda.util.StringPool;

@Controller
public class SectionController {
	@Autowired
	SectionService sectionService;

	@RequestMapping(value = "/controlpanel/section/create", method = RequestMethod.POST)
	public String create(
			@ModelAttribute SectionEditCommand sectionEditCommand,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		int siteId = user.getLastVisitSiteId();

		Section referenceSection = sectionService.getSectionBySiteId_SectionId(siteId, sectionEditCommand.getCreateSectionId());
//		Section referenceSection =  SectionDAO.load(siteId, Format.getLong(form.getCreateSectionId()));

		int sectionParentId = 0;

		int seqNum = 0;

		String sql = "";

		Query query = null;

		if (sectionEditCommand.getCreateMode().equals("C")) { // append child node
			sectionParentId = referenceSection.getSectionId();

			int sectionSeqNum = sectionService.selectMaxSeqNumBySectionId_SiteId(siteId, referenceSection.getSectionId());

			if (sectionSeqNum == 0) {
				seqNum = 0;
			}
			else {
				seqNum = sectionSeqNum + 1;
			}
		}
		else if (sectionEditCommand.getCreateMode().equals("B")) { // before current
			sectionParentId = referenceSection.getParentId();

			seqNum = referenceSection.getSeqNum();

			sectionService.updateSeqNum(siteId, referenceSection.getParentId(), referenceSection.getSeqNum());
		}
		else if (sectionEditCommand.getCreateMode().equals("A")) { // after current
			sectionParentId = referenceSection.getParentId();

			seqNum = referenceSection.getSeqNum() + 1;

			sectionService.updateSeqNum(siteId, referenceSection.getParentId(), referenceSection.getSeqNum());
		}

		Section section = sectionService.addSection(
			siteId, user.getUserId(), sectionParentId, seqNum, "New section",
			StringPool.BLANK, StringPool.BLANK, true);

		sectionEditCommand.setSectionId(section.getSectionId());
		sectionEditCommand.setSectionParentId(sectionParentId);
		sectionEditCommand.setSectionTitle("");
		sectionEditCommand.setSectionShortTitle(section.getShortTitle());
		sectionEditCommand.setSectionDesc("");
		sectionEditCommand.setPublished(section.isPublished());
		sectionEditCommand.setMode(Constants.MODE_UPDATE);
		sectionEditCommand.setSectionTree(sectionService.makeSectionTree(siteId));
		sectionEditCommand.setPublished(true);
		sectionEditCommand.setSequence(false);
		sectionEditCommand.setMode(Constants.MODE_CREATE);

		return "controlpanel/section/edit";
	}

	@RequestMapping(value="/controlpanel/section/edit", method = RequestMethod.GET)
	public ModelAndView showPanel(
		HttpServletRequest request, HttpServletResponse response) {
//		String loginMessage = AdminLookup.lookUpAdmin(request, response);
//
//		if (Validator.isNotNull(loginMessage)) {
//			ModelMap modelMap = new ModelMap();
//
//			modelMap.put("loginMessage", loginMessage);
//
//			return new ModelAndView(
//				"redirect:" + Constants.LOGIN_PAGE_URL, modelMap);
//		}

		User user = PortalUtil.getAuthenticatedUser();

		SectionEditCommand command = new SectionEditCommand();

		try {
			command.setSectionTree(sectionService.makeSectionTree(user.getLastVisitSiteId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String error = request.getParameter("error");

//		if (error != null && error.equals("E01")) {
//			ActionMessages errors = new ActionMessages();
//			errors.add("sectionId", new ActionMessage("error.sectionId.required"));
//			saveMessages(request, errors);
//		}

		command.setMode("");

		return new ModelAndView(
			"controlpanel/section/edit", "sectionEditCommand", command);
	}

	@RequestMapping(value = "/controlpanel/section/save", method = RequestMethod.POST)
	public String saveSection(
			@ModelAttribute SectionEditCommand sectionEditCommand,
			BindingResult result, SessionStatus status, 
			HttpServletRequest request, HttpServletResponse response) {
		User user = PortalUtil.getAuthenticatedUser();

		new SectionSaveValidator().validate(sectionEditCommand, result);

		if(result.hasErrors()) {
			sectionEditCommand.setSectionTree(sectionService.makeSectionTree(user.getLastVisitSiteId()));

			return "controlpanel/section/edit";
		}

		try {
			sectionService.addSection(
				user.getLastVisitSiteId(), user.getUserId(), sectionEditCommand.getSectionId(),
				sectionEditCommand.getSectionTitle(),
				sectionEditCommand.getSectionShortTitle(),
				sectionEditCommand.getSectionDesc(),
				sectionEditCommand.isPublished());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SectionShortTitleException e) {
			sectionEditCommand.setSectionTree(sectionService.makeSectionTree(user.getLastVisitSiteId()));

			result.rejectValue("sectionShortTitle", "error.section.naturalkey.toolong");

			return "controlpanel/section/edit";
		}

		sectionEditCommand.setSectionTree(sectionService.makeSectionTree(user.getLastVisitSiteId()));

		return "controlpanel/section/edit";
	}

	@RequestMapping(value = "/controlpanel/section/remove", method = RequestMethod.POST)
	public String remove(
			@ModelAttribute SectionEditCommand sectionEditCommand,
			HttpServletRequest request) throws Exception {
		User user = PortalUtil.getAuthenticatedUser();

		sectionService.cascadeRemoveSection(
			user.getLastVisitSiteId(), sectionEditCommand.getSectionId());

		sectionEditCommand.setSectionTree(sectionService.makeSectionTree(user.getLastVisitSiteId()));
		sectionEditCommand.setMode("");

		return "controlpanel/section/edit";
	}

	@RequestMapping(value = "/controlpanel/section/removeselected", method = RequestMethod.POST)
	public String removeSelected(
			@ModelAttribute SectionEditCommand sectionEditCommand,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		SectionDisplayCommand childrenSections[] = sectionEditCommand.getChildrenSections();

		for (int i = 0; i < childrenSections.length; i++) {
			if (childrenSections[i].isRemove()) {
				int sectionId = childrenSections[i].getSectionId();

				sectionService.cascadeRemoveSection(sectionId, user.getLastVisitSiteId());
			}
		}

		sectionEditCommand.setSectionTree(sectionService.makeSectionTree(user.getLastVisitSiteId()));

		initListInfo(sectionEditCommand, user.getLastVisitSiteId());

		sectionEditCommand.setSequence(true);

		return "controlpanel/section/edit";
	}

	@RequestMapping(value = "/controlpanel/section/resequence", method = RequestMethod.POST)
	public String resequence(
			@ModelAttribute SectionEditCommand sectionEditCommand,
			BindingResult result, SessionStatus status, 
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		new SectionResequenceValidator().validate(sectionEditCommand, result);

		if(result.hasErrors()) {
			sectionEditCommand.setSectionTree(sectionService.makeSectionTree(user.getLastVisitSiteId()));
			sectionEditCommand.setSequence(true);

			return "controlpanel/section/edit";
		}

		SectionDisplayCommand childSections[] = sectionEditCommand.getChildrenSections();

		for (int i = 0; i < childSections.length; i++) {
			int seqNum = Format.getInt(childSections[i].getSeqNum());

			int sectionId = childSections[i].getSectionId();

			Section section = sectionService.getSectionBySiteId_SectionId(user.getLastVisitSiteId(), sectionId);

			section.setSeqNum(seqNum);

			sectionService.updateSection(section);
		}

		sectionEditCommand.setSectionTree(sectionService.makeSectionTree(user.getLastVisitSiteId()));
		sectionEditCommand.setSequence(true);

		return "controlpanel/section/edit";
	}

	@RequestMapping(value = "/controlpanel/section/showsequence", method = RequestMethod.POST)
	public String showSequence(
			@RequestParam(value = "sectionId") int sectionId,
			@ModelAttribute SectionEditCommand command, BindingResult result,
			SessionStatus status, 
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		command.setSectionTree(sectionService.makeSectionTree(user.getLastVisitSiteId()));

		Section section = sectionService.getSectionBySiteId_SectionId(user.getLastVisitSiteId(), sectionId);

		command.setSectionId(sectionId);

		if (section.getParentId() != 0) {
			command.setSectionParentId(section.getParentId());
		}

		command.setSectionTitle(section.getTitle());
		command.setSectionShortTitle(section.getShortTitle());
		command.setSectionDesc(section.getDescription());
		command.setPublished(section.isPublished());
		command.setMode(Constants.MODE_UPDATE);
		command.setSequence(true);

		initListInfo(command, user.getLastVisitSiteId());

		return "controlpanel/section/edit";
	}

	@RequestMapping(value = "/controlpanel/section/update/{sectionId}", method = RequestMethod.GET)
	public ModelAndView updateMenu(
			@PathVariable("sectionId") int sectionId,
			@ModelAttribute SectionEditCommand command,
			HttpServletRequest request)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		command.setSectionTree(sectionService.makeSectionTree(user.getLastVisitSiteId()));

		Section section = sectionService.getSectionBySiteId_SectionId(user.getLastVisitSiteId(), sectionId);

		command.setSectionId(sectionId);

		if (section.getParentId() != 0) {
			command.setSectionParentId(section.getParentId());
		}

		command.setSectionTitle(section.getTitle());
		command.setSectionShortTitle(section.getShortTitle());
		command.setSectionDesc(section.getDescription());
		command.setPublished(section.isPublished());
		command.setMode(Constants.MODE_UPDATE);
		command.setSequence(false);

		return new ModelAndView("controlpanel/section/edit", "sectionEditCommand", command);
	}

	protected void initListInfo(SectionEditCommand command, int siteId)
			throws Exception {
		List<Section> sections = sectionService.getSectionBySiteId_SectionParentId(siteId, command.getSectionId());

		Vector<SectionDisplayCommand> vector = new Vector<SectionDisplayCommand>();

		for (Section childSection : sections) {
			SectionDisplayCommand display = new SectionDisplayCommand();

			display.setSectionId(childSection.getSectionId());
			display.setSeqNum(Format.getInt(childSection.getSeqNum()));
			display.setSectionShortTitle(childSection.getShortTitle());
			display.setSectionTitle(childSection.getTitle());
			display.setSectionDesc(childSection.getDescription());
			display.setPublished(childSection.isPublished());

			vector.add(display);
		}

		SectionDisplayCommand childrenSections[] = new SectionDisplayCommand[vector.size()];

		vector.copyInto(childrenSections);

		command.setChildrenSections(childrenSections);
	}
}