<%@ include file="/html/common/init.jsp" %>

<%@ page language="java" import="com.yoda.fckeditor.FckEditorCreator"%>

<jsp:useBean id="contentEditCommand" type="com.yoda.content.ContentEditCommand" scope="request" />

<script language="javascript" type="text/javascript" src='<c:url value="/FCKeditor/fckeditor.js" />'></script>

<!--  tab panel -->
<script type="text/javascript" language="JavaScript">
	function submitDefaultImage(input) {
		document.forms[0].process.value = "defaultImage";
		document.forms[0].createDefaultImageId.value = input;
	}

	function submitBackForm(type) {
		location.href='<spring:url value="/controlpanel/content/list" />';
		return false;
	}

	function removeContent() {
		document.fm.action= '<spring:url value="/controlpanel/content/remove" />';
		document.fm.submit();
		return false;
	}

	function handleContentPublishOn(type, args, obj) { 
		document.forms[0].publishDate.value = jc_calendar_callback(type, args, obj);
	}

	function handleContentExpireOn(type, args, obj) { 
		document.forms[0].expireDate.value = jc_calendar_callback(type, args, obj);
	}

	YAHOO.namespace("example.calendar");

	function init() {
		YAHOO.example.calendar.calContentPublishOn = new YAHOO.widget.Calendar("calContentPublishOn", "calContentPublishOnContainer", { title:"Choose a date:", close:true } );
		YAHOO.example.calendar.calContentPublishOn.render();
		YAHOO.example.calendar.calContentPublishOn.hide();
		YAHOO.util.Event.addListener("calContentPublishOn", "click", YAHOO.example.calendar.calContentPublishOn.show, YAHOO.example.calendar.calContentPublishOn, true);
		YAHOO.example.calendar.calContentPublishOn.selectEvent.subscribe(handleContentPublishOn, YAHOO.example.calendar.calContentPublishOn, true); 

		YAHOO.example.calendar.calContentExpireOn = new YAHOO.widget.Calendar("calContentExpireOn", "calContentExpireOnContainer", { title:"Choose a date:", close:true } );
		YAHOO.example.calendar.calContentExpireOn.render();
		YAHOO.example.calendar.calContentExpireOn.hide();
		YAHOO.util.Event.addListener("calContentExpireOn", "click", YAHOO.example.calendar.calContentExpireOn.show, YAHOO.example.calendar.calContentExpireOn, true);
		YAHOO.example.calendar.calContentExpireOn.selectEvent.subscribe(handleContentExpireOn, YAHOO.example.calendar.calContentExpireOn, true);
	}

	YAHOO.util.Event.addListener(window, "load", init);

	var tabView = new YAHOO.widget.TabView('tabPanel');
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/content/list" />">Content Listing</a></li>
	<li>Content Maintenance</li>
</ol>

