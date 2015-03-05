<%@ include file="/html/common/init.jsp"%>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/system/edit" />">System Maintenances</a></li>
</ol>

<form:form method="post">
	<p>Installation configuration has been reset.</p>
	<p>
		Please proceed immediately to 
			<a href="<spring:url value="/install/createInstallForm" htmlEscape="true" />">
				installation configuration
			</a>
			screen to complete the configuration.
	</p>
</form:form>
