// package com.taklip.yoda.controller;

// import java.util.List;
// import java.util.Vector;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.ModelMap;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.support.SessionStatus;
// import org.springframework.web.servlet.ModelAndView;

// import com.taklip.yoda.model.ContactUs;
// import com.taklip.yoda.model.Site;
// import com.taklip.yoda.model.User;
// import com.taklip.yoda.service.ContactUsService;
// import com.taklip.yoda.service.SiteService;
// import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
// import com.taklip.yoda.common.contant.Constants;
// import com.taklip.yoda.common.util.AuthenticatedUtil;
// import com.taklip.yoda.common.util.SiteUtil;
// import com.taklip.yoda.vo.ContactUsDisplayCommand;
// import com.taklip.yoda.vo.ContactUsEditCommand;
// import com.taklip.yoda.vo.ContactUsListCommand;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.validation.Valid;

// @Controller
// public class ContactUsController {
//     @Autowired
//     ContactUsService contactUsService;

//     @Autowired
//     SiteService siteService;

//     @GetMapping("/controlpanel/contactus/list")
//     public ModelAndView showPanel() {
//         Page<ContactUs> contactUsList = contactUsService.getByPage(0, 10);

//         return new ModelAndView(
//                 "controlpanel/contactus/list", "contactUsList", contactUsList);
//     }

//     @GetMapping("/controlpanel/contactus/add")
//     public ModelAndView setupForm() {
//         ContactUsEditCommand command = new ContactUsEditCommand();

//         command.setActive(true);

//         return new ModelAndView(
//                 "controlpanel/contactus/edit", "contactUsEditCommand", command);
//     }

//     @PostMapping("/controlpanel/contactus/add")
//     public ModelAndView add(
//             @Valid @ModelAttribute ContactUsEditCommand command,
//             HttpServletRequest request)
//             throws Throwable {

//         User user = AuthenticatedUtil.getAuthenticatedUser();

//         ModelMap model = new ModelMap();

//         ContactUs contactUs = contactUsService.addContactUs(
//                 user.getLastVisitSiteId(), command.isActive(),
//                 command.getContactUsName(), command.getContactUsEmail(),
//                 command.getContactUsPhone(), command.getContactUsAddressLine1(),
//                 command.getContactUsAddressLine2(), command.getContactUsCityName(),
//                 command.getContactUsZipCode(), command.getSeqNum(),
//                 command.getContactUsDesc());

//         command.setContactUsId(contactUs.getId());

//         return new ModelAndView("redirect:/controlpanel/contactus/" + contactUs.getId() + "/edit", model);
//     }

//     @GetMapping("/controlpanel/contactus/{contactUsId}/edit")
//     public ModelAndView showPanel(
//             @PathVariable long contactUsId) {
//         ContactUsEditCommand command = new ContactUsEditCommand();

//         ContactUs contactUs = new ContactUs();

//         try {
//             contactUs = contactUsService.getContactUsById(contactUsId);
//         } catch (SecurityException e) {
//             e.printStackTrace();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//         copyProperties(command, contactUs);

//         return new ModelAndView(
//                 "controlpanel/contactus/edit", "contactUsEditCommand", command);
//     }

//     @PostMapping("/controlpanel/contactus/{contactUsId}/edit")
//     public ModelAndView update(
//             @Valid @ModelAttribute ContactUsEditCommand command,
//             BindingResult result, SessionStatus status,
//             HttpServletRequest request)
//             throws Throwable {

//         User user = AuthenticatedUtil.getAuthenticatedUser();

//         ModelMap model = new ModelMap();

//         ContactUs contactUs = contactUsService.updateContactUs(
//                 command.getContactUsId(),
//                 user.getLastVisitSiteId(), command.isActive(),
//                 command.getContactUsName(), command.getContactUsEmail(),
//                 command.getContactUsPhone(), command.getContactUsAddressLine1(),
//                 command.getContactUsAddressLine2(), command.getContactUsCityName(),
//                 command.getContactUsZipCode(), command.getSeqNum(),
//                 command.getContactUsDesc());

//         command.setContactUsId(contactUs.getId());

//         model.put("success", "success");

//         return new ModelAndView("controlpanel/contactus/edit", model);
//     }

//     private void copyProperties(
//             ContactUsEditCommand command, ContactUs contactUs) {
//         command.setContactUsId(contactUs.getId());
//         command.setContactUsName(contactUs.getName());
//         command.setContactUsEmail(contactUs.getEmail());
//         command.setContactUsAddressLine1(contactUs.getAddressLine1());
//         command.setContactUsAddressLine2(contactUs.getAddressLine2());
//         command.setContactUsCityName(contactUs.getCityName());
//         command.setContactUsZipCode(contactUs.getZipCode());
//         command.setContactUsPhone(contactUs.getPhone());
//         command.setContactUsDesc(contactUs.getDescription());
//         command.setActive(contactUs.isActive());
//     }

//     @GetMapping("/controlpanel/contactus/list/resequence")
//     public String resequence(
//             @ModelAttribute ContactUsListCommand command,
//             HttpServletRequest request, HttpServletResponse response)
//             throws Throwable {
//         ContactUsDisplayCommand contactUsCommands[] = command.getContactUsCommands();

