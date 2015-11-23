//package com.yoda.section;
//
//import java.io.OutputStream;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.yoda.kernal.util.PortalUtil;
//import com.yoda.section.service.SectionService;
//import com.yoda.user.model.User;
//
//@Controller
//@RequestMapping("/controlpanel/lookup/sectionLookup")
//public class SectionLookUpController {
//	@Autowired
//	SectionService sectionService;
//
//	@RequestMapping(method=RequestMethod.GET)
//	public void lookUpSection(
//			HttpServletRequest request,HttpServletResponse response)
//		throws Exception {
//		User user = PortalUtil.getAuthenticatedUser();
//
//		JSONObject jsonSection = sectionService.makeJSONSectionTree(user.getLastVisitSiteId());
//
//		String jsonString = jsonSection.toString();
//
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//
//		OutputStream outputStream = response.getOutputStream();
//
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
////		return null;
//	}
//}