<%@ include file="/html/common/init.jsp"%>

<script type="text/javascript">
function submitNew() {
	location.href = '<c:url value="/controlpanel/user/add"/>';

	return false;
}

function submitSearch() {
	document.fm.action = '<c:url value="/controlpanel/user/list/search"/>';
	document.fm.submit();
	return false;
}

function getSelectedUserIds(){
	var selectBoxs = document.all("userIds");

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

function submitRemove() {
	var ids = getSelectedUserIds();

	if(ids){
		var url = '<c:url value="/controlpanel/user/list/remove"/>?userIds='+ids+'';
		location.href = url;
	}

	//document.fm.action = '<c:url value="/controlpanel/user/list/remove"/>';
	//document.fm.submit();
	return false;
}
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/user/list" htmlEscape="true" />">User Listing</a></li>
</ol>

<form:form name="fm" modelAttribute="userListCommand" method="post">
	<!-- <html:hidden property="process" value="search" /> -->
	<form:hidden path="srPageNo" />

	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<spring:message code="search" />
				</div>
				<div class="panel-body">
					<div class="form-group">
						<label for=userId><spring:message code="id" /></label>
						<form:input path="userId" cssClass="form-control" value="" />
					</div>
					<div class="form-group">
						<label for=userName><spring:message code="username" /></label>
						<form:input path="userName" cssClass="form-control" />
					</div>
					<div class="form-group">
						<label>User Type</label>
						<div class="radio">
							<label>
								<form:radiobutton path="type" value="S" />
								<spring:message code="user.type.S" />
							</label>
						</div>
						<div class="radio">
							<label>
								<form:radiobutton path="type" value="A" />
								<spring:message code="user.type.A" />
							</label>
						</div>
						<div class="radio">
							<label>
								<form:radiobutton path="type" value="R" />
								<spring:message code="user.type.R" />
							</label>
						</div>
						<div class="radio">
							<label>
								<form:radiobutton path="type" value="*" />
								<spring:message code="user.type.*" />
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="title">Active</label>
						<div class="radio">
							<label>
								<form:radiobutton path="active" value="Y" />
								Active
							</label>
						</div>
						<div class="radio">
							<label>
								<form:radiobutton path="active" value="N" />
								In-active
							</label>
						</div>
						<div class="radio">
							<label>
								<form:radiobutton path="active" value="*" />
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
				<h4>User Listing Result</h4>
			</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<c:if test="${userListCommand.users != null}">
						<div class="text-right">
							<input type="submit" value="<spring:message code="new" />" class="btn btn-sm btn-primary" role="button" onclick="return submitNew();">
							<input type="submit" value="<spring:message code="remove" />" class="btn btn-sm btn-default" role="button" onclick="return submitRemove();">
						</div>
						<thead>
							<tr>
								<th></th>
								<th><spring:message code="id" /></th>
								<th><spring:message code="username" /></th>
								<th>User Type</th>
								<th>Active</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${userListCommand.users}">
								<tr>
									<td>
										<%-- <form:checkbox path="${users.userId}" id="removeUserId" label="" value="${user.userId}" /> --%>
										<input type="checkbox" id="userIds" value="${user.userId}">
										<%-- <input type="hidden" value="${user.userId}"> --%>
										<!-- <html:checkbox indexed="true" name="user" property="remove" />
										<html:hidden indexed="true" name="user" property="userId" /> -->
									</td>
									<td>
										<spring:url value="/controlpanel/user/{userId}/edit" var="editUserUrl">
											<spring:param name="userId" value="${user.userId}"/>
										</spring:url>
										<a href="${fn:escapeXml(editUserUrl)}">
											<c:out value="${user.userId}" />
										</a>
									</td>
									<td>
										<input type="hidden" value="${user.userName}">
										<c:out value="${user.userName}" />
									</td>
									<td>
										<spring:message code="${user.type}" />
									</td>
									<td>
										<input type="hidden" value="${user.active}">
										<!-- <html:hidden indexed="true" name="user" property="active" /> -->
										<c:out value="${user.active}" />
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:if>
				</table>
			</div>
		</div>
	</div>

	<%-- <c:if test="${userListCommand.pageNo > 1}">
		<td>
			<spring:url value="/controlpanel/user/list/search/{srPageNo}" var="srPageNoUrl">
				<spring:param name="srPageNo" value="${userListCommand.pageNo - 1}"/>
			</spring:url>
			<a class="jc_navigation_link" href="${fn:escapeXml(srPageNoUrl)}">
				&lt;
			</a>
			<a class="jc_navigation_link"
			href="<c:out value='/${adminBean.contextPath}'/>/admin/user/userListing.do?process=list&srPageNo=<c:out value="${userListCommand.pageNo - 1}"/>">&lt;</a>
		</td>
	</c:if>
	<td>
		<c:forEach begin="${userListCommand.startPage}" end="${userListCommand.endPage}" var="index">
			<c:choose>
				<c:when test="${index == userListCommand.pageNo}">
					<span class="jc_navigation_line">
						<c:out value="${index}" />
					</span>
				</c:when>
				<c:otherwise>
					<spring:url value="/controlpanel/user/list/search/{srPageNo}" var="srPageNoUrl2">
						<spring:param name="srPageNo" value="${index}"/>
					</spring:url>
					<a class="jc_navigation_link" href="${fn:escapeXml(srPageNoUrl2)}">
						<c:out value="${index}" />
					</a>
					<a class="jc_navigation_link"
						href="<c:out value='/${adminBean.contextPath}'/>/admin/user/userListing.do?process=list&srPageNo=<c:out value='${index}'/>">
						<c:out value="${index}" />
					</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		&nbsp;
	</td>
	<c:if test="${userListCommand.pageNo < userListCommand.pageCount}">
		<td>
			<spring:url value="/controlpanel/user/list/search/{srPageNo}" var="srPageNoUrl3">
				<spring:param name="srPageNo" value="${userListCommand.pageNo + 1}"/>
			</spring:url>
			<a class="jc_navigation_link" href="${fn:escapeXml(srPageNoUrl3)}">
				&gt;
			</a>
			<a class="jc_navigation_link"
				href="<c:out value='/${adminBean.contextPath}'/>/admin/user/userListing.do?process=list&srPageNo=<c:out value="${userListCommand.pageNo + 1}"/>">
				&gt;
			</a>
		</td>
	</c:if> --%>
</form:form>