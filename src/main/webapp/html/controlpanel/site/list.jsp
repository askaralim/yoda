<%@ include file="/html/common/init.jsp"%>

<jsp:useBean id="siteListCommand" type="com.yoda.site.SiteListCommand" scope="request" />

<script type="text/javascript">
function submitNew() {
	document.fm.action = '<c:url value="/controlpanel/site/add"/>';
	document.fm.submit();
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
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/site/list" />">Site Listing</a></li>
</ol>

<form:form name="fm" modelAttribute="siteListCommand" method="post">
	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<spring:message code="search" />
				</div>
				<div class="panel-body">
					<!-- <input type="button" value="New" class="jc_submit_button" onclick="return submitNewForm();"> -->

					<div class="form-group">
						<label for="siteId"><spring:message code="id" /></label>
						<form:input path="siteId" cssClass="form-control" value="" />
					</div>
					<div class="form-group">
						<label for="siteName"><spring:message code="site-name" /></label>
						<form:input path="siteName" cssClass="form-control" />
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
				<h4>Site Listing Result</h4>
			</div>
			<div class="text-right">
				<input type="submit" value="<spring:message code="new" />" class="btn btn-sm btn-primary" role="button" onclick="return submitNew();">
				<input type="submit" value="<spring:message code="remove" />" class="btn btn-sm btn-default" role="button" onclick="return submitRemove();">
			</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<c:if test="${siteListCommand.sites != null}">
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
							<c:forEach var="site" items="${siteListCommand.sites}">
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
										<c:out value="${site.publicDomainNamePort}" />
									</td>
									<td>
										<c:out value="${site.secureDomainNamePort}" />
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
</form:form>