<form:form method="post" modelAttribute="contentEditCommand" name="fm">
	<form:hidden path="contentId" />

	<div class="row">
		<div class="col-md-8">
			<div class="form-group">
				<label for="title"><spring:message code="title" /></label>
				<form:input path="title" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for="shortDescription"><spring:message code="short-description" /></label>
				<form:input path="shortDescription" cssClass="form-control" />
			</div>
			<div class="form-group">
				<label for=description><spring:message code="text" /></label>
				<%
				out.println(FckEditorCreator.getFckEditor(request, "description", "100%", "300", "Basic", contentEditCommand.getDescription()));
				%>
			</div>
			<div class="form-group">
				<label for="pageTitle"><spring:message code="keywords" /></label>
				<form:textarea path="pageTitle" cssClass="form-control" />
			</div>
		</div>
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					General
				</div>
				<div class="panel-body">
					<c:if test="${!contentEditCommand['new']}">
						<div class="form-group">
							<label for="password">Hit Counter</label>
							<form:input path="hitCounter" cssClass="form-control" />
							<a onclick="jc_reset_counter()" href="javascript:void(0);">
								Reset Counter
							</a>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12">
			<c:if test="${contentEditCommand['new']}">
				<input type="submit" value='<spring:message code="save" />' class="btn btn-sm btn-primary" role="button">
			</c:if>
			<c:if test="${!contentEditCommand['new']}">
				<input type="submit" value='<spring:message code="update" />' class="btn btn-sm btn-primary" role="button">
			</c:if>
			<input type="button" value='<spring:message code="cancel" />' class="btn btn-sm btn-default" role="button" onclick="return submitBackForm();">
		</div>
	</div>

	<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td width="100%" valign="top">
			</td>
			<td valign="top" width="400">
				<div class="jc_detail_panel_header">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<span class="jc_input_label">
									General
								</span>
							</td>
							<td>
								<div align="right">&nbsp;</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="jc_detail_panel">
					<table width="400" border="0" cellspacing="0" cellpadding="5" class="jc_nobordered_table">
						<c:if test="${!contentEditCommand['new']}">
							<tr>
								<td class="jc_input_label">
									Hit Counter
								</td>
								<td class="jc_input_label">
									<span id="hitCounter">
										<form:input path="hitCounter" cssClass="jc_input_control" />
									</span>
									&nbsp;&nbsp;
									<a class="jc_navigation_link" onclick="jc_reset_counter()" href="javascript:void(0);">
										Reset Counter
									</a>
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="jc_input_label">
								Publish On
							</td>
							<td class="jc_input_label">
								<span class="jc_input_error">
									<form:input path="publishDate" cssClass="jc_input_control" />
									<form:errors path="publishDate" cssClass="error" />

									<img id="calContentPublishOn" src="<spring:url value="/resources/images/icon/image_plus.gif" />" alt=""/>

									<div id="calContentPublishOnContainer" style="position: absolute; display: none;"></div>
								</span>
							</td>
						</tr>
						<tr>
							<td class="jc_input_label">
								Expire On
							</td>
							<td class="jc_input_label">
								<span class="jc_input_error">
									<form:input path="expireDate" cssClass="jc_input_control" />
									<form:errors path="expireDate" cssClass="error" />

									<img id="calContentExpireOn" src="<spring:url value="/resources/images/icon/image_plus.gif" />" border="0">

									<div id="calContentExpireOnContainer" style="position: absolute; display: none;"></div>
								</span>
							</td>
						</tr>
						<tr>
							<td class="jc_input_label">
								Show in Home Page
							</td>
							<td class="jc_input_label">
								<form:checkbox path="homePage" cssClass="jc_input_control" />
							</td>
						</tr>
						<tr>
							<td class="jc_input_label">
								Published
							</td>
							<td class="jc_input_label">
								<form:checkbox path="published" cssClass="jc_input_control" />
							</td>
						</tr>
					</table>
				</div> 
				<c:if test="${!contentEditCommand['new']}">
					<div class="jc_detail_panel_header">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<span class="jc_input_label">
										Images
									</span>
								</td>
								<td>
									<div align="right">
										<table width="0" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<div id="tabImage_show">
														<a href="javascript:void(0);" onclick="showId('tabImage');hideId('tabImage_show');showId('tabImage_hide')">
															<img src="<%= request.getContextPath() %>/resources/images/icon/image_plus.gif" width="11" height="11" border="0">
														</a>
													</div>
												</td>
												<td>
													<div id="tabImage_hide" style="display: none;">
														<a href="javascript:void(0);" onclick="hideId('tabImage');showId('tabImage_show');hideId('tabImage_hide')">
															<img src="<%= request.getContextPath() %>/resources/images/icon/image_minus.gif" width="11" height="11" border="0">
														</a>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<div id="tabImage" class="jc_detail_panel" style="display: none;">
						<table width="400" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<div align="left">
										Upload&nbsp;image
										<a class="jc_navigation_link" onclick="jc_image_upload_show(<c:out value='${contentEditCommand.contentId}'/>)" href="javascript:void(0);">
											Upload&nbsp;image
										</a>
										<%-- <a class="jc_navigation_link" onclick="jc_images_remove()" href="javascript:void(0);">
											Remove&nbsp;selected&nbsp;images
										</a> --%>
									</div>
									<!-- <input type="file" name="file" class="jc_input_object" width="300px"> -->
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
						</table>
						<table width="400" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<div class="jc_image_scroll" style="width: 400px">
										<table id="jc_images_table" width="100%" border="0" cellspacing="0" cellpadding="5">
											<tr>
												<c:if test="${contentEditCommand.defaultImage != null}">
													<td width="150px" align="center" valign="bottom" class="jc_input_label_small">
														<img id=""
														src='<spring:url value="/images" />?imageId=${contentEditCommand.defaultImage.imageId}&type=C&maxsize=100'
														alt=""/>
														<br>
														<input type="checkbox" name="removeImages" value="${contentEditCommand.defaultImage.imageId}">
														<br>
														<c:out value="${contentEditCommand.defaultImage.imageName}" />
														<br>
													</td>
												</c:if>
												<form:hidden path="createDefaultImageId" />
												<%-- <c:forEach items="${contentEditCommand.images}" var="image">
													<td width="150px" align="center" valign="bottom" class="jc_input_label_small">
														<img id="${image.imageId}"
														src='<spring:url value="/controlpanel/content/secureimage" />?imageId='+ ${image.imageId} + '&type=C&maxsize=100'
														alt=""/>
														<br>
														<input type="checkbox" name="removeImages" value="${image.imageId}">
														<br>
														<c:out value="${image.imageName}" />
														<br>
														<a class="jc_navigation_link" onclick='jc_images_set_default("${image.imageId}")' href="javascript:void(0);">
															Set&nbsp;Default
														</a>
													</td>
												</c:forEach> --%>
												<td width="100%"></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<div class="jc_detail_panel_header">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><span class="jc_input_label">Menus</span></td>
								<td>
									<div align="right">
										<table width="0" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<div id="tabMenus_show">
														<a href="javascript:void(0);" onclick="showId('tabMenus');hideId('tabMenus_show');showId('tabMenus_hide')">
															<img src="<%= request.getContextPath() %>/resources/images/icon/image_plus.gif" width="11" height="11" border="0">
														</a>
													</div>
												</td>
												<td>
													<div id="tabMenus_hide" style="display: none;">
														<a href="javascript:void(0);" onclick="hideId('tabMenus');showId('tabMenus_show');hideId('tabMenus_hide')">
															<img src="<%= request.getContextPath() %>/resources/images/icon/image_minus.gif" width="11" height="11" border="0">
														</a>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<div id="tabMenus" class="jc_detail_panel" style="display: none;">
						<table width="400" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<div align="right">
										<a href="javascript:jc_menus_search_show();" class="jc_navigation_link">Pick&nbsp;Menus</a>
										<!-- <html:link href="javascript:jc_menus_search_show();" styleClass="jc_navigation_link">Pick&nbsp;Menus</html:link> -->

										<a href="javascript:void(0);" onclick="jc_menus_remove;" class="jc_navigation_link">Remove&nbsp;Selected&nbsp;Menus</a>
										<!-- <html:link href="javascript:void(0);" onclick="jc_menus_remove()" styleClass="jc_navigation_link">Remove&nbsp;Selected&nbsp;Menus</html:link> -->
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
						</table>
						<table id="menuTable" width="400" border="0" cellspacing="0" cellpadding="2" class="jc_unbordered_table">
							<tr>
								<td></td>
								<td class="jc_input_label">Menu</td>
								<td class="jc_input_label">Window Mode</td>
								<td class="jc_input_label">Window Target</td>
							</tr>
							<%-- <form:checkboxes items="${contentEditCommand.selectedMenus}" path="selectedMenusIds" itemLabel="menuLongDesc" itemValue="menuId" /> --%>
							<c:forEach items="${contentEditCommand.selectedMenus}" var="selectedMenu">
								<tr>
									<td class="jc_input_control">
										<%-- <form:checkbox path="contentEditCommand.selectedMenus.menuId" /> --%>
										<input type="checkbox" name="removeMenus" value="${selectedMenu.menuId}">
									</td>
									<td>
										<%-- <form:label path="contentEditCommand.selectedMenus.menuLongDesc" /> --%>
										<c:out value="${selectedMenu.menuLongDesc}" />
									</td>
									<td>
										<%-- <form:label path="contentEditCommand.selectedMenus.menuWindowMode" /> --%>
										<c:out value="${selectedMenu.menuWindowMode}" />
									</td>
									<td>
										<%-- <form:label path="contentEditCommand.selectedMenus.menuWindowTarget" /> --%>
										<c:out value="${selectedMenu.menuWindowTarget}" />
									</td>
								</tr>
							</c:forEach>
							<!-- <logic:iterate name="contentMaintForm" property="selectedMenus" id="selectedMenu">
								<tr>
									<td class="jc_input_control">
										<html:multibox property="removeMenus">
											<bean:write name="selectedMenu" property="menuId" />
										</html:multibox>
									</td>
									<td>
										<bean:write name="selectedMenu" property="menuLongDesc" />
									</td>
									<td>
										<bean:write name="selectedMenu" property="menuWindowMode" />
									</td>
									<td>
										<bean:write name="selectedMenu" property="menuWindowTarget" />
									</td>
								</tr>
							</logic:iterate> -->
						</table>
					</div>
					<div class="jc_detail_panel_header">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><span class="jc_input_label">Section</span></td>
								<td>
									<div align="right">
										<table width="0" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<div id="tabSection_show">
														<a href="javascript:void(0);" onclick="showId('tabSection');hideId('tabSection_show');showId('tabSection_hide')">
															<img src="<%= request.getContextPath() %>/resources/images/icon/image_plus.gif" width="11" height="11" border="0">
														</a>
													</div>
												</td>
												<td>
													<div id="tabSection_hide" style="display: none;">
														<a href="javascript:void(0);" onclick="hideId('tabSection');showId('tabSection_show');hideId('tabSection_hide')">
															<img src="<%= request.getContextPath() %>/resources/images/icon/image_minus.gif" width="11" height="11" border="0">
														</a>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<div id="tabSection" class="jc_detail_panel" style="display: none;">
						<table width="400" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<div align="right">
										<!-- <html:link href="javascript:jc_section_search_show();" styleClass="jc_navigation_link">Pick&nbsp;Section</html:link> -->
										<a href="javascript:jc_section_search_show();" class="jc_navigation_link">Pick&nbsp;Section</a>
										<a href="javascript:void(0);" onclick="return jc_section_remove();" class="jc_navigation_link">Remove&nbsp;from&nbsp;section</a>
										<!-- <html:link href="javascript:void(0);" onclick="return jc_section_remove();" styleClass="jc_navigation_link">
											Remove&nbsp;from&nbsp;section
										</html:link> -->
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
						</table>
						<table width="400" border="0" cellspacing="0" cellpadding="2" bordercolor="#CCCCCC" class="jc_unbordered_table">
							<tr class="jc_input_control">
								<td>
									<div id="selectedSection">
										<c:out value='${contentEditCommand.selectedSection}' />
									</div>
								</td>
							</tr>
						</table>
					</div>
					<div class="jc_detail_panel_header">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<span class="jc_input_label">
										Timestamp
									</span>
								</td>
								<td>
									<div align="right">
										<table width="0" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<div id="tabTimestamp_show">
														<a href="javascript:void(0);" onclick="showId('tabTimestamp');hideId('tabTimestamp_show');showId('tabTimestamp_hide')">
															<img src="<%= request.getContextPath() %>/resources/images/icon/image_plus.gif" width="11" height="11" border="0">
														</a>
													</div>
												</td>
												<td>
													<div id="tabTimestamp_hide" style="display: none;">
														<a href="javascript:void(0);" onclick="hideId('tabTimestamp');showId('tabTimestamp_show');hideId('tabTimestamp_hide')">
															<img src="<%= request.getContextPath() %>/resources/images/icon/image_minus.gif" width="11" height="11" border="0">
														</a>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<div id="tabTimestamp" class="jc_detail_panel" style="display: none;">
						<table width="400" border="0" cellspacing="0" cellpadding="5" bordercolor="#CCCCCC" class="jc_unbordered_table">
							<tr class="jc_input_table_row">
								<td class="jc_input_label" width="150">
									Updated By
								</td>
								<td class="jc_input_control" width="0">
									<div id="updateBy">
										<form:input path="updateBy" cssClass="jc_input_control"/>
										<!-- <layout:text layout="false" property="updateBy" mode="I,I,I" styleClass="jc_input_control" /> -->
									</div>
								</td>
							</tr>
							<tr class="jc_input_table_row">
								<td class="jc_input_label">
									Updated On
								</td>
								<td class="jc_input_control">
									<div id="updateDate">
										<form:input path="updateDate" cssClass="jc_input_control"/>
										<!-- <layout:text layout="false" property="updateDate" mode="I,I,I" styleClass="jc_input_control" /> -->
									</div>
								</td>
							</tr>
							<tr class="jc_input_table_row">
								<td class="jc_input_label">
									Created By
								</td>
								<td class="jc_input_label">
									<form:input path="createBy" cssClass="jc_input_control"/>
									<!-- <layout:text layout="false" property="recCreateBy" mode="I,I,I" styleClass="jc_input_control" /> -->
								</td>
							</tr>
							<tr class="jc_input_table_row">
								<td class="jc_input_label">
									Created On
								</td>
								<td class="jc_input_label">
									<form:input path="createDate" cssClass="jc_input_control"/>
									<!-- <layout:text layout="false" property="recCreateDatetime" mode="I,I,I" styleClass="jc_input_control" /> -->
								</td>
							</tr>
						</table>
					</div>
				</c:if>
				<!-- </layout:mode> -->
			</td>
		</tr>
	</table>

