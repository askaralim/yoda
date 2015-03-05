//package com.yoda.item;
//
//import java.io.OutputStream;
//import java.util.List;
//import java.util.Vector;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.yoda.item.model.Item;
//import com.yoda.item.service.ItemService;
//import com.yoda.kernal.util.PortalUtil;
//import com.yoda.user.model.User;
//import com.yoda.util.Constants;
//
//@Controller
//@RequestMapping("/controlpanel/lookup/itemLookup")
//public class ItemLookUpController {
//	@Autowired
//	ItemService itemService;
//
//	@RequestMapping(method=RequestMethod.GET)
//	public void lookUpSection(
//			@RequestParam(value="itemNum") String itemNum,
//			@RequestParam(value="itemUpcCd") String itemUpcCd,
//			@RequestParam(value="itemShortDesc") String itemShortDesc,
//			HttpServletRequest request,HttpServletResponse response)
//		throws Exception {
//		User user = PortalUtil.getAuthenticatedUser();
//
//		List<Item> items = itemService.search(
//			user.getLastVisitSiteId(), itemNum, itemUpcCd, itemShortDesc);
//
//		JSONObject jsonResult = new JSONObject();
//
//		jsonResult.put("itemNum", itemNum);
//		jsonResult.put("itemUpcCd", itemUpcCd);
//		jsonResult.put("itemShortDesc", itemShortDesc);
//
//		int counter = 0;
//
//		Vector<JSONObject> vector = new Vector<JSONObject>();
//
//		for (Item item : items) {
//			JSONObject jsonItem = new JSONObject();
//			jsonItem.put("itemId", item.getItemId());
//			jsonItem.put("itemNum", item.getItemNum());
//			jsonItem.put("itemUpcCd", item.getItemUpcCd());
//			jsonItem.put("itemShortDesc", item.getItemShortDesc());
//
//			vector.add(jsonItem);
//
//			counter++;
//
//			if (counter == Constants.ADMIN_SEARCH_MAXCOUNT) {
//				jsonResult.put("message", "error.lookup.tooManyRecord");
//				break;
//			}
//		}
//
//		jsonResult.put("items", vector);
//
//		String jsonString = jsonResult.toString();
//
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//
//		OutputStream outputStream = response.getOutputStream();
//
//		outputStream.flush();
//	}
//}
