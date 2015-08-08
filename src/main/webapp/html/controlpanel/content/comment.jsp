<%@ include file="/html/common/init.jsp" %>

<ol class="breadcrumb">
	<li><a href='<spring:url value="/controlpanel/home" />'>Administration</a></li>
	<li class="active"><a href="<spring:url value="/controlpanel/comment" />">Comments</a></li>
</ol>

<div class="container-fluid">
	<dl>
		<dt><spring:message code="id" /></dt>
		<dd>${comment.id}</dd>
	</dl>
	<dl>
		<dt><spring:message code="site-id" /></dt>
		<dd>${comment.siteId}</dd>
	</dl>
	<dl>
		<dt><spring:message code="content" /></dt>
		<dd>${comment.content.title}</dd>
	</dl>
	<dl>
		<dt><spring:message code="username" /></dt>
		<dd>${comment.user.username}</dd>
	</dl>
	<dl>
		<dt><spring:message code="create-date" /></dt>
		<dd>${comment.createDate}</dd>
	</dl>
	<dl>
		<dt><spring:message code="description" /></dt>
		<dd>${comment.description}</dd>
	</dl>
	<dl>
		<dt><spring:message code="rating" /></dt>
		<dd>${comment.rating}</dd>
	</dl>
</div>