<script type="text/javascript">

var jc_section_add_callback =
{
	success: function(o) {
		if (!isJsonResponseValid(o.responseText)) {
			jc_section_search_messge("Unexcepted Error: unable to assign section");
			return false;
		}
		var jsonObject = eval('(' + o.responseText + ')');
		setIdValue("updateBy", jsonObject.updateBy);
		setIdValue("updateDate", jsonObject.updateDate);
		var selectedSection = document.getElementById("selectedSection");
		selectedSection.innerHTML = jsonObject.selectedSection;
		jc_section_search_finish();
	},
	failure: function(o) {
		jc_section_search_messge("Unable to add section");
	}
};

function jc_section_search_client_callback(value) {
	var response = eval('(' + value + ')');
	var contentId = <c:out value='${contentEditCommand.contentId}'/>;	

	var url = '<spring:url value="/controlpanel/content/edit/${contentEditCommand.contentId}/addSection"/>';
	//var url = '<spring:url value="/controlpanel/content/edit/{contentId}/addSection"><spring:param name="contentId" value="${contentEditCommand.contentId}"/></spring:url>';
	var data = "sectionId=" + response.sections[0].sectionId;
	var request = YAHOO.util.Connect.asyncRequest('POST', url, jc_section_add_callback, data);

	return false;
}
</script>

