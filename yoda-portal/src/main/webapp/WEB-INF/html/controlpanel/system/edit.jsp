<%@ include file="../../common/init.jsp"%>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/system/edit" />">System Maintenances</a></li>
</ol>

<form:form method="post">
	<p>Once installation is performed, the web interface for
		installation configuration will be automatically disabled to
		protect the fundamental configuration from further changes.</p>
	<p>In case changes is required again, you can reset the
		installation configuration to allow for changes via the web
		interface. Once reset is performed, please proceed as soon as
		possible to perform the changes and to complete the installation.
		This is very important to ensure no illegal access to the
		installation configuration steps.</p>
	<p>Proceed to reset installation configuration</p> 

	<button class="btn btn-primary" type="submit">Reset</button>
</form:form>
