<%@ include file="/html/common/init.jsp"%>

<%@ page language="java" import="com.yoda.fckeditor.FckEditorCreator"%>

<jsp:useBean id="siteEditCommand" type="com.yoda.site.SiteEditCommand" scope="request" />

<script type="text/javascript">
function submitBackForm() {
	location.href='<spring:url value="/controlpanel/site/list" htmlEscape="true" />';

	return false;
}
</script>

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
	<li><a href="<spring:url value="/controlpanel/site/list" htmlEscape="true" />">Site Listing</a></li>
	<li>Site Maintenance</li>
</ol>

<form:form method="post" modelAttribute="siteEditCommand" name="fm">
	<form:hidden path="tabIndex" />

	<c:if test="${success != null}">
		<div class="alert alert-success" role="alert">
			<a class="panel-close close" data-dismiss="alert">×</a>
			<i class="glyphicon glyphicon-ok"></i> <spring:message code="saved-success" />
		</div>
	</c:if>
	<c:if test="${errors != null}">
		<div class="alert alert-danger" role="alert">
			<a class="panel-close close" data-dismiss="alert">×</a>
			<form:errors path="*" />
		</div>
	</c:if>

	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<spring:message code="details" />
				</div>
				<div class="panel-body">
					<div class="form-group">
						<c:if test="${!siteEditCommand['new']}">
							<div class="form-group">
								<form:hidden path="siteId"/>
								<label><spring:message code="id" /></label>
								<p class="form-control-static"><c:out value="${siteEditCommand.siteId}" /></p>
								<form:hidden path="siteId"/>
							</div>
						</c:if>
					</div>
					<div class="form-group">
						<label for="siteName"><spring:message code="site-name" /></label>
						<form:input path="siteName" cssClass="form-control" />
						<p class="help-block">The name of the site and is feed to the template.</p>
					</div>
					<div class="form-group">
						<label for="domainName">Site Public Domain Name</label>
						<form:input path="domainName" cssClass="form-control" />
						<p class="help-block">Domain name for the public site and is important for
							determining if the incoming request is for this site. It
							should be in the format of www.yodasite.com.</p>
					</div>
					<div class="form-group">
						<label for="publicPort">Site Public Port Number (default to 80)</label>
						<form:input path="publicPort" cssClass="form-control" />
						<p class="help-block">If not specified, port number will be defaulted to standard port 80.</p>
					</div>
					<div class="form-group">
						<div class="checkbox">
							<label>
								<form:checkbox path="secureConnectionEnabled" value="TRUE" />Enable SSL secure connection
							</label>
							<p class="help-block">Select this option when SSL is required when
								performing secure transactions in the public site. Once
								selected, SSL will be used when user perform checkout
								transaction and viewing their account information.</p>
						</div>
					</div>
					<div class="form-group">
						<label for="securePort">Site Secure Port Number (default to 443)</label>
						<form:input path="securePort" cssClass="form-control" />
						<p class="help-block">If not specified, port number will be defaulted to standard port 443.</p>
					</div>
					<div class="form-group">
						<label for="googleAnalyticsId">Google Analytics Id</label>
						<form:input path="googleAnalyticsId" cssClass="form-control" />
					</div>
					<div class="form-group">
						<div class="checkbox">
							<label>
								<form:checkbox path="active" value="Y" />Active
							</label>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-9">
			<div class="text-right">
				<c:if test="${siteEditCommand['new']}">
					<input type="submit" value="<spring:message code="save" />" class="btn btn-sm btn-primary" role="button">
				</c:if>
				<c:if test="${!siteEditCommand['new']}">
					<input type="submit" value="<spring:message code="update" />" class="btn btn-sm btn-primary" role="button">
				</c:if>
				<input type="button" value='<spring:message code="cancel" />' class="btn btn-sm btn-default" onclick="return submitBackForm();" role="button" >
			</div>

			<div class="nav-tabs-custom">
				<ul class="nav nav-tabs">
					<li class='${siteEditCommand.tabIndex == 0 ? "active" : ""}'><a href="#tab-general" data-toggle="tab">General</a></li>
					<li class='${siteEditCommand.tabIndex == 1 ? "active" : ""}'><a href="#tab-logo" data-toggle="tab">Site logo</a></li>
					<%-- <li class='${siteEditCommand.tabIndex == "mail" ? "active" : ""}'><a href="#tab-mail" data-toggle="tab">Mail</a></li>
					<li class='${siteEditCommand.tabIndex == "template" ? "active" : ""}'><a href="#tab-template" data-toggle="tab">Template</a></li>
					<li class='${siteEditCommand.tabIndex == "business" ? "active" : ""}'><a href="#tab-business" data-toggle="tab">Business</a></li>
					<li class='${siteEditCommand.tabIndex == "shipping" ? "active" : ""}'><a href="#tab-shipping" data-toggle="tab">Shipping</a></li>
					<li class='${siteEditCommand.tabIndex == "checkout" ? "active" : ""}'><a href="#tab-checkout" data-toggle="tab">Checkout</a></li>
					<li class='${siteEditCommand.tabIndex == "paypal" ? "active" : ""}'><a href="#tab-paypal" data-toggle="tab">Paypal</a></li>
					<li class='${siteEditCommand.tabIndex == "payment" ? "active" : ""}'><a href="#tab-payment" data-toggle="tab">Payment Gateway</a></li> --%>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab-general">
						<div class="form-group">
							<label for="listingPageSize">Listing page size</label>
							<form:input path="listingPageSize" cssClass="form-control" />
							<p class="help-block">Number of entries to be shown per page during search.</p>
						</div>
						<div class="form-group">
							<label for="sectionPageSize">Section page size</label>
							<form:input path="sectionPageSize" cssClass="form-control" />
							<p class="help-block">Number of entries to be shown per section page.</p>
						</div>
						<div class="form-group">
							<label for="footer">Public site footer</label>
							<form:input path="footer" cssClass="form-control" />
							<p class="help-block">Footer to be used on public site.</p>
						</div>
					</div>

					<div class="tab-pane" id="tab-logo">
						<div class="form-group">
							<label for="footer">Site Logo</label>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>