<%@ include file="/html/controlpanel/include/sectionLookup.jsp"%>

<script type="text/javascript">
var jc_section_remove_callback =
{
	success: function(o) {
		if (!isJsonResponseValid(o.responseText)) {
			jc_panel_show_error("Unexcepted Error: unable to assign section");
			return false;
		}

		var jsonObject = getJsonObject(o.responseText);

		setIdValue("updateBy", jsonObject.updateBy);
		setIdValue("updateDate", jsonObject.updateDate);

		var selectedSection = document.getElementById("selectedSection");

		selectedSection.innerHTML = "";
		jc_panel_show_message("Content removed from section successfully");
	},
	failure: function(o) {
		jc_panel_show_error("Unable to remove content from section");
	}
};

function jc_section_remove() {
	var url = '<spring:url value="/controlpanel/content/edit/${contentEditCommand.contentId}/removeSection"/>';
	var request = YAHOO.util.Connect.asyncRequest('POST', url, jc_section_remove_callback, data);
	return false;
}
</script>

<script type="text/javascript">
function jc_menus_show_menus(jsonObject) {
	var table = document.getElementById("menuTable");
	var tbody = table.getElementsByTagName("tbody")[0];
	if (!tbody) {
		tbody = document.createElement('tbody');
		table.appendChild(tbody);
	}
	var rows = tbody.childNodes;
	for (var i = table.rows.length - 1; i > 0; i--) {
		table.deleteRow(i);
	}

	for (var i = 0; i < jsonObject.menus.length; i++) {
		var row = document.createElement('tr');
		var column;
		column = document.createElement('td');
		column.innerHTML = '<input type="checkbox" value="' + jsonObject.menus[i].menuId + '" name="removeMenus"/>';
		row.appendChild(column);
		column = document.createElement('td');
		column.innerHTML = jsonObject.menus[i].menuLongDesc;
		row.appendChild(column);
		column = document.createElement('td');
		column.innerHTML = jsonObject.menus[i].menuWindowMode;
		row.appendChild(column);
		column = document.createElement('td');
		column.innerHTML = jsonObject.menus[i].menuWindowTarget;
		row.appendChild(column);
		tbody.appendChild(row);
	}
}

