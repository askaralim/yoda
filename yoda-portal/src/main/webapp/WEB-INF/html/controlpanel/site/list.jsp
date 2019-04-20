<%@ include file="../../common/init.jsp"%>

<%-- <jsp:useBean id="siteListCommand" type="com.yoda.site.SiteListCommand" scope="request" /> --%>

<script type="text/javascript">
function submitNew() {
	location.href = '<c:url value="/controlpanel/site/add"/>';

	return false;
}

function submitSearch() {
	document.fm.action = '<c:url value="/controlpanel/site/list/search"/>';
	document.fm.submit();
	return false;
}

function submitRemove() {
	document.fm.action = '<c:url value="/controlpanel/site/list/remove"/>';
	document.fm.submit();
	return false;
}

function submitRemove() {
	var ids = getSelectedContentIds();

	if(ids){
		var url = '<c:url value="/controlpanel/site/list/remove"/>?siteIds='+ids+'';
		location.href = url;
	}

	return false;
}

function getSelectedContentIds(){
	var selectBoxs = document.all("siteIds");

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
	<li><a href="<spring:url value="/controlpanel/site/list" />">Site Listing</a></li>
</ol>

<%-- <form:form name="fm" modelAttribute="siteListCommand" method="post"> --%>
	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<spring:message code="search" />
				</div>
				<div class="panel-body">
					<div class="form-group">
						<label for="siteId"><spring:message code="id" /></label>
						<input id="siteId" name="siteId" type="text" class="form-control">
					</div>
					<div class="form-group">
						<label for="siteName"><spring:message code="site-name" /></label>
						<input id="siteName" name="siteName" type="text" class="form-control">
					</div>
					<div class="form-group">
						<label for="title">Active</label>
						<div class="radio">
							<label>
								<input type="radio" name="active" id="active" value="1">
								Active
							</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="active" id="active" value="0">
								In-active
							</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="active" id="active" value="all" checked>
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
				<h4>Site Listing Result</h4>
			</div>
			<div class="text-right">
				<input type="submit" value="<spring:message code="new" />" class="btn btn-sm btn-primary" role="button" onclick="return submitNew();">
				<input type="submit" value="<spring:message code="remove" />" class="btn btn-sm btn-default" role="button" onclick="return submitRemove();">
			</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<c:if test="${sites != null}">
						<thead>
							<tr>
								<th></th>
								<th><spring:message code="id" /></th>
								<th><spring:message code="site-name" /></th>
								<th>Public Domain Name</th>
								<th>Secure Domain Name</th>
								<th>Active</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="site" items="${sites}">
								<spring:url value="/controlpanel/site/{siteId}/edit" var="editSiteUrl">
									<spring:param name="siteId" value="${site.siteId}"/>
								</spring:url>
								<tr>
									<td>
										<c:choose>
											<c:when test="${site.siteId == 1}">
												<input type="checkbox" id="siteIds" value="${site.siteId}" disabled>
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="siteIds" value="${site.siteId}">
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<a href="${fn:escapeXml(editSiteUrl)}">
											<c:out value="${site.siteId}" />
										</a>
									<td>
										<a href="${fn:escapeXml(editSiteUrl)}">
											<c:out value="${site.siteName}" />
										</a>
									</td>
									<td>
										<c:out value="${site.domainName}" />
										<c:if test="${site.publicPort != null}">
											: <c:out value="${site.publicPort}" />
										</c:if>
									</td>
									<td>
										<c:out value="${site.domainName}" />
										<c:if test="${site.securePort != null}">
											: <c:out value="${site.securePort}" />
										</c:if>
									</td>
									<td>
										<c:out value="${site.active}" />
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:if>
				</table>
			</div>
		</div>
	</div>
<%-- </form:form> --%>