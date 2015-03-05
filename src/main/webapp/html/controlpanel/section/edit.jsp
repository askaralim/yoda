<%@ include file="/html/common/init.jsp"%>

<%@ page language="java" import="com.yoda.section.DropDownMenuGenerator" %>

<script type="text/javascript">
function submitForm(methodType) {
	document.forms[0].action = '/<c:out value='${adminBean.contextPath}'/>/admin/section/sectionMaint1.do';
	document.forms[0].process.value = methodType;
	document.forms[0].submit();
	return false;
}

function submitSave() {
	document.fm.action = '<c:url value="/controlpanel/section/save"/>';
	document.fm.submit();
	//return true;
	return false;
}

function submitRemove() {
	document.fm.action = '<c:url value="/controlpanel/section/remove"/>';
	document.fm.submit();
	//return true;
	return false;
}

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
</script>

<jsp:useBean id="sectionEditCommand" type="com.yoda.section.SectionEditCommand" scope="request" />

<form:form modelAttribute="sectionEditCommand" name="fm" method="post">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="jc_top_navigation">
		<tr>
			<td>Administration - Section</td>
		</tr>
	</table>
	<br>
	<table width="0" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="0%" valign="top">
				<div class="jc_search_panel_open">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="jc_panel_table_header">Sections</td>
						</tr>
					</table>
					<br>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<form:hidden path="createSectionId" />
								<form:hidden path="createMode" />
								<form:hidden path="sectionId" />
								<div id="home" style="cursor: hand; cursor: pointer">
									<div style="display: none">
										<c:out value="${sectionEditCommand.sectionTree.menuKey}" />
									</div>
									<c:out value="${sectionEditCommand.sectionTree.menuName}" /> - System default
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div id="sectionLocation"></div>
								<%= DropDownMenuGenerator.generate1(
										sectionEditCommand.getSectionTree(), null, "sectionTree", DropDownMenuGenerator.SELECTION_NONE, "sectionId",
										request.getContextPath() + "/controlpanel/section/update", "sectionId") %>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
				</div>
			</td>
			<td width="20px"></td>
			<td width="100%" valign="top">
				<c:if test="${sectionEditCommand.mode != ''}">
					<table width="100%" border="0" cellspacing="0" cellpadding="2">
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="2">
									<tr>
										<td class="jc_panel_table_header" valign="top">
											<c:if test="${sectionEditCommand.sequence == false}">
												<div align="right">
													<input type="submit" value="Save" class="jc_submit_button" onclick="return submitSave();">
													<input type="submit" value="Remove" class="jc_submit_button" onclick="return submitRemove();">
												</div>
											</c:if>
											<c:if test="${sectionEditCommand.sequence == true}">
												Section - 
												<c:out value="${sectionEditCommand.sectionShortTitle}" />
											</c:if>
										</td>
									</tr>
									<tr>
										<c:if test="${sectionEditCommand.sequence == false}">
											<td valign="top">
												<table width="100%" border="0" cellspacing="0" cellpadding="2">
													<tr>
														<td>
															<table width="100%" border="0" cellspacing="0" cellpadding="2">
																<tr>
																	<td width="0" class="jc_input_label">
																		Section short title
																	</td>
																</tr>
																<tr>
																	<td>
																		<form:input path="sectionShortTitle" cssClass="tableContent" size="20" maxlength="20" />
																	</td>
																</tr>
																<tr>
																	<td class="jc_input_label">
																		<span class="jc_input_error">
																			<form:errors path="sectionShortTitle" cssClass="error" />
																			<!-- <logic:messagesPresent property="sectionShortTitle" message="true">
																				<html:messages property="sectionShortTitle" id="errorText" message="true">
																					<bean:write name="errorText" />
																				</html:messages>
																			</logic:messagesPresent> -->
																		</span>
																	</td>
																</tr>
																<tr>
																	<td class="jc_input_label">
																		Title
																	</td>
																</tr>
																<tr>
																	<td class="jc_input_control">
																		<form:input path="sectionTitle" cssClass="tableContent" size="40" maxlength="40" />
																	</td>
																</tr>
																<tr>
																	<td class="jc_input_label">
																		Description
																	</td>
																</tr>
																<tr>
																	<td class="jc_input_control">
																		<form:textarea path="sectionDesc" cssClass="tableContent" rows="5" cols="45" />
																	</td>
																</tr>
																<tr>
																	<td class="jc_input_label" width="0">
																		Published
																	</td>
																</tr>
																<tr>
																	<td class="jc_input_label" width="0">
																		<form:checkbox path="published" value="Y" mode="E,E,E" layout="false" />
																	</td>
																</tr>
															</table>
														</td>
														<td></td>
													</tr>
												</table>
											</td>
										</c:if>
										<c:if test="${sectionEditCommand.sequence == true}">
											<td valign="top" width="100%">
												<table width="100%" border="0" cellspacing="0" cellpadding="1">
													<tr>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td class="jc_list_table_header" width="100">
															<div align="center">
																<a href="javascript:submitRemoveSelected()" class="jc_submit_link">Remove</a>
															</div>
														</td>
														<td class="jc_list_table_header" nowrap>
															Short Title
														</td>
														<td class="jc_list_table_header" width="100">
															<div align="center">
																<a href="javascript:submitResequence()" class="jc_submit_link">Resequence</a>
															</div>
														</td>
														<td class="jc_list_table_header" width="100">
															<div align="center">
																Published
															</div>
														</td>
													</tr>
													<%
														int index = 0;
													%>
													<c:forEach var="childrenSection" items="${sectionEditCommand.childrenSections}">
														<tr class="jc_list_table_row">
															<td width="100" class="jc_list_table_content">
																<input type="hidden" value="${childrenSection.sectionId}">
																<input type="hidden" value="${childrenSection.sectionShortTitle}">
																<input type="hidden" value="${childrenSection.sectionTitle}">
																<input type="hidden" value="${childrenSection.sectionDesc}">
																<input type="hidden" value="${childrenSection.published}">
																<!-- <html:hidden indexed="true" name="childrenSection" property="published" /> -->
																<input type="checkbox" value="${childrenSection.remove}" class="jc_input_control" />
																<div align="center">
																	<!-- <html:checkbox indexed="true" name="childrenSection" property="remove" /> -->
																</div>
															</td>
															<td nowrap class="jc_list_table_content">
																<c:out value="${childrenSection.sectionShortTitle}" />
															</td>
															<td width="100" class="jc_list_table_content" nowrap>
																<div align="center">
																	<input type="text" value="${childrenSection.seqNum}" class="tableContent" size="2" />
																	<!-- <html:text indexed="true" name="childrenSection" property="seqNum" size="2" /> -->
																	<span class="jc_input_error">
																		<form:errors value="seqNum_<%= index %>" cssClass="error" />
																		<%-- <logic:messagesPresent property="seqNum_<%=index%>" message="true">
																			<html:messages property="seqNum_<%=index%>" id="errorText" message="true">
																				<bean:write name="errorText" />
																			</html:messages>
																		</logic:messagesPresent> --%>
																	</span>
																</div>
															</td>
															<td width="100" class="jc_list_table_content" nowrap>
																<div align="center">
																	<c:out value="${childrenSection.published}" />
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