var jc_menus_add_callback =
{
	success: function(o) {
		if (!isJsonResponseValid(o.responseText)) {
			var message = document.getElementById("jc_menus_search_message");
			message.innerHTML = "Unexcepted Error: unable to add menus";
			return false;
		}

		var jsonObject = eval('(' + o.responseText + ')');
		setIdValue("updateBy", jsonObject.updateBy);
		setIdValue("updateDate", jsonObject.updateDate); 
		jc_menus_show_menus(jsonObject);
		jc_menus_search_finish();
	},
	failure: function(o) {
		jc_panel_show_error("Unable to add menus");
	}
};

function jc_menus_search_client_callback(value) {
	var response = eval('(' + value + ')');
	var contentId = <c:out value='${contentEditCommand.contentId}'/>

	var url = '<spring:url value="/controlpanel/content/edit/${contentEditCommand.contentId}/addMenus"/>';
	//var url = '<spring:url value="/controlpanel/content/edit/{contentId}/addMenus"><spring:param name="contentId" value="${contentEditCommand.contentId}"/></spring:url>';

	var data = "menuWindowTarget=" + response.menuWindowTarget + "&menuWindowMode=" + response.menuWindowMode;

	for (var i = 0; i < response.menus.length; i++) {
		data += "&addMenus=" + response.menus[i].menuId;
	}

	var request = YAHOO.util.Connect.asyncRequest('POST', url, jc_menus_add_callback, data);

	return false;
}
</script>

