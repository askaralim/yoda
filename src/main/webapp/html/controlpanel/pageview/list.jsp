<%@ include file="/html/common/init.jsp" %>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/pageview" />">Page View Listing</a></li>
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
		<!-- <div class="page-header">
			<h4>Page View Listing Result</h4>
		</div> -->
		<div class="table-responsive">
			<table class="table table-striped table-condensed">
				<c:if test="${page.data != null}">
					<thead>
						<tr>
							<th></th>
							<th><spring:message code="id" /></th>
							<th><spring:message code="page-url" /></th>
							<th><spring:message code="page-name" /></th>
							<%-- <th><spring:message code="page-id" /></th>
							<th><spring:message code="page-type" /></th>
							<th><spring:message code="user-id" /></th> --%>
							<th><spring:message code="username" /></th>
							<th><spring:message code="user-ip-address" /></th>
							<th><spring:message code="create-date" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="pageView" items="${page.data}">
							<tr>
								<td>
									<input type="checkbox" id="pageViewIds" value="${pageView.id}">
								</td>
								<td>
									<spring:url value="/controlpanel/pageview/{id}" var="viewPageViewUrl">
										<spring:param name="id" value="${pageView.id}"/>
									</spring:url>
									<a href="${fn:escapeXml(viewPageViewUrl)}">
										<c:out value="${pageView.id}" />
									</a>
								</td>
								<td>
									<c:out value="${pageView.pageUrl}" />
								</td>
								<td>
									<c:out value="${pageView.pageName}" />
								</td>
								<%-- <td>
									<c:out value="${pageView.pageId}" />
								</td>
								<td>
									<c:out value="${pageView.pageType}" />
								</td>
								<td>
									<c:out value="${pageView.userId}" />
								</td> --%>
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
		<div class="row">
			<div class="col-sm-6">
				<div class="pagination-info" role="alert" aria-live="polite" aria-relevant="all">
					Showing ${((page.currentPageNo - 1) * 10) + 1} to ${(page.currentPageNo) * 10} of ${page.totalCount} entries
				</div>
			</div>
			<div class="col-sm-6">
				<div class="pagination-section">
					<div class="pagination-pages">
						<ul class="pagination">
							<c:choose>
								<c:when test="${page.hasPreviousPage}">
									<li><a href='<spring:url value="/controlpanel/pageview?offset=${page.currentPageNo - 2}" />' aria-label="Previous"><span>&laquo;</span></a></li>
								</c:when>
								<c:otherwise>
									<li class="disabled"><span>&laquo;</span></li>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${page.hasNextPage}">
									<li><a href="<spring:url value="/controlpanel/pageview?offset=${page.currentPageNo}" />" aria-label="Next"><span>&raquo;</span></a></li>
								</c:when>
								<c:otherwise>
									<li class="disabled"><span>&raquo;</span></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
					<div class="pagination-nav">${page.currentPageNo}/${page.totalPageCount}</div>
					<div class="pagination-go-input">
						<input type="text" class="form-control">
					</div>
					<div class="pagination-go-button">
						<input type="button" class="btn btn-default" value="Go">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src='<c:url value="/resources/js/jquery.dataTables.min.js" />'></script>