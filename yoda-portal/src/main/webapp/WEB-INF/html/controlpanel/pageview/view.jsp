<%@ include file="../../common/init.jsp"%>

<ol class="breadcrumb">
	<li><a href='<spring:url value="/controlpanel/home" />'>Administration</a></li>
	<li class="active"><a href="<spring:url value="/controlpanel/pageview" />">Page View Listing</a></li>
</ol>

<div class="container-fluid">
	<dl>
		<dt><spring:message code="id" /></dt>
		<dd>${pageView.id}</dd>
	</dl>
	<dl>
		<dt><spring:message code="page-url" /></dt>
		<dd>${pageView.pageUrl}</dd>
	</dl>
	<dl>
		<dt><spring:message code="page-name" /></dt>
		<dd>${pageView.pageName}</dd>
	</dl>
	<dl>
		<dt><spring:message code="page-id" /></dt>
		<dd>${pageView.pageId}</dd>
	</dl>
	<dl>
		<dt><spring:message code="page-type" /></dt>
		<dd>
			<c:choose>
				<c:when test="${pageView.pageType == 1}">
					<spring:message code="home" />
				</c:when>
				<c:when test="${pageView.pageType == 2}">
					<spring:message code="brand" />
				</c:when>
				<c:when test="${pageView.pageType == 3}">
					<spring:message code="content" />
				</c:when>
				<c:when test="${pageView.pageType == 4}">
					<spring:message code="items" />
				</c:when>
				<c:otherwise>
					empty
				</c:otherwise>
			</c:choose>
		</dd>
	</dl>
	<dl>
		<dt><spring:message code="user-id" /></dt>
		<dd>${pageView.userId}</dd>
	</dl>
	<dl>
		<dt><spring:message code="username" /></dt>
		<dd>${pageView.username}</dd>
	</dl>
	<dl>
		<dt><spring:message code="user-ip-address" /></dt>
		<dd>${pageView.userIPAddress}</dd>
	</dl>
	<dl>
		<dt><spring:message code="create-date" /></dt>
		<dd><fmt:formatDate value="${pageView.createDate}" pattern="yyyy-MM-dd hh:mm:ss" /></dd>
	</dl>
</div>