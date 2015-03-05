<%@ include file="/html/common/init.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>${siteTitle} - <spring:message code="login" /></title>

	<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

	<link rel="stylesheet" href="<c:url value="/resources/bootstrap-3.2.0/css/bootstrap.min.css" />" type="text/css">

	<link rel="stylesheet" href="<c:url value="/resources/css/login.css" />">
</head>

<body>
	<div id="loginModal" class="modal show" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<a href='<c:url value="/"/>'>
						<i class="glyphicon glyphicon-home"></i>
					</a>
					<h1 class="text-center"><spring:message code="login" /></h1>
				</div>
				<div class="modal-body">
					<form action="<c:url value='j_spring_security_check'/>" class="form col-md-12 center-block" method="POST" name="fm">
						<%-- If the query parameter error exists, authentication was attempted and failed --%>
						<%-- <c:if test="${error != null}">
							<p class="alert alert-danger" role="alert"><c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/></p>
						</c:if> --%>
						<%-- <c:if test="${error != null}">
							<p class="alert alert-danger" role="alert"><spring:message code="${error}" /></p>
						</c:if> --%>
						<c:if test="${exception != null}">
							<p class="alert alert-danger" role="alert"><spring:message code="${exception}" /></p>
						</c:if>
						<c:if test="${param.logout != null}">
							<p class="alert alert-danger" role="alert"><spring:message code="you-have-been-logged-out" /></p>
						</c:if>

						<div class="form-group">
							<input id="email" name="email" type="email" class="form-control input-lg" placeholder="<spring:message code="email" />" required autofocus>
						</div>
						<div class="form-group">
							<input id="password" name="password" type="password" class="form-control input-lg" placeholder="<spring:message code="password" />" required>
						</div>
						<div class="form-group">
							<div class="checkbox">
								<label>
									<input name="remember-me" type="checkbox" value="remember-me">
									<spring:message code="remember-me" />
								</label>
							</div>
						</div>
						<div class="form-group">
							<button class="btn btn-primary btn-lg btn-block"><spring:message code="login" /></button>
							<span class="pull-right"><a href='<c:url value="/user/register"/>'><spring:message code="register" /></a></span>
							<%-- <span><a href="#">Need help?</a></span> --%>
						</div>

						<input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
					</form>
				</div>
				<div class="modal-footer">
					<div class="col-md-12">
						<%-- <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button> --%>
						</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>