<%@ include file="/html/controlpanel/include/menusLookup.jsp"%>

<script type="text/javascript">
var jc_menus_remove_callback = {
	success: function(o) {
		if (!isJsonResponseValid(o.responseText)) {
			jc_panel_show_error("Unexcepted Error: unable to remove menus");
			return false;
		}

		var jsonObject = eval('(' + o.responseText + ')');

		setIdValue("updateBy", jsonObject.updateBy);
		setIdValue("updateDate", jsonObject.updateDate);

		jc_menus_show_menus(jsonObject);
		jc_panel_show_message("Menu(s) removed successfully");
	},

	failure: function(o) {
		jc_panel_show_error("Unexcepted Error: unable to add to menus");
	}
};

function jc_menus_remove() {
	//var contentId = <c:out value='${contentEditCommand.contentId}'/>;

	var url = '<spring:url value="/controlpanel/content/edit/${contentEditCommand.contentId}/removeMenus"/>';
	//var url = "<c:out value='/${admin.contextPath}'/>/admin/content/contentMaint.do";
	//var data = "process=removeMenus&contentId=" + contentId;

	var e = document.getElementById("menuTable");
	var checkboxes = new Array();
	jc_traverse_element(e, checkboxes, 'input', 'removeMenus');

	var removeMenus = new Array();
	var count = 0;
	for (var i = 0; i < checkboxes.length; i++) {
		if (!checkboxes[i].checked) {
			continue;
		}

		if (i == 0) {
			data += "removeMenus=" + checkboxes[i].value;
		}
		else {
			data += "&removeMenus=" + checkboxes[i].value;
		}

		removeMenus[count++] = checkboxes[i].value;
	}

	var request = YAHOO.util.Connect.asyncRequest('POST', url, jc_menus_remove_callback, data);

	return false;
}
</script>