//         for (int i = 0; i < contactUsCommands.length; i++) {
//             int seqNum = Integer.valueOf(contactUsCommands[i].getSeqNum());
//             ContactUs contactUs = contactUsService.getContactUsById(contactUsCommands[i].getContactUsId());

//             contactUs.setSeqNum(seqNum);

//             contactUsService.update(contactUs);
//         }

//         return "/controlpanel/contactus/list";
//     }

//     @GetMapping("/controlpanel/contactus/list/remove")
//     public String removeContactUs(
//             @RequestParam String contactUsIds,
//             HttpServletRequest request) {
//         String[] arrIds = contactUsIds.split(",");

//         for (int i = 0; i < arrIds.length; i++) {
//             try {
//                 contactUsService.deleteContactUs(Integer.valueOf(arrIds[i]));
//             } catch (NumberFormatException e) {
//                 e.printStackTrace();
//             } catch (SecurityException e) {
//                 e.printStackTrace();
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         }

//         return "redirect:/controlpanel/contactus/list";
//     }

//     @PostMapping("/controlpanel/contactus/list/search")
//     public String search(
//             @PathVariable long contactUsId,
//             @ModelAttribute ContactUsListCommand command,
//             BindingResult result, SessionStatus status,
//             HttpServletRequest request) {

//         command.setSrPageNo(1);

//         User user = AuthenticatedUtil.getAuthenticatedUser();

//         extract(command, user);

//         command.setEmpty(false);

//         return "controlpanel/contactus/list";
//     }

//     @PostMapping("/controlpanel/contactus/list/{srPageNo}")
//     public String list(
//             @PathVariable int srPageNo,
//             @ModelAttribute ContactUsListCommand command,
//             BindingResult result, SessionStatus status,
//             HttpServletRequest request) {

//         User user = AuthenticatedUtil.getAuthenticatedUser();

//         extract(command, user);

//         return "controlpanel/contactus/list";
//     }

//     public void extract(ContactUsListCommand command, User user) {
//         Site site = siteService.getSite(user.getLastVisitSiteId());

//         if (command.getSrPageNo() == 0) {
//             command.setSrPageNo(1);
//         }

//         Boolean active = null;

//         if (command.getSrActive().equals(Constants.ACTIVE_YES)) {
//             active = true;
//         } else if (command.getSrActive().equals(Constants.ACTIVE_NO)) {
//             active = false;
//         }

//         List<ContactUs> list = contactUsService.search(user.getLastVisitSiteId(), command.getSrContactUsName(), active);

//         calcPage(user, command, list, command.getSrPageNo());

//         Vector<ContactUsDisplayCommand> vector = new Vector<ContactUsDisplayCommand>();

//         int startRecord = (command.getPageNo() - 1) * Integer.valueOf(site.getListingPageSize());

//         int endRecord = startRecord + Integer.valueOf(site.getListingPageSize());

//         for (int i = startRecord; i < list.size() && i < endRecord; i++) {
//             ContactUs contactUs = list.get(i);

//             ContactUsDisplayCommand contactUsDisplay = new ContactUsDisplayCommand();

//             contactUsDisplay.setContactUsId(contactUs.getId());
//             contactUsDisplay.setContactUsName(contactUs.getName());
//             contactUsDisplay.setContactUsEmail(contactUs.getEmail());
//             contactUsDisplay.setContactUsPhone(contactUs.getPhone());
//             contactUsDisplay.setActive(String.valueOf(contactUs.isActive()));
//             contactUsDisplay.setSeqNum(String.valueOf(contactUs.getSeqNum()));

//             vector.add(contactUsDisplay);
//         }

//         ContactUsDisplayCommand contactUsDisplayCommand[] = new ContactUsDisplayCommand[vector.size()];

//         vector.copyInto(contactUsDisplayCommand);

//         command.setContactUsCommands(contactUsDisplayCommand);
//     }

//     protected void calcPage(
//             User user, ContactUsListCommand command, List list, int pageNo) {

//         Site site = siteService.getSite(user.getLastVisitSiteId());

//         command.setPageNo(pageNo);

//         /* Calc Page Count */
//         int pageCount = (list.size() - list.size()
//                 % Integer.valueOf(site.getListingPageSize()))
//                 / Integer.valueOf(site.getListingPageSize());

//         if (list.size() % Integer.valueOf(site.getListingPageSize()) > 0) {
//             pageCount++;
//         }

//         command.setPageCount(pageCount);

//         int half = Constants.DEFAULT_LISTING_PAGE_COUNT / 2;

//         /* Calc Start Page */
//         int startPage = pageNo - half + 1;

//         if (startPage < 1) {
//             startPage = 1;
//         }

//         command.setStartPage(startPage);

//         /* Calc End Page */
//         /* Trying to make sure the maximum number of navigation is visible */
//         int endPage = startPage + Constants.DEFAULT_LISTING_PAGE_COUNT - 1;

//         while (endPage > pageCount && startPage > 1) {
//             endPage--;
//             startPage--;
//         }
//         /* Still not possible. Trimming navigation. */

//         if (endPage > pageCount) {

//             if (pageCount == 0) {
//                 endPage = 1;
//             } else {
//                 endPage = pageCount;
//             }

//         }

//         command.setStartPage(startPage);
//         command.setEndPage(endPage);
//     }

//     public void initSearchInfo(ContactUsListCommand command) {
//         if (command.getSrActive() == null) {
//             command.setSrActive("*");
//         }
//     }
// }