<script language="JavaScript">
	jc_create_yui_treemenu('sectionTree', 'sectionLocation');

	function addChildNode() {
		document.fm.createSectionId.value = sectionId;
		document.fm.createMode.value = "C";
		document.fm.action = '<c:url value="/controlpanel/section/create"/>';
		document.fm.submit();
	}

	function createBeforeNode() {
		document.fm.createSectionId.value = sectionId;
		document.fm.createMode.value = "B";
		document.fm.action = '<c:url value="/controlpanel/section/create"/>';
		document.fm.submit();
	}

	function createAfterNode() {
		document.fm.createSectionId.value = sectionId;
		document.fm.createMode.value = "A";
		document.fm.action = '<c:url value="/controlpanel/section/create"/>';
		document.fm.submit();
	}

	function sequenceNode() {
		document.fm.sectionId.value = sectionId;
		document.fm.createMode.value = "A";
		document.fm.action = '<c:url value="/controlpanel/section/showsequence"/>';
		document.fm.submit();
	}

	var sectionId = null;

	function sequenceHomeNode() {
		document.fm.sectionId.value = sectionId;
		document.fm.createMode.value = "A";
		document.fm.action = '<c:url value="/controlpanel/section/showsequence"/>';
		document.fm.submit();
	}

	function getKey(oTextNode) {
		var key = "";
		var nodes = oTextNode.childNodes;
		for (var i = 0; i < nodes.length; i++) {
			if (nodes[i].tagName && nodes[i].tagName.toUpperCase() == 'DIV') {
				key = nodes[i].innerHTML;
			}
		}
		return key;
	}

	function onTriggerContextMenu(p_oEvent) {
		function getTextNodeFromEventTarget(p_oTarget) {
			//        if (p_oTarget.tagName.toUpperCase() == "A" && p_oTarget.className == "ygtvlabel") {
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
			sectionId = getKey(oTextNode);
		} else {
			this.cancel();
		}
	}

	function onTriggerHomeMenu(p_oEvent) {
		var object = this.contextEventTarget;
		var nodes = object.childNodes;
		for (var i = 0; i < nodes.length; i++) {
			if (nodes[i].tagName && nodes[i].tagName.toUpperCase() == 'DIV') {
				sectionId = nodes[i].innerHTML;
				return;
			}
		}
	}

	var oContextMenu = new YAHOO.widget.ContextMenu("contextMenu", {
		trigger : "sectionLocation",
		lazyload : true,
		itemdata : [ {
			text : "Append Child Section",
			onclick : {
				fn : addChildNode
			}
		}, {
			text : "Create section before",
			onclick : {
				fn : createBeforeNode
			}
		}, {
			text : "Create section after",
			onclick : {
				fn : createAfterNode
			}
		}, {
			text : "Resequence/remove children",
			onclick : {
				fn : sequenceNode
			}
		} ]
	});
	oContextMenu.subscribe("triggerContextMenu", onTriggerContextMenu);

	var homeContextMenu = new YAHOO.widget.ContextMenu("contextMenu1", {
		trigger : "home",
		lazyload : true,
		itemdata : [ {
			text : "Append Child Section",
			onclick : {
				fn : addChildNode
			}
		}, {
			text : "Resequence/remove children",
			onclick : {
				fn : sequenceHomeNode
			}
		} ]
	});
	homeContextMenu.subscribe("triggerContextMenu", onTriggerHomeMenu);
</script>