</form:form>

<script type="text/javascript">
function jc_image_upload_client_callback(value) {
	var response = eval('(' + value + ')');
	var formName = response.formName;
	YAHOO.util.Connect.setForm(formName, true, true);

	var url = '<spring:url value="/controlpanel/content/${contentEditCommand.contentId}/edit/uploadImage?${_csrf.parameterName}=${_csrf.token}"/>';
// alert("response.file" + response.file);
	var data = "file=" + response.file;

	var response = YAHOO.util.Connect.asyncRequest('POST', url, jc_upload_callback, data);
}

var jc_upload_callback = {
	upload: function(o) {
		var value = o.responseText.replace(/<\/?pre>/ig, '');

		if (!isJsonResponseValid(value)) {
			jc_image_upload_message('Unable to upload image');
			return false;
		}

		var jsonObject = getJsonObject(value);

		if (jsonObject.status == 'failed') {
			jc_image_upload_message('Unable to upload image');
		}
		else {
			setIdValue("updateBy", jsonObject.updateBy);
			setIdValue("updateDate", jsonObject.updateDate);
			jc_image_show_images(jsonObject);
			jc_image_upload_finish();
		}
	}
}

function jc_image_show_images(jsonObject) {
	var table = document.getElementById("jc_images_table");
	var tbody = table.getElementsByTagName("tbody")[0];

	if (!tbody) {
		tbody = document.createElement('tbody');
		table.appendChild(tbody);
	}

	var rows = tbody.childNodes;

	for (var i = table.rows.length - 1; i >= 0; i--) {
		table.deleteRow(i);
	}

	var row = document.createElement('tr');

	if (jsonObject.defaultImage) {
		var column = document.createElement('td');
		column.className = 'jc_input_label_small';
		column.setAttribute("width", '150');
		column.setAttribute("valign", 'bottom');
		column.setAttribute("align", 'center');
		column.innerHTML = '<img src="<spring:url value="/images" />?imageId='+ jsonObject.defaultImage.imageId + '&type=C&maxsize=100/><br>';
		//column.innerHTML = '<img src="<c:out value="/${admin.contextPath}"/>/services/SecureImageProvider.do?type=C&maxsize=100&imageId=' + jsonObject.defaultImage.imageId + '"/><br>';
		column.innerHTML += '<input type="checkbox" value="' + jsonObject.defaultImage.imageId + '" name="removeImages"/><br>';
		column.innerHTML += jsonObject.defaultImage.imageName + '<br>';
		column.innerHTML += 'Default Image';
		row.appendChild(column);
	}

	for (var i = 0; i < jsonObject.images.length; i++) {
		var image = jsonObject.images[i];
		var column = document.createElement('td');
		column.noWrap = true;
		column.className = 'jc_input_label_small';
		column.setAttribute("width", '150');
		column.setAttribute("valign", 'bottom');
		column.setAttribute("align", 'center');
		column.innerHTML = '<img src="<spring:url value="/images" />?imageId='+ image.imageId + '&type=C&maxsize=100/><br>';
		//column.innerHTML = '<img src="<c:out value="/${admin.contextPath}"/>/services/SecureImageProvider.do?type=C&maxsize=100&imageId=' + image.imageId + '"/><br>';
		column.innerHTML += '<input type="checkbox" value="' + image.imageId + '" name="removeImages"/><br>';
		column.innerHTML += image.imageName + '<br><br>';
		column.innerHTML += '<a class="jc_navigation_link" onclick="jc_images_set_default(' + image.imageId + ');" href="javascript:void(0);">Set&nbsp;Default</a>';
		row.appendChild(column);
	}

	var column = document.createElement('td');
	column.setAttribute("width", '100%');
	row.appendChild(column);
	tbody.appendChild(row);
}

