<%@ include file="../../common/init.jsp"%>

<ol class="breadcrumb">
	<li><a href='<spring:url value="/controlpanel/home" />'>Administration</a></li>
	<li class="active"><a href="<spring:url value="/controlpanel/feedback" />">Feedback</a></li>
</ol>

<div class="container-fluid">
	<dl>
		<dt><spring:message code="id" /></dt>
		<dd>${feedback.id}</dd>
	</dl>
	<dl>
		<dt><spring:message code="username" /></dt>
		<dd>${feedback.username}</dd>
	</dl>
	<dl>
		<dt><spring:message code="email" /></dt>
		<dd>${feedback.email}</dd>
	</dl>
	<dl>
		<dt><spring:message code="phone" /></dt>
		<dd>${feedback.phone}</dd>
	</dl>
	<dl>
		<dt><spring:message code="create-date" /></dt>
		<dd><fmt:formatDate value="${feedback.createDate}" pattern="yyyy-MM-dd" /></dd>
	</dl>
	<dl>
		<dt><spring:message code="description" /></dt>
		<dd>${feedback.description}</dd>
	</dl>
</div>