<%@ include file="/html/common/init.jsp"%>

<script type="text/javascript">
function submitNewForm() {
	location.href = '<c:url value="/controlpanel/contactus/add"/>';

	return false;
}

function submitRemove() {
	var ids = getSelectedContactUsIds();

	if(ids){
		var url = '<c:url value="/controlpanel/contactus/list/remove"/>?contactUsIds='+ids+'';
		location.href = url;
	}

	return false;
}

function getSelectedContactUsIds(){
	var selectBoxs = document.all("contantUsIds");

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

function submitSearch() {
	document.fm.action = '<c:url value="/controlpanel/contactus/list/search"/>';
	document.fm.method="POST";
	//document.forms[0].srPageNo.value = page;
	document.fm.submit();

	return false;
}

function submitResequence() {
	document.fm.action = '<c:url value="/controlpanel/contactus/list/resequence"/>';
	document.fm.submit();

	return false;
}
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/contactus/list" htmlEscape="true" />">Contact Us Listing</a></li>
</ol>

<%-- <form:form name="fm" modelAttribute="searchForm" method="post"> --%>
	<!-- <html:hidden property="process" value="list" /> -->
	<%-- <form:hidden path="srPageNo" /> --%>

	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<spring:message code="search" />
				</div>
				<div class="panel-body">
					<div class="form-group">
						<label for="name"><spring:message code="name" /></label>
						<input id="name" name="name" type="text" class="form-control" />
					</div>
					<%-- <div class="form-group">
						<div class="radio">
							<label>
								<form:radiobutton path="srActive" value="Y"/>
								Active
							</label>
						</div>
						<div class="radio">
							<label>
								<form:radiobutton path="srActive" value="N"/>
								In-active
							</label>
						</div>
						<div class="radio">
							<label>
								<form:radiobutton path="srActive" value="*"/>
								All
							</label>
						</div>
					</div> --%>

					<input type="submit" value='<spring:message code="search" />' class="btn btn-sm btn-primary" role="button" onclick="return submitSearch();">
				</div>
			</div>
		</div>
		<div class="col-md-9">
			<div class="page-header">
				<h4>Contact Us Listing Result</h4>
			</div>
			<div class="text-right">
				<input type="submit" value="<spring:message code="new" />" class="btn btn-sm btn-primary" role="button" onclick="return submitNewForm();">
				<input type="submit" value="<spring:message code="remove" />" class="btn btn-sm btn-default" role="button" onclick="return submitRemove();">
			</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th></th>
							<th><spring:message code="id" /></th>
							<th><a class="jc_submit_link" href="javascript:submitResequence()"><spring:message code="resequence" /></a></th>
							<th><spring:message code="name" /></th>
							<th><spring:message code="resequence" /></th>
							<th><spring:message code="action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="contactUs" items="${contactUsList}">
							<tr>
								<td>
									<input type="checkbox" id="contantUsIds" value="${contactUs.contactUsId}">
								</td>
								<td>
									<c:out value="${contactUs.contactUsId}" />
								</td>
								<td>
									<input class="form-control" name="seqNum" value="${contactUs.seqNum}">
								</td>
								<td>
									<c:out value="${contactUs.name}" />
								</td>
								<td>
									<c:out value="${contactUs.active}" />
								</td>
								<td>
									<spring:url value="/controlpanel/contactus/{contactUsId}/edit" var="editContactUsUrl">
										<spring:param name="contactUsId" value="${contactUs.contactUsId}"/>
									</spring:url>
									<a href="${fn:escapeXml(editContactUsUrl)}">
										<spring:message code="edit" />
									</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
						<%-- <table width="0%" border="0" cellspacing="0" cellpadding="0" class="jc_input_label">
							<tr>
								<c:if test="${contactUsListCommand.pageNo > 1}">
									<td>
										<spring:url value="/controlpanel/contactus/list/{srPageNo}" var="srPageNoUrl">
											<spring:param name="srPageNo" value="${contactUsListCommand.pageNo - 1}"/>
										</spring:url>
										<a class="jc_navigation_link" href="${fn:escapeXml(srPageNoUrl)}">
											&lt;
										</a>
									</td>
								</c:if>
								<td>
									<c:forEach begin="${contactUsListCommand.startPage}" end="${contactUsListCommand.endPage}" var="index">
										<c:choose>
											<c:when test="${index == contactUsListCommand.pageNo}">
												<span class="jc_navigation_line">
													&lt;
												</span>
											</c:when>
											<c:otherwise>
												<spring:url value="/controlpanel/contactus/list/{srPageNo}" var="srPageNoUrl">
													<spring:param name="srPageNo" value="${index}"/>
												</spring:url>
												<a class="jc_navigation_link" href="${fn:escapeXml(srPageNoUrl)}">
													<c:out value="${index}" />
												</a>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									&nbsp;
								</td>
								<c:if test="${contactUsListCommand.pageNo < contactUsListCommand.pageCount}">
									<td>
										<spring:url value="/controlpanel/contactus/list/{srPageNo}" var="srPageNoUrl">
											<spring:param name="srPageNo" value="${contactUsListCommand.pageNo + 1}"/>
										</spring:url>
										<a class="jc_navigation_link" href="${fn:escapeXml(srPageNoUrl)}">
											&gt;
										</a>
									</td>
								</c:if>
								<td>&nbsp;&nbsp;</td>
							</tr>
						</table> --%>
<%-- </form:form> --%>