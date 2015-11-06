<%@ include file="/html/common/init.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>Yoda Site - <spring:message code="login" /></title>

	<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />" />

	<link rel="stylesheet" href="<c:url value="/resources/bootstrap-3.2.0/css/bootstrap.min.css" />" type="text/css">

	<link rel="stylesheet" href="<c:url value="/resources/css/login.css" />">
</head>

<body>
	<div class="form-box" id="login-box">
		<div class="header">
			<div class="icons">
				<a href='<c:url value="/"/>'><i class="glyphicon glyphicon-home"></i></a>
			</div>
			<h2 class="text-center"><spring:message code="register" /></h2>
		</div>
		<form action="<c:url value='/user/register'/>" method="POST" name="fm">
			<div class="body">
				<c:if test="${error != null}">
					<p class="alert alert-danger" role="alert"><spring:message code="${error}" /></p>
				</c:if>

				<div class="form-group">
					<input id="username" name="username" type="text" class="form-control" placeholder='<spring:message code="username" />' required autofocus>
				</div>
				<div class="form-group">
					<input id="email" name="email" type="email" class="form-control" placeholder='<spring:message code="email" />' required>
				</div>
				<div class="form-group">
					<input id="password" name="password" type="password" class="form-control" placeholder='<spring:message code="password" />' required>
				</div>

				<input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
			</div>

			<div class="footer">
				<button class="btn btn-block"><spring:message code="register" /></button>
			</div>
		</form>
	</div>
</body>
</html>