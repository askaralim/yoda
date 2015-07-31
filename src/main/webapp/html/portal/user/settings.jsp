<%@ include file="/html/common/init.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>${user.username} - ${siteInfo.siteName}</title>

	<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

	<link rel="stylesheet" href='<c:url value="/resources/bootstrap-3.2.0/css/bootstrap.min.css" />' type="text/css">
	<link rel="stylesheet" href='<c:url value="/template/basic/main.css" />' type="text/css" />

	<link href="http://fonts.useso.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
	<link href='<c:url value="/template/basic/font-awesome-4.1.0/css/font-awesome.min.css" />' rel="stylesheet" type="text/css">
</head>

<body>
	${horizontalMenu}
	<div class="container pt">
		<!-- <h3>Edit Profile</h3>
		<hr> -->
		<div class="row">
			<!-- left column -->
			<%-- <div class="col-md-3">
				<div class="text-center">
					<img src="//placehold.it/100" class="avatar img-circle" alt="avatar">
					<h6>Upload a different photo...</h6>

					<input type="file" class="form-control">
				</div>
			</div> --%>

			<!-- edit form column -->
			<div class="col-lg-8 col-lg-offset-2">
				<form:form method="post" modelAttribute="user" cssClass="form-horizontal" role="form">
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

					<form:hidden path="userId" />

					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs">
							<li class='${tab == "basic" ? "active" : ""}'>
								<a href="#tab-basic" data-toggle="tab"><spring:message code="basic" /></a>
							</li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab-basic">
								<div class="form-group">
									<label class="col-md-2 control-label"><spring:message code="username" /></label>
									<div class="col-md-8">
										<form:input path="username" cssClass="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label"><spring:message code="email" /></label>
									<div class="col-md-8">
										<form:input path="email" cssClass="form-control" readonly="true" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label"><spring:message code="password" /></label>
									<div class="col-md-8">
										<form:password path="password" cssClass="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label"><spring:message code="verify-password" /></label>
									<div class="col-md-8">
										<form:password path="verifyPassword" cssClass="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label"></label>
									<div class="col-md-8">
										<input type="submit" class="btn btn-primary" value='<spring:message code="save" />'>
										<span></span>
										<%--
										<input type="reset" class="btn btn-primary" value="<spring:message code="cancel" />">
										--%>
									</div>
								</div>
									<%-- <a class="btn btn-sm btn-primary" href="javascript:submitForm('password');" role="button"><spring:message code="update" /></a> --%>
	
								<%-- <div class="tab-pane" id="tab-profile">
									<div class="form-group">
										<label for="userName"><spring:message code="username" /></label>
										<form:input path="userName" cssClass="form-control" />
									</div>
									<a class="btn btn-sm btn-primary" href="javascript:submitForm('update');" role="button"><spring:message code="update" /></a>
								</div> --%>
							</div>
						</div>
					</div>
				</form:form>
			</div>

			<!-- Custom Tabs -->
		</div>
	</div>

	<p class="small text-muted text-center">${siteInfo.siteFooter}</p>

	<script type="text/javascript" src='<c:url value="/template/basic/jquery-1.11.1.min.js" />'></script>
	<script type="text/javascript" src='<c:url value="/template/basic/bootstrap-3.2.0/js/bootstrap.min.js" />'></script>
</body>
</html>