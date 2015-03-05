<%@ include file="/html/common/init.jsp"%>

<%@ page language="java" import="com.yoda.section.DropDownMenuGenerator"%>
<%@ page language="java" import="com.yoda.section.model.DropDownMenu"%>

<jsp:useBean id="menuEditCommand" type="com.yoda.menu.MenuEditCommand" scope="request" />

<script type="text/javascript">
function submitRemoveSelected() {
	document.fm.action = '<c:url value="/controlpanel/menu/removeselected"/>';
	document.fm.submit();
	return true;
}

function submitResequence() {
	document.fm.action = '<c:url value="/controlpanel/menu/resequence"/>';
	document.fm.submit();
	return true;
}

function submitNewMenuSet() {
	document.fm.action = '<c:url value="/controlpanel/menu/newmenuset"/>';
	document.fm.submit();
	return true;
}

function submitSave() {
	document.fm.action = '<c:url value="/controlpanel/menu/save"/>';
	document.fm.submit();
	return true;
}

function submitRemove() {
	document.fm.action = '<c:url value="/controlpanel/menu/remove"/>';
	document.fm.submit();
	return true;
}

var menuId = null;

function addChildNode() {
	document.fm.createMenuId.value = menuId;
	document.fm.createMode.value = "C";
	document.fm.action = '<c:url value="/controlpanel/menu/create"/>';
	document.fm.submit();
}

function createBeforeNode() {
	document.fm.createMenuId.value = menuId;
	document.fm.createMode.value = "B";
	document.fm.action = '<c:url value="/controlpanel/menu/create"/>';
	document.fm.submit();
}

function createAfterNode() {
	document.fm.createMenuId.value = menuId;
	document.fm.createMode.value = "A";
	document.fm.action = '<c:url value="/controlpanel/menu/create"/>';
	document.fm.submit();
}

function sequenceNode() {
	document.fm.menuId.value = menuId;
	document.fm.createMode.value = "A";
	document.fm.action = '<c:url value="/controlpanel/menu/showsequence"/>';
	document.fm.submit();
}

function removeNode() {
	document.fm.action = '<c:url value="/controlpanel/menu/removemenuset"/>';
	document.fm.removeMenuId.value = menuId;
	document.fm.submit();
}

var sectionId = null;

function sequenceHomeNode() {
	document.fm.menuId.value = menuId;
	document.fm.createMode.value = "A";
	document.fm.action = '<c:url value="/controlpanel/menu/showsequence"/>';
	document.fm.submit();
}

function getKey(oTextNode) {
	var key = "";
	var nodes = oTextNode.childNodes;
	for ( var i = 0; i < nodes.length; i++) {
		if (nodes[i].tagName && nodes[i].tagName.toUpperCase() == 'DIV') {
			key = nodes[i].innerHTML;
		}
	}
	return key;
}

function onTriggerContextMenu(p_oEvent) {
	function getTextNodeFromEventTarget(p_oTarget) {
		// if (p_oTarget.tagName.toUpperCase() == "A" && p_oTarget.className ==
		// "ygtvlabel") {
		if (p_oTarget.tagName.toUpperCase() == "A") {
			return p_oTarget;
		} else {
			if (p_oTarget.parentNode && p_oTarget.parentNode.nodeType == 1) {
				return getTextNodeFromEventTarget(p_oTarget.parentNode);
			}
		}
	}

	var oTextNode = getTextNodeFromEventTarget(this.contextEventTarget);
	if (oTextNode) {
		menuId = getKey(oTextNode);
	} else {
		this.cancel();
	}
}

function onTriggerHomeMenu(p_oEvent) {
	var object = this.contextEventTarget;
	var nodes = object.childNodes;
	for ( var i = 0; i < nodes.length; i++) {
		if (nodes[i].tagName && nodes[i].tagName.toUpperCase() == 'DIV') {
			menuId = nodes[i].innerHTML;
			return;
		}
	}
}

function expand(pos) {
	for (i = 1; i < 9; i++) {
		var id = "location-" + i;
		var object = document.getElementById(id);
		if (pos == i) {
			object.style.display = 'block';
		} else {
			object.style.display = 'none';
		}
	}
}
</script>

