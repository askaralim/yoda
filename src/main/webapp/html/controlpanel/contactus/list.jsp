<%@ include file="/html/common/init.jsp"%>

<jsp:useBean id="contactUsListCommand" type="com.yoda.contactus.ContactUsListCommand" scope="request" />

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

<form:form name="fm" modelAttribute="contactUsListCommand" method="post">
	<!-- <html:hidden property="process" value="list" /> -->
	<form:hidden path="srPageNo" />

	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<spring:message code="search" />
				</div>
				<div class="panel-body">
					<!-- <input type="button" value="New" class="jc_submit_button" onclick="return submitNewForm();"> -->

					<div class="form-group">
						<label for="srContactUsName">Contact Us Name</label>
						<form:input path="srContactUsName" cssClass="form-control" />
					</div>
					<div class="form-group">
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
					</div>

					<input type="submit" value='<spring:message code="search" />' class="btn btn-sm btn-primary" role="button" onclick="return submitSearch();">
				</div>
			</div>
		</div>
		<div class="col-md-9">
			<div class="page-header">
				<h4>Contact Us Listing Result</h4>
			</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<c:if test="${contactUsListCommand.contactUsCommands != null}">
						<div class="text-right">
							<input type="submit" value="<spring:message code="new" />" class="btn btn-sm btn-primary" role="button" onclick="return submitNewForm();">
							<input type="submit" value="<spring:message code="remove" />" class="btn btn-sm btn-default" role="button" onclick="return submitRemove();">
						</div>
					</c:if>
					<thead>
						<tr>
							<th></th>
							<th>ID</th>
							<th><a class="jc_submit_link" href="javascript:submitResequence()">Resequence</a></th>
							<th>Contact Us Name</th>
							<th>Active</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="contactUs" items="${contactUsListCommand.contactUsCommands}">
							<spring:url value="/controlpanel/contactus/{contactUsId}/edit" var="editContactUsUrl">
									<spring:param name="contactUsId" value="${contactUs.contactUsId}"/>
								</spring:url>
							<tr>
								<td>
									<input type="checkbox" id="contantUsIds" value="${contactUs.contactUsId}">
								</td>
								<td>
									<a href="${fn:escapeXml(editContactUsUrl)}">
										<c:out value="${contactUs.contactUsId}" />
									</a>
								</td>
								<td>
									<div class="col-md-2">
										<input class="form-control" name="seqNum" value="${contactUs.seqNum}">
									</div>
								</td>
								<td>
									<a href="${fn:escapeXml(editContactUsUrl)}">
										<c:out value="${contactUs.contactUsName}" />
									</a>
								</td>
								<td>
									<input type="hidden" value="${contactUs.active}">
									<!-- <html:hidden indexed="true" name="contactUs" property="active" /> -->
									<c:out value="${contactUs.active}" />
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
</form:form>