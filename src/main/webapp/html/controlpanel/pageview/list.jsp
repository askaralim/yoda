<%@ include file="/html/common/init.jsp" %>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/brand" />">Page View Listing</a></li>
</ol>

<div class="row">
	<div class="col-md-3">
		<div class="panel panel-default">
			<div class="panel-heading">
				<spring:message code="search" />
			</div>
			<div class="panel-body">
				<form name="fm">
					<div class="form-group">
						<label for="name"><spring:message code="name" /></label>
						<input id="name" name="name" type="text" class="form-control" />
					</div>
					<input type="submit" value='<spring:message code="search" />' class="btn btn-sm btn-primary" role="button" onclick="">
				</form>
			</div>
		</div>
	</div>
	<div class="col-md-9">
		<div class="page-header">
			<h4>Page View Listing Result</h4>
		</div>
		<div class="table-responsive">
			<table class="table table-striped">
				<c:if test="${pageViews != null}">
					<thead>
						<tr>
							<th></th>
							<th><spring:message code="id" /></th>
							<th><spring:message code="page-url" /></th>
							<th><spring:message code="page-name" /></th>
							<th><spring:message code="page-id" /></th>
							<th><spring:message code="page-type" /></th>
							<th><spring:message code="user-id" /></th>
							<th><spring:message code="username" /></th>
							<th><spring:message code="user-ip-address" /></th>
							<th><spring:message code="create-date" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="pageView" items="${pageViews}">
							<tr>
								<td>
									<input type="checkbox" id="pageViewIds" value="${pageView.id}">
								</td>
								<td>
									<c:out value="${pageView.id}" />
								</td>
								<td>
									<c:out value="${pageView.pageUrl}" />
								</td>
								<td>
									<c:out value="${pageView.pageName}" />
								</td>
								<td>
									<c:out value="${pageView.pageId}" />
								</td>
								<td>
									<c:out value="${pageView.pageType}" />
								</td>
								<td>
									<c:out value="${pageView.userId}" />
								</td>
								<td>
									<c:out value="${pageView.username}" />
								</td>
								<td>
									<c:out value="${pageView.userIPAddress}" />
								</td>
								<td>
									<fmt:formatDate value="${pageView.createDate}" pattern="yyyy-MM-dd hh:mm:ss" />
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</c:if>
			</table>
		</div>
	</div>
</div>

<script src='<c:url value="/resources/js/jquery.dataTables.min.js" />'></script>