<form:form modelAttribute="menuEditCommand" method="post" name="fm">
	<form:hidden path="removeMenuId" />
	<form:hidden path="createMenuId" />
	<form:hidden path="createMode" />
	<form:hidden path="menuId" />

	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="jc_top_navigation">
		<tr>
			<td>Administration - Menu</td>
		</tr>
	</table>
	<br>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="0%" valign="top">
				<div class="jc_search_panel_open">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="jc_panel_table_header">Menu Sets</td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="jc_input_error">
								<!-- <logic:messagesPresent property="menuId" message="true">
									<html:messages property="menuId" id="errorText" message="true">
										<bean:write name="errorText" />
									</html:messages>
								</logic:messagesPresent> -->
							</td>
						</tr>
						<tr>
							<td>
								<%
								DropDownMenu menuList[] = menuEditCommand.getMenuList();
								String locationId = "";
								for (int i = 0; i < menuList.length; i++) {
									locationId = "location" + i;
									String id = String.valueOf(i);
									String value = DropDownMenuGenerator.generate1(
										menuList[i], null, id, DropDownMenuGenerator.SELECTION_NONE, "menuId", request.getContextPath() + "/controlpanel/menu/update", "menuId");
									out.print(value);
									out.print("<br>");
									//out.print("<input name=\"menuId\" value=\"" + menuList[i].getMenuKey() + "\" type=\"radio\">");
									out.print("<div id=\"home" + id + "\" style=\"cursor: hand; cursor: pointer\"><div style=\"display: none\">" + menuList[i].getMenuKey() + "</div>");
									out.print(menuList[i].getMenuName());
									out.print("</div>");
									out.print("<div id=\"location" + id + "\"></div>");
								%>

								<script type="text/javascript">
									jc_create_yui_treemenu('<%= id %>', '<%= locationId %>');

									var oContextMenu = new YAHOO.widget.ContextMenu("ContextMenu<%= id %>", {
										trigger: "<%= locationId %>",
										lazyload: true, 
										itemdata: [
											{ text: "Append Child Menu", onclick: { fn: addChildNode } },
											{ text: "Create menu before", onclick: { fn: createBeforeNode } },
											{ text: "Create menu after", onclick: { fn: createAfterNode } }, 
											{ text: "Resequence/remove children", onclick: { fn: sequenceNode } }
										] });
										oContextMenu.subscribe("triggerContextMenu", onTriggerContextMenu);

									var homeContextMenu = new YAHOO.widget.ContextMenu("homeMenu<%= id %>", {
										trigger: "home<%= id %>",
										lazyload: true, 
										itemdata: [
											<%if (!menuList[i].getMenuName().equals("MAIN") && !menuList[i].getMenuName().equals("SECONDARY")) {
												out.print("{ text: \"Remove Menu Set\", onclick: { fn: removeNode } },");
											}%>
											{ text: "Append Child Menu", onclick: { fn: addChildNode } },
											{ text: "Resequence/remove children", onclick: { fn: sequenceHomeNode } }
										] });
									homeContextMenu.subscribe("triggerContextMenu", onTriggerHomeMenu);
								</script>
								<%
								}
								%>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="jc_panel_table_header" nowrap>Create New Menu Set</td>
							<td class="jc_panel_table_header">
								<div align="right">
									<input type="submit" value="Create" class="jc_submit_button" onclick="return submitNewMenuSet();">
								</div>
							</td>
						</tr>
						<tr>
							<td class="jc_input_label">Menu Set Name</td>
							<td></td>
						</tr>
						<tr>
							<td class="jc_input_object" colspan="2">
								<form:input path="createMenuSetName" cssClass="jc_input_object" />
							</td>
						</tr>
						<tr>
							<td>
								<span class="jc_input_error">
									<form:errors path="createMenuSetName" cssClass="error" />
									<!-- <logic:messagesPresent property="createMenuSetName" message="true">
										<html:messages property="createMenuSetName" id="errorText" message="true">
											<bean:write name="errorText" />
										</html:messages>
									</logic:messagesPresent> -->
								</span>
							</td>
							<td>
							</td>
						</tr>
					</table>
				</div>
			</td>
			<td width="20px"></td>

			<td width="100%" valign="top">
				<c:if test="${menuEditCommand.menuId != 0}">
				<%-- <c:if test="${menuEditCommand.mode != ''}"> --%>
					<table width="100%" border="0" cellspacing="0" cellpadding="2">
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="2">
									<tr>
										<td class="jc_panel_table_header" valign="top">
											<c:if test="${menuEditCommand.sequence == false}">
												<div align="right">
													<input type="submit" value="Save" class="jc_submit_button" onclick="return submitSave();">
													<input type="submit" value="Remove" class="jc_submit_button" onclick="return submitRemove();">
												</div>
											</c:if> 
											<c:if test="${menuEditCommand.sequence == true}">
												Menu - <c:out value="${menuEditCommand.menuName}" />
											</c:if>
										</td>
									</tr>
									<tr>
										<c:if test="${menuEditCommand.sequence == false}">
											<td valign="top">
												<table width="100%" border="0" cellspacing="0" cellpadding="2">
													<tr>
														<td>
															<table width="100%" border="0" cellspacing="0" cellpadding="2">
																<tr>
																	<td width="0" class="jc_input_label">Menu Title</td>
																</tr>
																<tr>
																	<td>
																		<form:input path="menuTitle" cssClass="tableContent" size="20" />
																		<form:errors path="menuTitle" cssClass="error" />
																	</td>
																</tr>
																<tr>
																	<td width="0" class="jc_input_label">Menu Name</td>
																</tr>
																<tr>
																	<td>
																		<form:input path="menuName" cssClass="tableContent" size="20" />
																		<form:errors path="menuName" cssClass="error" />
																	</td>
																</tr>
																<tr>
																<tr>
																	<td width="0" class="jc_input_label">&nbsp;</td>
																</tr>
																<tr>
																	<td width="0" class="jc_input_label">Menu Location</td>
																</tr>
																<tr>
																	<td>
																		<table width="500" border="0" cellspacing="0" cellpadding="3" class="jc_bordered_table">
																			<tr>
																				<td>
																					<form:radiobutton path="menuType" value="SECT" onclick="expand(1)"/>
																					Section
																					<div id="location-1" style="display: none; padding: 10px 20px 10px 20px">
																						<form:hidden path="sectionId" />
																						<form:hidden path="sectionShortTitle" />

																						<div style="padding: 5px; border: 1px solid #dcdcdc;">
																							<table width="100%" border="0" cellspacing="0" cellpadding="3">
																								<tr>
																									<td class="jc_input_label" width="100px">Section</td>
																									<td>
																										<div id="sectionShortTitle_container">
																											<c:out value='${menuEditCommand.sectionShortTitle}' />
																										</div>
																									</td>
																								</tr>
																								<tr>
																									<td nowrap>
																										<a href="javascript:void(0);" onclick="showSectionPickWindow();" class="jc_navigation_link">
																											Pick Section
																										</a>
																									</td>
																									<td>
																									</td>
																								</tr>
																							</table>
																						</div>
																					</div>
																				</td>
																			</tr>
																			<tr>
																				<td>
																					<form:radiobutton path="menuType" value="CONT" onclick="expand(2)"/>
																					Content
																					<div id="location-2" style="display: none; padding: 10px 20px 10px 20px">
																						<form:hidden path="contentId" />
																						<form:hidden path="contentTitle" />
																						<div style="padding: 5px; border: 1px solid #dcdcdc;">
																							<table width="100%" border="0" cellspacing="0" cellpadding="3">
																								<tr>
																									<td class="jc_input_label" width="100px">
																										Content
																									</td>
																									<td>
																										<div id="contentTitle_container">
																											<c:out value='${menuEditCommand.contentTitle}' />
																										</div>
																									</td>
																								</tr>
																								<tr>
																									<td nowrap>
																										<a href="javascript:void(0);" onclick="showContentPickWindow();" class="jc_navigation_link">
																											Pick Content
																										</a>
																									</td>
																									<td>
																									</td>
																								</tr>
																							</table>
																						</div>
																					</div>
																				</td>
																			</tr>
																			<tr>
																				<td>
																					<form:radiobutton path="menuType" value="ITEM" onclick="expand(3)"/>
																					Item
																					<div id="location-3" style="display: none; padding: 10px 20px 10px 20px">
																						<form:hidden path="itemId" />
																						<form:hidden path="itemNum" />
																						<form:hidden path="itemShortDesc" />
																						<div style="padding: 5px; border: 1px solid #dcdcdc;">
																							<table width="100%" border="0" cellspacing="0" cellpadding="3">
																								<tr>
																									<td class="jc_input_label" width="100px" nowrap>
																										Item Number
																									</td>
																									<td width="100%">
																										<div id="itemNum_container">
																											<c:out value='${menuEditCommand.itemNum}' />
																										</div>
																									</td>
																								</tr>
																								<tr>
																									<td class="jc_input_label">Description</td>
																									<td>
																										<div id="itemShortDesc_container">
																											<c:out value='${menuEditCommand.itemShortDesc}' />
																										</div>
																									</td>
																								</tr>
																								<tr>
																									<td nowrap>
																										<a href="javascript:void(0);"
																											onclick="showItemPickWindow();" class="jc_navigation_link">
																											Pick item
																										</a>
																									</td>
																									<td>
																									</td>
																								</tr>
																							</table>
																						</div>
																					</div>
																				</td>
																			</tr>
																			<tr>
																				<td>
																					<form:radiobutton path="menuType" value="SURL" onclick="expand(4)"/>
																					Static URL
																					<div id="location-4" style="display: none; padding: 10px 20px 10px 20px">
																						<div style="padding: 5px; border: 1px solid #dcdcdc;">
																							<table width="100%" border="0" cellspacing="0" cellpadding="3">
																								<tr>
																									<td class="jc_input_label" width="100px" nowrap>
																										URL
																									</td>
																									<td width="100%">
																										<form:input path="menuUrl" cssClass="tableContent" size="80" maxlength="255"/>
																									</td>
																								</tr>
																							</table>
																						</div>
																					</div>
																				</td>
																			</tr>
																			<tr>
																				<td>
																					<form:radiobutton path="menuType" value="HOME" onclick="expand(5)"/>
																					Home
																					<div id="location-5" style="display: none;">
																					</div>
																				</td>
																			</tr>
																			<tr>
																				<td>
																					<form:radiobutton path="menuType" value="CTUS" onclick="expand(6)"/>
																					Contact Us
																					<div id="location-6" style="display: none;">
																					</div>
																				</td>
																			</tr>
																			<tr>
																				<td>
																					<form:radiobutton path="menuType" value="SIGI" onclick="expand(7)"/>
																					Sign in
																					<div id="location-7" style="display: none;">
																					</div>
																				</td>
																			</tr>
																			<tr>
																				<td>
																					<form:radiobutton path="menuType" value="SIGO" onclick="expand(8)"/>
																					Sign Out
																					<div id="location-8" style="display: none;">
																					</div>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr>
																	<td width="0" class="jc_input_label">&nbsp;</td>
																</tr>
																<tr>
																	<td class="jc_input_label">Window Mode</td>
																</tr>
																<tr>
																	<td class="jc_input_control">
																		<form:input path="menuWindowMode" cssClass="tableContent" size="80" maxlength="128"/>
																	</td>
																</tr>
																<tr>
																	<td class="jc_input_label">Window Target</td>
																</tr>
																<tr>
																	<td class="jc_input_control" nowrap>
																		<form:select path="menuWindowTarget" cssClass="tableContent">
																			<form:option value="_self">Current window (_self)</form:option>
																			<form:option value="_blank">New window (_blank)</form:option>
																			<form:option value="_parent">Parent Window (_parent)</form:option>
																			<form:option value="_top">Top of the frameset (_top)</form:option>
																		</form:select>
																	</td>
																</tr>
																<tr>
																	<td class="jc_input_label" width="0">Published</td>
																</tr>
																<tr>
																	<td class="jc_input_label" width="0">
																		<form:checkbox path="published" value="Y"/>
																	</td>
																</tr>
															</table>
														</td>
														<td>
														</td>
													</tr>
												</table>
											</td>
										</c:if>
										<c:if test="${menuEditCommand.sequence == true}">
											<td valign="top" width="100%"><br>
												<table width="100%" border="0" cellspacing="0" cellpadding="1">
													<tr>
														<td class="jc_list_table_header" width="100">
															<div align="center">
																<a href="javascript:submitRemoveSelected()" class="jc_submit_link">Remove</a>
															</div>
														</td>
														<td class="jc_list_table_header">Name</td>
														<td class="jc_list_table_header" width="100">
															<div align="center">
																<a href="javascript:submitResequence()" class="jc_submit_link">Resequence</a>
															</div>
														</td>
														<td class="jc_list_table_header" width="100">Published</td>
													</tr>
													<%
														int index = 0;
													%>
													<c:forEach var="childrenMenu" items="${menuEditCommand.childrenMenus}">
														<tr class="jc_list_table_row">
															<td width="100" class="jc_list_table_content">
																<div align="center">
																	<input type="hidden" value="${childrenMenu.menuId}">
																	<input type="hidden" value="${childrenMenu.menuName}">
																	<input type="hidden" value="${childrenMenu.menuUrlOrContent}">
																	<input type="hidden" value="${childrenMenu.menuWindowTarget}">
																	<input type="hidden" value="${childrenMenu.menuWindowMode}">
																	<input type="hidden" value="${childrenMenu.published}">
																	<input type="checkbox" value="${childrenMenu.remove}" class="jc_input_control" />
																	<!-- <html:hidden indexed="true" name="childrenMenu" property="published" />
																	<html:checkbox indexed="true" name="childrenMenu" property="remove" /> -->
																</div>
															</td>
															<td class="jc_list_table_content" nowrap>
																<c:out value="${childrenMenu.menuName}" />
															</td>
															<td width="100" class="jc_list_table_content" nowrap>
																<div align="center">
																	<!-- <html:text indexed="true" name="childrenMenu" property="seqNum" size="2" /> -->
																	<input type="text" value="${childrenMenu.seqNum}" class="tableContent" size="2" />
																	<%-- <form:input path="${childrenMenu.seqNum}" size="2"/> --%>
																	<span class="jc_input_error">
																		<form:errors value="seqNum_<%= index %>" cssClass="error" />
																	</span>
																</div>
															</td>
															<td width="100" class="jc_list_table_content" nowrap>
																<div align="center">
																	<c:out value="${childrenMenu.published}" />
																</div>
															</td>
														</tr>
														<%
															index++;
														%>
													</c:forEach>
												</table>
											</td>
										</c:if>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</c:if>
			</td>
		</tr>
	</table>
</form:form>

<%@ include file="/html/controlpanel/menu/panel.jsp"%>