function jc_images_remove() {
	//var contentId = <c:out value='${contentEditCommand.contentId}'/>
	var url = '<spring:url value="/controlpanel/content/edit/${contentEditCommand.contentId}/removeImages"/>';
	//var url = "<c:out value='/${admin.contextPath}'/>/admin/content/contentMaint.do";
	//var data = "process=removeImages&contentId=" + contentId;

	var e = document.getElementById("jc_images_table");
	var checkboxes = new Array();
	jc_traverse_element(e, checkboxes, 'input', 'removeImages');

	var removeImages = new Array();
	var count = 0;
	var data;
	for (var i = 0; i < checkboxes.length; i++) {
		if (!checkboxes[i].checked) {
		continue;
		}

		if (i == 0) {
			data += "removeImages=" + checkboxes[i].value;
		}
		else {
			data += "&removeImages=" + checkboxes[i].value;
		}

		removeImages[count++] = checkboxes[i].value;
	}
	//var request = YAHOO.util.Connect.asyncRequest('POST', url, jc_images_remove_callback, data);
	var request = YAHOO.util.Connect.asyncRequest('POST', url, jc_images_remove_callback);
	return false;
}

var jc_images_remove_callback =
{
	success: function(o) {

		if (!isJsonResponseValid(o.responseText)) {
		jc_panel_show_error("Unexcepted Error: unable to add to menus");
		return false;
		}

		var jsonObject = getJsonObject(o.responseText);

		setIdValue("updateBy", jsonObject.updateBy);

		setIdValue("updateDate", jsonObject.updateDate);

		jc_image_show_images(jsonObject);

		jc_panel_show_message('Image(s) removed successfully');
	},
	failure: function(o) {
		jc_panel_show_error("Unexcepted Error: unable to remove menus");
	}
};

function jc_images_set_default(imageId) {
	//var contentId = <c:out value='${contentEditCommand.contentId}'/>
	var url = '<spring:url value="/controlpanel/content/edit/${contentEditCommand.contentId}/defaultImage"/>';
	//var url = "<c:out value='/${admin.contextPath}'/>/admin/content/contentMaint.do";
	//var data = "process=defaultImage&contentId=" + contentId + "&createDefaultImageId=" + imageId;
	var data = "createDefaultImageId=" + imageId;
	var request = YAHOO.util.Connect.asyncRequest('POST', url, jc_images_set_default_callback, data);
	jc_panel_show_message('Image set successfully');

	return false;
}

var jc_images_set_default_callback =
{
	success: function(o) {
		if (!isJsonResponseValid(o.responseText)) {
			jc_image_upload_message("Unexcepted Error: unable to set default menu");
			return false;
		}

		var jsonObject = getJsonObject(o.responseText);
		setIdValue("updateBy", jsonObject.updateBy);
		setIdValue("updateDate", jsonObject.updateDate);
		jc_image_show_images(jsonObject);
	},
	failure: function(o) {
		jc_image_upload_message("Unexcepted Error: unable to set default menu");
	}
};
</script>

<%@ include file="/html/controlpanel/include/imageUpload.jsp"%>

<script type="text/javascript">
function jc_reset_counter() {
	var contentId = <c:out value='${contentEditCommand.contentId}'/>
	var url = '<spring:url value="/controlpanel/content/edit/${contentEditCommand.contentId}/resetCounter"/>';
	//var url = "<c:out value='/${admin.contextPath}'/>/admin/content/contentMaint.do";
	//var data = "process=resetCounter&contentId=" + contentId ;
	var request = YAHOO.util.Connect.asyncRequest('POST', url, jc_reset_counter_callback);

	return false;
}

var jc_reset_counter_callback =
{
	success: function(o) {
		if (!isJsonResponseValid(o.responseText)) {
			jc_panel_show_error("Unexcepted Error: Unable to reset hit counter");
			return false;
		}
		var text = document.getElementById("hitCounter");
		text.innerHTML = '<span class="jc_input_control">0</span>';
		var jsonObject = getJsonObject(o.responseText);
		setIdValue("updateBy", jsonObject.updateBy);
		setIdValue("updateDate", jsonObject.updateDate);
		jc_panel_show_message("Hit counter reset successfully");
	},
	failure: function(o) {
		jc_panel_show_error("Unexcepted Error: Unable to reset hit counter");
	}
};
</script>
<%@ include file="/html/controlpanel/include/panel.jsp"%>