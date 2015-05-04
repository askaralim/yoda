<%@ include file="/html/common/init.jsp" %>

<%@ page import="com.yoda.section.DropDownMenuGenerator" %>

<script type="text/javascript">
function submitNewForm() {
	var url = '<c:url value="/controlpanel/content/add"/>';
	location.href = url;

	return false;
}

function submitSearch() {
	document.fm.action = '<c:url value="/controlpanel/content/list/search"/>';
	document.fm.method="POST";
	document.fm.submit();
}

function submitRemove() {
	var ids = getSelectedContentIds();

	if(ids){
		var url = '<c:url value="/controlpanel/content/list/remove"/>?contentIds='+ids+'';
		location.href = url;
	}

	return false;
}

function getSelectedContentIds(){
	var selectBoxs = document.all("contentIds");

	if(!selectBoxs) return null;

	if(typeof(selectBoxs.length) == "undefined" && selectBoxs.checked){
		return selectBoxs.value;
	}
	else{//many checkbox ,so is a array 
		var ids = "";
		var split = "";
		for(var i = 0 ; i < selectBoxs.length ; i++){
			if(selectBoxs[i].checked){
				ids += split+selectBoxs[i].value;
				split = ",";
			}
		}

		return ids;
	}
}
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/content/list" />">Content Listing</a></li>
</ol>

