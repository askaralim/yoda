<%@ include file="../../common/init.jsp"%>

<script type="text/javascript">
function submitForm(type) {
	document.fm.cmd.value = type;
	document.fm.submit();
}
</script>

<c:url value="/controlpanel/home" var="actionURL" />

<ol class="breadcrumb">
	<li><a href="<spring:url value="/controlpanel/home" />">Administration</a></li>
</ol>

<form:form method="post" modelAttribute="homePageCommand" action="${actionURL}" name="fm">
	<form:hidden path="cmd" />

	<form:hidden path="userId" />

	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<spring:message code="welcome" /> <c:out value='${homePageCommand.userName}' />,
					<spring:message code="last-login" /> <c:out value="${homePageCommand.lastLoginDatetime}" />
				</div>
				<div class="panel-body">
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

					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs">
							<li class='${homePageCommand.tabName == "password" ? "active" : ""}'><a href="#tab-password" data-toggle="tab"><spring:message code="password" /></a></li>
							<li class='${homePageCommand.tabName == "profile" ? "active" : ""}'><a href="#tab-profile" data-toggle="tab"><spring:message code="details" /></a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab-password">
								<div class="form-group">
									<label for="password"><spring:message code="password" /></label>
									<form:password path="password" cssClass="form-control" />
								</div>
								<div class="form-group">
									<label for="verifyPassword"><spring:message code="verify-password" /></label>
									<form:password path="verifyPassword" cssClass="form-control" />
								</div>
								<a class="btn btn-sm btn-primary" href="javascript:submitForm('password');" role="button"><spring:message code="update" /></a>
							</div>

							<div class="tab-pane" id="tab-profile">
								<div class="form-group">
									<label for="userName"><spring:message code="username" /></label>
									<form:input path="userName" cssClass="form-control" />
								</div>
								<div class="form-group">
									<label for="email"><spring:message code="email" /></label>
									<form:input path="email" cssClass="form-control" />
								</div>
								<div class="form-group">
									<label for="phone"><spring:message code="phone" /></label>
									<form:input path="phone" cssClass="form-control" />
								</div>
								<div class="form-group">
									<label><spring:message code="address" /></label>
									<form:input path="addressLine1" cssClass="form-control" />
								</div>
								<div class="form-group">
									<label></label>
									<form:input path="addressLine2" cssClass="form-control" />
								</div>
								<div class="form-group">
									<label for="cityName"><spring:message code="city" /></label>
									<form:input path="cityName" cssClass="form-control" />
								</div>
								<a class="btn btn-sm btn-primary" href="javascript:submitForm('update');" role="button"><spring:message code="update" /></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<spring:message code="site" /> - <c:out value='${homePageCommand.siteName}' />
				</div>
				<div class="panel-body">
					<div class="form-group">
						<label><spring:message code="switch-site" /></label>
						<form:select path="siteId" cssClass="form-control" onchange="javascript:submitForm('switchSite')">
							<form:options items="${homePageCommand.sites}" itemLabel="siteName" itemValue="siteId" />
						</form:select>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">
					<spring:message code="statistics" />
				</div>
				<div class="panel-body">
					<div class="form-group">
						<c:forEach var="serverStat" items="${homePageCommand.serverStats}">
							<label><c:out value="${serverStat.key}" /></label>
							<p class="form-control-static"><c:out value="${serverStat.value}" /></p>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>