<form:form modelAttribute="searchForm" name="fm" method="post">
	<%-- <form:hidden path="srPageNo" /> --%>

	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<spring:message code="search" />
				</div>
				<div class="panel-body">
					<!-- <input type="button" value="New" class="jc_submit_button" onclick="return submitNewForm();"> -->

					<div class="form-group">
						<label for="title"><spring:message code="title" /></label>
						<form:input path="title" cssClass="form-control" />
					</div>
					<div class="form-group">
						<div class="radio">
							<label>
								<form:radiobutton path="published" value="true"/>
								Published
							</label>
						</div>
						<div class="radio">
							<label>
								<form:radiobutton path="published" value="false"/>
								Not-Published
							</label>
						</div>
						<div class="radio">
							<label>
								<form:radiobutton path="published" value=""/>
								All
							</label>
						</div>
					</div>
					<%-- <div class="form-group">
						<label for="updateBy"><spring:message code="update-by" /></label>
						<form:select path="updateBy" cssClass="form-control">
							<form:option value="All" />
							<form:options items="${contentListCommand.selectUsers}" />
						</form:select>
					</div>
					<div class="form-group">
						<label for="createBy"><spring:message code="create-by" /></label>
						<form:select path="createBy" cssClass="form-control">
							<form:option value="All" />
							<form:options items="${contentListCommand.selectUsers}" />
						</form:select>
					</div> --%>
					<%-- <div class="form-group">
						<label for="createBy">Select sections to show</label>
						<div id="sectionLocation"></div>
						<%=  
						DropDownMenuGenerator.generate1(contentListCommand.getSectionTree(), contentListCommand.getSelectedSections(), "sectionTree", DropDownMenuGenerator.SELECTION_MULTIPLE, "selectedSections", null, null)
						%>
					</div> --%>
					<div class="form-group">
						<label for="publishDateStart"><spring:message code="publish-date" /></label>
						<div class="input-daterange input-group" id="datepicker">
							<form:input path="publishDateStart" cssClass="form-control" />
							<span class="input-group-addon">to</span>
							<form:input path="publishDateEnd" cssClass="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label for="expireDateStart"><spring:message code="expire-date" /></label>
						<div class="input-daterange input-group" id="datepicker">
							<form:input path="expireDateStart" cssClass="form-control" />
							<span class="input-group-addon">to</span>
							<form:input path="expireDateEnd" cssClass="form-control" />
						</div>
					</div>

					<input type="submit" value='<spring:message code="search" />' class="btn btn-sm btn-primary" role="button" onclick="return submitSearch();">
				</div>
			</div>
		</div>
		<div class="col-md-9">
			<div class="page-header">
				<h4>Content Listing Result</h4>
			</div>
			<div class="text-right">
				<input type="submit" value="<spring:message code="new" />" class="btn btn-sm btn-primary" role="button" onclick="return submitNewForm();">
				<input type="submit" value="<spring:message code="remove" />" class="btn btn-sm btn-default" role="button" onclick="return submitRemove();">
			</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<c:if test="${contents != null}">
						<thead>
							<tr>
								<th></th>
								<th><spring:message code="id" /></th>
								<th><spring:message code="title" /></th>
								<!-- <th>Section</th> -->
								<th><spring:message code="published" /></th>
								<th><spring:message code="publish-date" /></th>
								<th><spring:message code="expire-date" /></th>
								<th><spring:message code="hit-counter" /></th>
								<th><spring:message code="action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="content" items="${contents}">
								<tr>
									<td>
										<input type="checkbox" id="contentIds" value="${content.contentId}">
									</td>
									<td>
										<c:out value="${content.contentId}" />
									</td>
									<td>
										<c:out value="${content.title}" />
									</td>
									<%-- <td>
										<c:out value="${content.sectionName}" />
									</td> --%>
									<td>
										<c:out value="${content.published}" />
									</td>
									<td>
										<c:out value="${content.publishDate}" />
									</td>
									<td>
										<c:out value="${content.expireDate}" />
									</td>
									<td>
										<c:out value="${content.hitCounter}" />
									</td>
									<td>
										<spring:url value="/controlpanel/content/{contentId}/edit" var="editContentUrl">
											<spring:param name="contentId" value="${content.contentId}"/>
										</spring:url>
										<a href="${fn:escapeXml(editContentUrl)}">
											<spring:message code="edit" />
										</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:if>
					<%-- <tr>
						<td width="100%" valign="top" class="jc_list_panel_open">
							<c:if test="${contentListCommand.contents != null}">
								<div align="right">
									<table width="0%" border="0" cellspacing="0" cellpadding="0" class="jc_input_label">
										<tr>
											<c:if test="${contentListCommand.pageNo > 1}">
												<td>
													<a class="jc_navigation_link" href="<c:out value='/${adminBean.contextPath}'/>/admin/content/contentListing.do?process=list&srPageNo=<c:out value="${contentListCommand.pageNo - 1}"/>">&lt;</a>
												</td>
											</c:if>
											<td>
												<c:forEach begin="${contentListCommand.startPage}" end="${contentListCommand.endPage}" var="index">
													<c:choose>
														<c:when test="${index == contentListCommand.pageNo}">
															<span class="jc_navigation_line">
																<c:out value="${index}" />
															</span>
														</c:when>
														<c:otherwise>
															<a class="jc_navigation_link" href="<c:out value='/${adminBean.contextPath}'/>/admin/content/contentListing.do?process=list&srPageNo=<c:out value='${index}'/>">
																<c:out value="${index}" />
															</a>
														</c:otherwise>
													</c:choose>
												</c:forEach> &nbsp;
											</td>
											<c:if test="${contentListCommand.pageNo < contentListCommand.pageCount}">
												<td>
													<a class="jc_navigation_link" href="<c:out value='/${adminBean.contextPath}'/>/admin/content/contentListing.do?process=list&srPageNo=<c:out value="${contentListCommand.pageNo + 1}"/>">&gt;</a>
												</td>
											</c:if>
											<td>&nbsp;&nbsp;</td>
										</tr>
									</table>
								</div>
							</c:if>
						</td>
					</tr> --%>
				</table>
			</div>
		</div>
	</div>
</form:form>

<script src='<c:url value="/resources/js/jquery-1.11.1.min.js" />'></script>
<script src='<c:url value="/resources/js/datepicker/bootstrap-datepicker.js" />'></script>

<script type="text/javascript">
	$('.input-daterange').datepicker({
		format : "yyyy-mm-dd",
		weekStart : 1,
		language : "zh-CN",
		todayHighlight : true
	});
	/* jc_create_yui_treemenu('sectionTree', 'sectionLocation'); */
</script>